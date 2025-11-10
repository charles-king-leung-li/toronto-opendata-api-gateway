package com.toronto.opendata.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.toronto.opendata.gateway.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
@Tag(name = "Configuration", description = "API configuration endpoints")
public class ApiKeyController {
    
    @Value("${google.maps.api.key:}")
    private String googleMapsApiKey;
    
    /**
     * Endpoint to provide the Google Maps API key to the frontend
     * Note: Only expose this if your API key has proper domain restrictions!
     */
    @Operation(
        summary = "Get Google Maps API Key",
        description = "Returns the Google Maps API key for frontend use. Ensure your API key has domain restrictions."
    )
    @GetMapping("/maps-key")
    public ResponseEntity<ApiResponse<Map<String, String>>> getGoogleMapsApiKey() {
        Map<String, String> data = new HashMap<>();
        data.put("apiKey", googleMapsApiKey);
        return ResponseEntity.ok(
            ApiResponse.success(data, "Google Maps API key retrieved")
        );
    }
}
