package com.example.ListArk.Dto.raw.armory.arkpassive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArkPassiveEffectDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Icon")
    private String icon;

    @JsonProperty("ToolTip")
    private String tooltip; // 주의: ToolTip 대소문자 맞춰야 함
}
