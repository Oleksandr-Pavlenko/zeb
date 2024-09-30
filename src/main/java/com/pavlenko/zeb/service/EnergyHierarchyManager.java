package com.pavlenko.zeb.service;

import com.pavlenko.zeb.dto.EnergyConsumptionRequestDto;
import com.pavlenko.zeb.dto.EnergyConsumptionResponseDto;
import com.pavlenko.zeb.dto.EnergySourceDto;
import com.pavlenko.zeb.dto.ScopeHierarchyDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EnergyHierarchyManager {
    private final EnergyConsumptionCalculator energyConsumptionCalculator;
    private final EnergyConsumptionDataHandler energyConsumptionDataHandler;
    private final EnergyDataManager energyDataManager;

    public EnergyHierarchyManager(EnergyConsumptionCalculator energyConsumptionCalculator,
                                  EnergyConsumptionDataHandler energyConsumptionDataHandler,
                                  EnergyDataManager energyDataManager) {
        this.energyConsumptionCalculator = energyConsumptionCalculator;
        this.energyConsumptionDataHandler = energyConsumptionDataHandler;
        this.energyDataManager = energyDataManager;
    }

    public List<EnergyConsumptionResponseDto> buildCo2HierarchyResponse(List<EnergyConsumptionRequestDto> consumptionList,
                                                                        EnergySourceDto[] energySources,
                                                                        ScopeHierarchyDto[] scopeHierarchy) {

        Map<String, EnergyConsumptionResponseDto> scopeDataMap = new HashMap<>();
        Map<String, EnergyConsumptionResponseDto> subScopeDataMap = new HashMap<>();
        int nameCounter = 1;

        for (EnergyConsumptionRequestDto consumption : consumptionList) {
            EnergySourceDto energySource = energyDataManager.findEnergySourceById(consumption.energySourceId(), energySources);
            ScopeHierarchyDto subScope = energyDataManager.findScopeById(energySource.scopeId(), scopeHierarchy);

            BigDecimal energyInKwh = energyConsumptionCalculator.calculateEnergyInKwh(consumption, energySource);
            BigDecimal co2Emissions = energyConsumptionCalculator.calculateCo2Emissions(energyInKwh, consumption, energySource);

            String resourceName = subScope.name() + "." + nameCounter++;
            String resourceLabel = energySource.name() + " (" + consumption.description() + ")";

            EnergyConsumptionResponseDto consumptionData = new EnergyConsumptionResponseDto(resourceName, resourceLabel, energyInKwh, co2Emissions, new ArrayList<>());

            energyConsumptionDataHandler.addConsumptionDataToSubScope(subScopeDataMap, subScope, consumptionData);
        }

        for (Map.Entry<String, EnergyConsumptionResponseDto> entry : subScopeDataMap.entrySet()) {
            String subScopeId = entry.getKey();
            EnergyConsumptionResponseDto subScopeDto = entry.getValue();

            ScopeHierarchyDto parentScope = energyDataManager.findParentScopeById(subScopeId, scopeHierarchy);
            energyConsumptionDataHandler.addSubScopeToParentScope(scopeDataMap, subScopeDto, parentScope);
        }

        return new ArrayList<>(scopeDataMap.values());
    }
}
