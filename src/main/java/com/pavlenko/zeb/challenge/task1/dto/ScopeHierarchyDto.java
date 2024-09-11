package com.pavlenko.zeb.challenge.task1.dto;

import java.util.List;

public record ScopeHierarchyDto(
        String id,
        String name,
        String label,
        List<ScopeHierarchyDto> subScopes
) {
}
