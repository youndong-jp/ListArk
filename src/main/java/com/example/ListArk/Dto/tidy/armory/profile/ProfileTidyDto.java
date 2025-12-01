package com.example.ListArk.Dto.tidy.armory.profile;

import lombok.Data;
import java.util.Map;

@Data
public class ProfileTidyDto {

    private String characterName;
    private String characterClass;
    private int characterLevel;

    private String itemLevel;
    private String serverName;
    private String guildName;
    private String title;
    private String pvpGrade;

    private Map<String, Integer> stats;
    private Map<String, Integer> tendencies;

    private String characterImage;
}
