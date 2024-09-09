package com.pavlenko.zeb.challenge.task1.client;

import com.pavlenko.zeb.challenge.task1.dto.EnergySourceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class EnergySourceClient {
    private static final Logger logger = LoggerFactory.getLogger(EnergySourceClient.class);
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://applied-coding-challenge.s3.eu-central-1.amazonaws.com";

    public EnergySourceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EnergySourceDto[] getEnergySources() {
        String url = baseUrl + "/backend/energy-sources.json";

        try {
            ResponseEntity<EnergySourceDto[]> response = restTemplate.getForEntity(url, EnergySourceDto[].class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Successfully obtained data on energy sources.");
                return response.getBody();
            } else {
                logger.error("Error while retrieving energy source data: Response status: {}", response.getStatusCode());
                return new EnergySourceDto[0];
            }
        } catch (RestClientException e) {
            logger.error("Error when trying to access the API: {}", e.getMessage());
            return new EnergySourceDto[0];
        }
    }
}
