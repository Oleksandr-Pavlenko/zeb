package com.pavlenko.zeb.challenge.task1.dto;

import java.math.BigDecimal;

public class EnergySourceDto {
    private String energySourceId;
    private String scopeId;
    private String name;
    private BigDecimal conversionFactor;
    private BigDecimal emissionFactor;

    public EnergySourceDto(String energySourceId, String scopeId, String name, BigDecimal conversionFactor, BigDecimal emissionFactor) {
        this.energySourceId = energySourceId;
        this.scopeId = scopeId;
        this.name = name;
        this.conversionFactor = conversionFactor;
        this.emissionFactor = emissionFactor;
    }

    public String getEnergySourceId() {
        return energySourceId;
    }

    public void setEnergySourceId(String energySourceId) {
        this.energySourceId = energySourceId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public BigDecimal getEmissionFactor() {
        return emissionFactor;
    }

    public void setEmissionFactor(BigDecimal emissionFactor) {
        this.emissionFactor = emissionFactor;
    }
}
