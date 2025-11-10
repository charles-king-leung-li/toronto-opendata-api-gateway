package com.toronto.opendata.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.toronto.opendata.gateway.client.MapServiceClient;
import com.toronto.opendata.gateway.dto.ApiResponse;
import com.toronto.opendata.gateway.dto.GeoJsonFeatureCollectionDTO;
import com.toronto.opendata.gateway.dto.MapPointDTO;

@RestController
@RequestMapping("/api/map")
@Tag(name = "Map", description = "Endpoints for map visualization of Toronto cultural hotspots")
public class MapController {
    
    private final MapServiceClient mapServiceClient;
    
    @Autowired
    public MapController(MapServiceClient mapServiceClient) {
        this.mapServiceClient = mapServiceClient;
    }
    
    @Operation(
        summary = "Get all map points",
        description = "Returns all cultural hotspots with coordinates as simple map points"
    )
    @GetMapping("/points")
    public ResponseEntity<ApiResponse<List<MapPointDTO>>> getAllMapPoints() {
        List<MapPointDTO> points = mapServiceClient.getAllMapPoints();
        return ResponseEntity.ok(
            ApiResponse.success(points, "Retrieved " + points.size() + " map points")
        );
    }
    
    @Operation(
        summary = "Get GeoJSON FeatureCollection",
        description = "Returns cultural hotspots in GeoJSON format compatible with Leaflet, Mapbox, Google Maps, etc."
    )
    @GetMapping("/geojson")
    public ResponseEntity<ApiResponse<GeoJsonFeatureCollectionDTO>> getGeoJson() {
        GeoJsonFeatureCollectionDTO geoJson = mapServiceClient.getGeoJson();
        return ResponseEntity.ok(
            ApiResponse.success(geoJson, "Retrieved GeoJSON data with " + geoJson.getFeatures().size() + " features")
        );
    }
    
    @Operation(
        summary = "Get map points within bounding box",
        description = "Returns cultural hotspots within the specified geographic bounds"
    )
    @GetMapping("/points/bounds")
    public ResponseEntity<ApiResponse<List<MapPointDTO>>> getMapPointsInBounds(
            @Parameter(description = "Minimum latitude") @RequestParam Double minLat,
            @Parameter(description = "Maximum latitude") @RequestParam Double maxLat,
            @Parameter(description = "Minimum longitude") @RequestParam Double minLon,
            @Parameter(description = "Maximum longitude") @RequestParam Double maxLon
    ) {
        List<MapPointDTO> points = mapServiceClient.getMapPointsInBounds(minLat, maxLat, minLon, maxLon);
        return ResponseEntity.ok(
            ApiResponse.success(points, "Retrieved " + points.size() + " points within bounds")
        );
    }
    
    @Operation(
        summary = "Get nearby map points",
        description = "Returns cultural hotspots within specified radius (in kilometers) from a center point"
    )
    @GetMapping("/points/nearby")
    public ResponseEntity<ApiResponse<List<MapPointDTO>>> getNearbyMapPoints(
            @Parameter(description = "Center latitude") @RequestParam Double lat,
            @Parameter(description = "Center longitude") @RequestParam Double lon,
            @Parameter(description = "Radius in kilometers", example = "5.0") @RequestParam(defaultValue = "5.0") Double radiusKm
    ) {
        List<MapPointDTO> points = mapServiceClient.getNearbyMapPoints(lat, lon, radiusKm);
        return ResponseEntity.ok(
            ApiResponse.success(points, "Retrieved " + points.size() + " points within " + radiusKm + "km radius")
        );
    }
}
