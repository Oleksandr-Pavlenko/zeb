package com.pavlenko.zeb.challenge.task1.service;

import com.pavlenko.zeb.challenge.task1.dto.EnergyConsumptionResponseDto;
import com.pavlenko.zeb.challenge.task1.dto.EnergyConsumptionRequestDto;
import com.pavlenko.zeb.challenge.task1.dto.EnergySourceDto;
import com.pavlenko.zeb.challenge.task1.dto.ScopeHierarchyDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnergyConsumptionService {
    private final EnergyDataManager energyDataManager;
    private final EnergyHierarchyManager energyHierarchyManager;

    public EnergyConsumptionService(EnergyDataManager energyDataManager,
                                    EnergyHierarchyManager energyHierarchyManager) {
        this.energyDataManager = energyDataManager;
        this.energyHierarchyManager = energyHierarchyManager;
    }

    public List<EnergyConsumptionResponseDto> calculateCo2(List<EnergyConsumptionRequestDto> consumptionList) {
        ScopeHierarchyDto[] scopeHierarchy = energyDataManager.getScopeHierarchy();
        EnergySourceDto[] energySources = energyDataManager.getEnergySources();

        return energyHierarchyManager.buildCo2HierarchyResponse(consumptionList, energySources, scopeHierarchy);
    }
}
