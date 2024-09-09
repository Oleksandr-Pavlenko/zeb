package com.pavlenko.zeb.challenge.task1.dto;

import java.util.List;

public class ScopeHierarchyDto {
    private String id;
    private String name;
    private String label;
    private List<ScopeHierarchyDto> subScopes;

    public ScopeHierarchyDto(String id, String name, String label, List<ScopeHierarchyDto> subScopes) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.subScopes = subScopes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<ScopeHierarchyDto> getSubScopes() {
        return subScopes;
    }

    public void setSubScopes(List<ScopeHierarchyDto> subScopes) {
        this.subScopes = subScopes;
    }
}
