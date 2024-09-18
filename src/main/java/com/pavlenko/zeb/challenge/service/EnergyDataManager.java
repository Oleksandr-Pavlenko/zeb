package com.pavlenko.zeb.challenge.service;

import com.pavlenko.zeb.challenge.client.ExternalApiProvider;
import com.pavlenko.zeb.challenge.dto.EnergySourceDto;
import com.pavlenko.zeb.challenge.dto.ScopeHierarchyDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class EnergyDataManager {
    private final ExternalApiProvider externalApiProvider;

    public EnergyDataManager(ExternalApiProvider externalApiProvider) {
        this.externalApiProvider = externalApiProvider;
    }

    public ScopeHierarchyDto[] getScopeHierarchy() {
        return externalApiProvider.getScopeHierarchy();
    }

    public EnergySourceDto[] getEnergySources() {
        return externalApiProvider.getEnergySources();
    }

    public EnergySourceDto findEnergySourceById(String energySourceId, EnergySourceDto[] energySources) {
        return Arrays.stream(energySources)
                .filter(source -> source.energySourceId().equals(energySourceId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Energy source with ID " + energySourceId + " not found."));
    }

    public ScopeHierarchyDto findScopeById(String scopeId, ScopeHierarchyDto[] scopeHierarchy) {
        return Arrays.stream(scopeHierarchy)
                .flatMap(scope -> scope.subScopes().stream())
                .filter(subScope -> subScope.id().equals(scopeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Scope with ID " + scopeId + " not found."));
    }

    public ScopeHierarchyDto findParentScopeById(String subScopeId, ScopeHierarchyDto[] scopeHierarchy) {
        String parentScopeId = subScopeId.substring(0, subScopeId.lastIndexOf('_'));
        return Arrays.stream(scopeHierarchy)
                .flatMap(scope -> Stream.concat(Stream.of(scope), scope.subScopes().stream()))
                .filter(scope -> scope.id().equals(parentScopeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Parent scope with ID " + parentScopeId + " not found."));
    }
}
