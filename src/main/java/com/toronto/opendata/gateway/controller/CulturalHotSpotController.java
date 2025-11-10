package com.toronto.opendata.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.toronto.opendata.gateway.client.CoreServiceClient;
import com.toronto.opendata.gateway.dto.ApiResponse;
import com.toronto.opendata.gateway.dto.CulturalHotSpotDTO;

@RestController
@RequestMapping("/api/cultural-hotspots")
@Tag(name = "Cultural Hotspots", description = "Cultural Hotspots API - BFF Layer")
public class CulturalHotSpotController {
    
    private final CoreServiceClient coreServiceClient;
    
    @Autowired
    public CulturalHotSpotController(CoreServiceClient coreServiceClient) {
        this.coreServiceClient = coreServiceClient;
    }

    @Operation(
        summary = "Get All Cultural Hotspots",
        description = "Returns all cultural hotspot points of interest from Toronto Open Data"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<CulturalHotSpotDTO>>> getAllCulturalHotSpots() {
        List<CulturalHotSpotDTO> hotSpots = coreServiceClient.getAllCulturalHotSpots();
        return ResponseEntity.ok(
            ApiResponse.success(hotSpots, "Retrieved " + hotSpots.size() + " cultural hotspots")
        );
    }

    @Operation(
        summary = "Get Cultural Hotspot by ID",
        description = "Returns a specific cultural hotspot by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CulturalHotSpotDTO>> getCulturalHotSpotById(@PathVariable String id) {
        try {
            CulturalHotSpotDTO hotSpot = coreServiceClient.getCulturalHotSpotById(id);
            if (hotSpot != null) {
                return ResponseEntity.ok(
                    ApiResponse.success(hotSpot, "Cultural hotspot retrieved successfully")
                );
            } else {
                return ResponseEntity.status(404).body(
                    ApiResponse.error("Cultural hotspot not found with id: " + id)
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                ApiResponse.error("Cultural hotspot not found with id: " + id)
            );
        }
    }
}
