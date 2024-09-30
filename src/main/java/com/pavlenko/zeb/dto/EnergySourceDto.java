package com.pavlenko.zeb.dto;

import java.math.BigDecimal;

public record EnergySourceDto(
        String energySourceId,
        String scopeId,
        String name,
        BigDecimal conversionFactor,
        BigDecimal emissionFactor
) {
}
