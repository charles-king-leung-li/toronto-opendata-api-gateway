package com.toronto.opendata.gateway.dto;

import lombok.Data;

@Data
public class MultiPointDTO {
    private double x;
    private double y;
    private String type;
}
