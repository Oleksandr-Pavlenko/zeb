package com.pavlenko.zeb.challenge.task1.service;

import com.pavlenko.zeb.challenge.task1.dto.EnergyConsumptionResponseDto;
import com.pavlenko.zeb.challenge.task1.dto.ScopeHierarchyDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@Component
public class EnergyConsumptionDataHandler {
    public void addConsumptionDataToSubScope(Map<String, EnergyConsumptionResponseDto> subScopeDataMap,
                                             ScopeHierarchyDto subScope,
                                             EnergyConsumptionResponseDto resourceData) {
        EnergyConsumptionResponseDto subScopeDto = getOrCreateScope(subScopeDataMap, subScope);

        subScopeDto.getChildren().add(resourceData);
        subScopeDto.setEnergy(subScopeDto.getEnergy().add(resourceData.getEnergy()));
        subScopeDto.setCo2(subScopeDto.getCo2().add(resourceData.getCo2()));
    }

    public void addSubScopeToParentScope(Map<String, EnergyConsumptionResponseDto> scopeDataMap,
                                         EnergyConsumptionResponseDto subScopeDto,
                                         ScopeHierarchyDto parentScope) {
        EnergyConsumptionResponseDto parentScopeDto = getOrCreateScope(scopeDataMap, parentScope);

        parentScopeDto.setEnergy(parentScopeDto.getEnergy().add(subScopeDto.getEnergy()));
        parentScopeDto.setCo2(parentScopeDto.getCo2().add(subScopeDto.getCo2()));

        if (!parentScopeDto.getChildren().contains(subScopeDto)) {
            parentScopeDto.getChildren().add(subScopeDto);
        }
    }

    private EnergyConsumptionResponseDto getOrCreateScope(Map<String, EnergyConsumptionResponseDto> scopeDataMap, ScopeHierarchyDto scope) {
        return scopeDataMap.computeIfAbsent(scope.id(), id ->
                new EnergyConsumptionResponseDto(scope.name(), scope.label(), BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>())
        );
    }
}

