package com.pavlenko.zeb.challenge.task1.dto;

import java.math.BigDecimal;
import java.util.List;

public class EnergyConsumptionResponseDto {
    private String name;
    private String label;
    private BigDecimal energy;
    private BigDecimal co2;
    private List<EnergyConsumptionResponseDto> children;

    public EnergyConsumptionResponseDto(String name, String label, BigDecimal energy, BigDecimal co2, List<EnergyConsumptionResponseDto> children) {
        this.name = name;
        this.label = label;
        this.energy = energy;
        this.co2 = co2;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    public BigDecimal getCo2() {
        return co2;
    }

    public void setCo2(BigDecimal co2) {
        this.co2 = co2;
    }

    public List<EnergyConsumptionResponseDto> getChildren() {
        return children;
    }

    public void setChildren(List<EnergyConsumptionResponseDto> children) {
        this.children = children;
    }
}
