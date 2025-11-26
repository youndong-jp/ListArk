package com.example.ListArk.Dto.tidy;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ProfileTidyDto {
    private String characterName;
    private String characterClassName;
    private String serverName;

    private int characterLevel;
    private String itemLevel;

    private String title;
    private String guildName;

    private String pvpGradeName;
    private int pvpLevel;

    private Map<String, Integer> stats;
    private List<String> engravings;
}
