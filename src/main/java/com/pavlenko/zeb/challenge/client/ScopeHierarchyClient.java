package com.pavlenko.zeb.challenge.client;

import com.pavlenko.zeb.challenge.dto.ScopeHierarchyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ScopeHierarchyClient {
    private static final Logger logger = LoggerFactory.getLogger(ScopeHierarchyClient.class);
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://applied-coding-challenge.s3.eu-central-1.amazonaws.com";

    public ScopeHierarchyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ScopeHierarchyDto[] getScopeHierarchy() {
        String url = baseUrl + "/backend/scopes.json";

        try {
            ResponseEntity<ScopeHierarchyDto[]> response = restTemplate.getForEntity(url, ScopeHierarchyDto[].class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Successfully retrieved Scope hierarchy data.");
                return response.getBody();
            } else {
                logger.error("Error retrieving Scope hierarchy data: Response status: {}", response.getStatusCode());
                return new ScopeHierarchyDto[0];
            }
        } catch (RestClientException e) {
            logger.error("Error when trying to access the Scope hierarchy API: {}", e.getMessage());
            return new ScopeHierarchyDto[0];
        }
    }
}
