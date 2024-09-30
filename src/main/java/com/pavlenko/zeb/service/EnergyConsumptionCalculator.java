package com.pavlenko.zeb.service;

import com.pavlenko.zeb.dto.EnergyConsumptionRequestDto;
import com.pavlenko.zeb.dto.EnergySourceDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class EnergyConsumptionCalculator {
    public BigDecimal calculateEnergyInKwh(EnergyConsumptionRequestDto consumption, EnergySourceDto energySource) {
        return consumption.energyConsumption()
                .multiply(energySource.conversionFactor())
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateCo2Emissions(BigDecimal energyInKwh, EnergyConsumptionRequestDto consumption, EnergySourceDto energySource) {
        BigDecimal emissionFactor = consumption.emissionFactor() != null ? consumption.emissionFactor() : energySource.emissionFactor();
        return energyInKwh.multiply(emissionFactor)
                .divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
