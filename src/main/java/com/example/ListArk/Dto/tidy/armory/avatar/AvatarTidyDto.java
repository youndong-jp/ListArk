package com.example.ListArk.Dto.tidy.armory.avatar;

import lombok.Data;

@Data
public class AvatarTidyDto {
    private String type;
    private String name;
    private String icon;
    private String grade;

    private boolean isSet;
    private boolean isInner;

    private String tooltip;
}
