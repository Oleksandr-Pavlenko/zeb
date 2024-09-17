package com.pavlenko.zeb.challenge.task1.client;

import com.pavlenko.zeb.challenge.task1.dto.EnergySourceDto;
import com.pavlenko.zeb.challenge.task1.dto.ScopeHierarchyDto;
import org.springframework.stereotype.Component;

@Component
public class ExternalApiProvider {
    private final ScopeHierarchyClient scopeHierarchyClient;
    private final EnergySourceClient energySourceClient;

    public ExternalApiProvider(ScopeHierarchyClient scopeHierarchyClient, EnergySourceClient energySourceClient) {
        this.scopeHierarchyClient = scopeHierarchyClient;
        this.energySourceClient = energySourceClient;
    }

    public ScopeHierarchyDto[] getScopeHierarchy() {
        return scopeHierarchyClient.getScopeHierarchy();
    }

    public EnergySourceDto[] getEnergySources() {
        return energySourceClient.getEnergySources();
    }
}
