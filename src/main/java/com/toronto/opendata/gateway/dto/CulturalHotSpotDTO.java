package com.toronto.opendata.gateway.dto;

import lombok.Data;

@Data
public class CulturalHotSpotDTO {
    private String id;
    private String name;
    private String address;
    private String type;
    private String description;
    private String pictureURL;
    private MultiPointDTO location;
}
