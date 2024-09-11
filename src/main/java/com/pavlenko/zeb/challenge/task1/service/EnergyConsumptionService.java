package com.pavlenko.zeb.challenge.task1.service;

import com.pavlenko.zeb.challenge.task1.client.EnergySourceClient;
import com.pavlenko.zeb.challenge.task1.client.ScopeHierarchyClient;
import com.pavlenko.zeb.challenge.task1.dto.EnergyConsumptionResponseDto;
import com.pavlenko.zeb.challenge.task1.dto.EnergyConsumptionRequestDto;
import com.pavlenko.zeb.challenge.task1.dto.EnergySourceDto;
import com.pavlenko.zeb.challenge.task1.dto.ScopeHierarchyDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class EnergyConsumptionService {
    private final ScopeHierarchyClient scopeHierarchyClient;
    private final EnergySourceClient energySourceClient;

    public EnergyConsumptionService(ScopeHierarchyClient scopeHierarchyClient, EnergySourceClient energySourceClient) {
        this.scopeHierarchyClient = scopeHierarchyClient;
        this.energySourceClient = energySourceClient;
    }

    public List<EnergyConsumptionResponseDto> calculateCo2(List<EnergyConsumptionRequestDto> consumptionList) {
        ScopeHierarchyDto[] scopeHierarchy = scopeHierarchyClient.getScopeHierarchy();
        EnergySourceDto[] energySources = energySourceClient.getEnergySources();

        return buildCo2HierarchyResponse(consumptionList, energySources, scopeHierarchy);
    }

    private List<EnergyConsumptionResponseDto> buildCo2HierarchyResponse(List<EnergyConsumptionRequestDto> consumptionList,
                                                                         EnergySourceDto[] energySources,
                                                                         ScopeHierarchyDto[] scopeHierarchy) {

        Map<String, EnergyConsumptionResponseDto> scopeDataMap = new HashMap<>();
        Map<String, EnergyConsumptionResponseDto> subScopeDataMap = new HashMap<>();
        int nameCounter = 1;

        for (EnergyConsumptionRequestDto consumption : consumptionList) {
            EnergySourceDto energySource = findEnergySourceById(consumption.energySourceId(), energySources);
            if (energySource == null) {
                throw new IllegalArgumentException("Energy source with ID " + consumption.energySourceId() + " not found.");
            }

            ScopeHierarchyDto subScope = findScopeById(energySource.scopeId(), scopeHierarchy);
            if (subScope == null) {
                throw new IllegalArgumentException("Scope with ID " + energySource.scopeId() + " not found.");
            }

            EnergyConsumptionResponseDto consumptionData = calculateEnergyAndCO2(consumption, energySource, subScope, nameCounter++);
            addConsumptionDataToSubScope(subScopeDataMap, subScope, consumptionData);
        }

        // Add all subScope to their parent Scope
        for (Map.Entry<String, EnergyConsumptionResponseDto> entry : subScopeDataMap.entrySet()) {
            ScopeHierarchyDto subScope = findScopeById(entry.getKey(), scopeHierarchy);
            ScopeHierarchyDto parentScope = findParentScope(subScope.id(), scopeHierarchy);
            if (parentScope != null) {
                addSubScopeToParentScope(scopeDataMap, entry.getValue(), parentScope);
            }
        }

        return new ArrayList<>(scopeDataMap.values());
    }

    private void addConsumptionDataToSubScope(Map<String, EnergyConsumptionResponseDto> subScopeDataMap,
                                              ScopeHierarchyDto subScope,
                                              EnergyConsumptionResponseDto resourceData) {
        // Add subScope if it is not in the map
        subScopeDataMap.computeIfAbsent(subScope.id(), id ->
                new EnergyConsumptionResponseDto(subScope.name(), subScope.label(), BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>())
        );

        // Add a resourceData to a child element of the subcategory and summarize the data
        EnergyConsumptionResponseDto subScopeDto = subScopeDataMap.get(subScope.id());
        subScopeDto.getChildren().add(resourceData);
        subScopeDto.setEnergy(subScopeDto.getEnergy().add(resourceData.getEnergy()));
        subScopeDto.setCo2(subScopeDto.getCo2().add(resourceData.getCo2()));
    }

    private void addSubScopeToParentScope(Map<String, EnergyConsumptionResponseDto> scopeDataMap,
                                          EnergyConsumptionResponseDto subScopeDto,
                                          ScopeHierarchyDto parentScope) {
        // Add a parent Scope if it doesn’t exist
        scopeDataMap.computeIfAbsent(parentScope.id(), id ->
                new EnergyConsumptionResponseDto(parentScope.name(), parentScope.label(), BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>())
        );

        // Summarize the subcategory data into the parent Scope (second layer)
        EnergyConsumptionResponseDto parentScopeDto = scopeDataMap.get(parentScope.id());
        parentScopeDto.setEnergy(parentScopeDto.getEnergy().add(subScopeDto.getEnergy()));
        parentScopeDto.setCo2(parentScopeDto.getCo2().add(subScopeDto.getCo2()));

        // Add a subcategory to the children of the parent Scope
        if (!parentScopeDto.getChildren().contains(subScopeDto)) {
            parentScopeDto.getChildren().add(subScopeDto);
        }
    }

    private EnergyConsumptionResponseDto calculateEnergyAndCO2(EnergyConsumptionRequestDto consumption, EnergySourceDto energySource, ScopeHierarchyDto scope, int resourceCounter) {
        BigDecimal energyInKwh = consumption.energyConsumption().multiply(energySource.conversionFactor());
        BigDecimal emissionFactor = consumption.emissionFactor() != null ? consumption.emissionFactor() : energySource.emissionFactor();
        BigDecimal co2Emissions = energyInKwh.multiply(emissionFactor).divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP);

        energyInKwh = energyInKwh.setScale(2, RoundingMode.HALF_UP);
        co2Emissions = co2Emissions.setScale(2, RoundingMode.HALF_UP);

        String resourceName = scope.name() + "." + resourceCounter;
        //resourceLabel for 3rd level
        String resourceLabel = energySource.name() + " (" + consumption.description() + ")";

        return new EnergyConsumptionResponseDto(resourceName, resourceLabel, energyInKwh, co2Emissions, new ArrayList<>());
    }

    private EnergySourceDto findEnergySourceById(String energySourceId, EnergySourceDto[] energySources) {
        for (EnergySourceDto source : energySources) {
            if (source.energySourceId().equals(energySourceId)) {
                return source;
            }
        }
        return null;
    }

    private ScopeHierarchyDto findScopeById(String scopeId, ScopeHierarchyDto[] scopeHierarchy) {
        for (ScopeHierarchyDto scope : scopeHierarchy) {
            for (ScopeHierarchyDto subScope : scope.subScopes()) {
                if (subScope.id().equals(scopeId)) {
                    return subScope;
                }
            }
        }
        return null;
    }

    private ScopeHierarchyDto findParentScope(String scopeId, ScopeHierarchyDto[] scopeHierarchy) {
        String parentScopeId = scopeId.substring(0, scopeId.lastIndexOf('_'));

        for (ScopeHierarchyDto scope : scopeHierarchy) {
            if (scope.id().equals(parentScopeId)) {
                return scope;
            }
        }

        return null;
    }
}
