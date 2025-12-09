package com.example.ListArk.dto.tidy.armory.arkgrid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "아크 그리드 슬롯 단일 정보")
public class ArkGridSlotTidyDto {

    @Schema(description = "슬롯 번호", example = "1")
    private int slot;

    @Schema(description = "슬롯 아이콘 URL", example = "https://cdn-lostark.game/arkgrid/icon_01.png")
    private String icon;

    @Schema(description = "코어 이름", example = "혼돈의 해 코어 : 신념의 강화")
    private String name;

    @Schema(description = "장착된 젬 질서 포인트", example = "20")
    private int point;

    @Schema(description = "코어 등급", example = "고대")
    private String grade;

    @Schema(description = "코어 타입 (damage / cooldown / special)", example = "damage")
    private String coreType;

    @Schema(description = "코어 의지력 수치", example = "7")
    private int willpower;

    @Schema(description = "코어 옵션 리스트")
    private List<CoreOptionDto> options;

    @Schema(description = "장착된 젬 정보 리스트")
    private List<ArkGridGemTidyDto> gems;
}
