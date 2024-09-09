package com.pavlenko.zeb.challenge.task1.controller;

import com.pavlenko.zeb.challenge.task1.dto.EnergyConsumptionRequestDto;
import com.pavlenko.zeb.challenge.task1.service.EnergyConsumptionService;
import com.pavlenko.zeb.challenge.task1.dto.EnergyConsumptionResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/energy")
public class EnergyConsumptionController {
    private final EnergyConsumptionService energyConsumptionService;

    public EnergyConsumptionController(EnergyConsumptionService energyConsumptionService) {
        this.energyConsumptionService = energyConsumptionService;
    }

    @PostMapping("/consumption")
    public ResponseEntity<List<EnergyConsumptionResponseDto>> calculateCo2(
            @RequestBody @Valid List<EnergyConsumptionRequestDto> energyConsumptionList) {
        List<EnergyConsumptionResponseDto> response = energyConsumptionService.calculateCo2(energyConsumptionList);

        return ResponseEntity.ok(response);
    }
}
