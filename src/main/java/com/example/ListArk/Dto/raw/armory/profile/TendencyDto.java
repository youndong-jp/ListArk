package com.example.ListArk.Dto.raw.armory.profile;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

    @Data
    public class TendencyDto {

        @JsonProperty("Type")
        private String type;

        @JsonProperty("Point")
        private int point;

        @JsonProperty("MaxPoint")
        private int maxPoint;
    }

