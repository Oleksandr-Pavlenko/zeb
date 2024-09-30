package com.pavlenko.zeb.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EnergyConsumptionRequestDto(
        @NotBlank(message = "Description of the energy source is required.") String description,
        @NotBlank(message = "Energy source ID is required.") String energySourceId,
        @NotNull(message = "Energy Consumption is required.")
        @Digits(integer = Integer.MAX_VALUE, fraction = 5, message = "Energy Consumption must have at most 5 decimal places.") BigDecimal energyConsumption,
        @Digits(integer = Integer.MAX_VALUE, fraction = 5) BigDecimal emissionFactor
) {
}
