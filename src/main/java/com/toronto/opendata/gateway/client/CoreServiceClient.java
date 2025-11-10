package com.toronto.opendata.gateway.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.toronto.opendata.gateway.dto.CulturalHotSpotDTO;

@FeignClient(name = "core-service", url = "${business.service.url}")
public interface CoreServiceClient {
    
    @GetMapping("/api/cultural-hotspots")
    List<CulturalHotSpotDTO> getAllCulturalHotSpots();
    
    @GetMapping("/api/cultural-hotspots/{id}")
    CulturalHotSpotDTO getCulturalHotSpotById(@PathVariable("id") String id);
}
