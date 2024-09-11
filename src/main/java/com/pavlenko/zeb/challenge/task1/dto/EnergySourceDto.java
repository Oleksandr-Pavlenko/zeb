package com.pavlenko.zeb.challenge.task1.dto;

import java.math.BigDecimal;

public record EnergySourceDto(
        String energySourceId,
        String scopeId,
        String name,
        BigDecimal conversionFactor,
        BigDecimal emissionFactor
) {
}
