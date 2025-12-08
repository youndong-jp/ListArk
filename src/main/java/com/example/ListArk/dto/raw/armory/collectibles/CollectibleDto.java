package com.example.ListArk.dto.raw.armory.collectibles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

    @Data
    public class CollectibleDto {

        @JsonProperty("Type")
        private String type;

        @JsonProperty("Icon")
        private String icon;

        @JsonProperty("Point")
        private int point;

        @JsonProperty("MaxPoint")
        private int maxPoint;

        @JsonProperty("CollectiblePoints")
        private List<CollectiblePointDto> collectiblePoints;
    }
