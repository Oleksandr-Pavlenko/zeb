package com.pavlenko.zeb.challenge.client;

import com.pavlenko.zeb.challenge.dto.EnergySourceDto;
import com.pavlenko.zeb.challenge.dto.ScopeHierarchyDto;
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
