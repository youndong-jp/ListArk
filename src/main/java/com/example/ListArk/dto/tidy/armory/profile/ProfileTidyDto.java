package com.example.ListArk.dto.tidy.armory.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "Tidy 프로필 정보 DTO")
public class ProfileTidyDto {

    @Schema(description = "캐릭터 이름", example = "니나브")
    private String characterName;

    @Schema(description = "캐릭터 클래스명", example = "블레이드")
    private String characterClass;

    @Schema(description = "계정 내 캐릭터 레벨(Combat Level)", example = "60")
    private int characterLevel;

    @Schema(description = "아이템 레벨", example = "1630.00")
    private String itemLevel;

    @Schema(description = "캐릭터가 소속된 서버명", example = "루페온")
    private String serverName;

    @Schema(description = "길드명", example = "로스트아크")
    private String guildName;

    @Schema(description = "칭호", example = "로스트아크의 영웅")
    private String title;

    @Schema(description = "PVP 등급", example = "실버")
    private String pvpGrade;

    @Schema(
            description = "전투 특성 (치명 / 특화 / 제압 / 신속 / 인내 / 숙련)",
            example = "{ \"치명\": 716, \"특화\": 602, \"신속\": 504 }"
    )
    private Map<String, Integer> stats;

    @Schema(
            description = "성향 (지성 / 담력 / 매력 / 친절)",
            example = "{ \"지성\": 270, \"담력\": 320, \"매력\": 350, \"친절\": 290 }"
    )
    private Map<String, Integer> tendencies;

    @Schema(
            description = "캐릭터 대표 이미지 URL",
            example = "https://img.lostark.co.kr/profile/abcdef12345.png"
    )
    private String characterImage;
}
