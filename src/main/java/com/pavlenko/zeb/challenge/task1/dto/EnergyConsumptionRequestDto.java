package com.pavlenko.zeb.challenge.task1.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class EnergyConsumptionRequestDto {
    @NotBlank(message = "Description of the energy source is required.")
    private String description;
    @NotBlank(message = "Energy source ID is required.")
    private String energySourceId;
    @NotNull(message = "Energy Consumption is required.")
    @Digits(integer = Integer.MAX_VALUE, fraction = 5, message = "Energy Consumption must have at most 5 decimal places.")
    private BigDecimal energyConsumption;
    @Digits(integer = Integer.MAX_VALUE, fraction = 5)
    private BigDecimal emissionFactor;

    public EnergyConsumptionRequestDto(String description, String energySourceId, BigDecimal energyConsumption, BigDecimal emissionFactor) {
        this.description = description;
        this.energySourceId = energySourceId;
        this.energyConsumption = energyConsumption;
        this.emissionFactor = emissionFactor;
    }

    public @NotBlank(message = "Description of the energy source is required.") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description of the energy source is required.") String description) {
        this.description = description;
    }

    public @NotBlank(message = "Energy source ID is required.") String getEnergySourceId() {
        return energySourceId;
    }

    public void setEnergySourceId(@NotBlank(message = "Energy source ID is required.") String energySourceId) {
        this.energySourceId = energySourceId;
    }

    public @NotNull(message = "Energy Consumption is required.") @Digits(integer = Integer.MAX_VALUE, fraction = 5) BigDecimal getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(@NotNull(message = "Energy Consumption is required.") @Digits(integer = Integer.MAX_VALUE, fraction = 5) BigDecimal energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public @Digits(integer = Integer.MAX_VALUE, fraction = 5) BigDecimal getEmissionFactor() {
        return emissionFactor;
    }

    public void setEmissionFactor(@Digits(integer = Integer.MAX_VALUE, fraction = 5) BigDecimal emissionFactor) {
        this.emissionFactor = emissionFactor;
    }
}
