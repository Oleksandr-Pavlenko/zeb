package com.pavlenko.zeb.challenge.service;

import com.pavlenko.zeb.challenge.dto.EnergyConsumptionResponseDto;
import com.pavlenko.zeb.challenge.dto.EnergyConsumptionRequestDto;
import com.pavlenko.zeb.challenge.dto.EnergySourceDto;
import com.pavlenko.zeb.challenge.dto.ScopeHierarchyDto;
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
