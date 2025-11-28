package com.example.ListArk.Dto.raw.armory.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DecorationDto {

    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("Emblems")
    private List<String> emblems;
}

