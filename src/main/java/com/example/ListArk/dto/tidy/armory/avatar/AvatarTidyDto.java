package com.example.ListArk.dto.tidy.armory.avatar;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 아바타 정보 DTO (이너/아우터/세트/효과 포함)")
public class AvatarTidyDto {

    @Schema(description = "아바타 타입", example = "무기 아바타")
    private String type;

    @Schema(description = "아바타 이름", example = "예견된 영원 한손검 (귀속)")
    private String name;

    @Schema(description = "아이콘 URL", example = "https://cdn-lostark.game/avatar/weapon_123.png")
    private String icon;

    @Schema(description = "등급", example = "전설")
    private String grade;

    @Schema(description = "세트 아바타 여부", example = "true")
    private Boolean isSet;

    @Schema(description = "이너 아바타 여부", example = "false")
    private Boolean isInner;

    // Tooltip 파싱 정보

    @Schema(description = "전용 직업 정보", example = "홀리나이트")
    private String exclusiveClass;

    @Schema(description = "귀속 여부", example = "true")
    private Boolean isBound;

    @Schema(description = "거래 가능 여부", example = "false")
    private Boolean isTradable;

    @Schema(description = "판매 가능 여부", example = "false")
    private Boolean isSellable;

    @Schema(description = "분해 가능 여부", example = "false")
    private Boolean isDecomposable;

    @Schema(
            description = "아바타 기본 효과 리스트",
            example = "[\"힘 +2.00%\", \"지능 +1.50%\"]"
    )
    private List<String> effects;

    // 성향

    @Schema(description = "지성", example = "10")
    private Integer intellect;

    @Schema(description = "담력", example = "5")
    private Integer courage;

    @Schema(description = "매력", example = "15")
    private Integer charm;

    @Schema(description = "친절", example = "8")
    private Integer kindness;
}
