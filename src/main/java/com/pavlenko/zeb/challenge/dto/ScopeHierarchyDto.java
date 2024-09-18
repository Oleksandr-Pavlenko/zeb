package com.pavlenko.zeb.challenge.dto;

import java.util.List;

public record ScopeHierarchyDto(
        String id,
        String name,
        String label,
        List<ScopeHierarchyDto> subScopes
) {
}
