package com.example.ListArk.dto.raw.armory.avatar;

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

        @JsonProperty("IsSet")
        private boolean isSet;

        @JsonProperty("IsInner")
        private boolean isInner;

        @JsonProperty("Tooltip")
        private String tooltip;
    }
