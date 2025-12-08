package com.example.ListArk.dto.raw.armory.collectibles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


    @Data
    public class CollectiblePointDto {

        @JsonProperty("PointName")
        private String pointName;

        @JsonProperty("Point")
        private int point;

        @JsonProperty("MaxPoint")
        private int maxPoint;
    }

