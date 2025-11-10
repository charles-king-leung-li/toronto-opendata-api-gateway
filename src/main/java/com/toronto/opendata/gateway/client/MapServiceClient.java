package com.toronto.opendata.gateway.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.toronto.opendata.gateway.dto.GeoJsonFeatureCollectionDTO;
import com.toronto.opendata.gateway.dto.MapPointDTO;

@FeignClient(name = "map-service", url = "${business.service.url}")
public interface MapServiceClient {
    
    @GetMapping("/api/map/points")
    List<MapPointDTO> getAllMapPoints();
    
    @GetMapping("/api/map/geojson")
    GeoJsonFeatureCollectionDTO getGeoJson();
    
    @GetMapping("/api/map/points/bounds")
    List<MapPointDTO> getMapPointsInBounds(
            @RequestParam("minLat") Double minLat,
            @RequestParam("maxLat") Double maxLat,
            @RequestParam("minLon") Double minLon,
            @RequestParam("maxLon") Double maxLon
    );
    
    @GetMapping("/api/map/points/nearby")
    List<MapPointDTO> getNearbyMapPoints(
            @RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon,
            @RequestParam("radiusKm") Double radiusKm
    );
}
