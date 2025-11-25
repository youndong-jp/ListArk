package com.example.ListArk.Dto.armory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AvatarDto {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("Grade")
    private String grade;

    @JsonProperty("IsSetItem")
    private boolean isSetItem;

    @JsonProperty("Tooltip")
    private String tooltip;
}
