package com.example.ListArk.dto.tidy.armory.arkpassive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "아크 패시브 효과 정보 DTO")
public class ArkPassiveEffectTidyDto {

    @Schema(description = "효과가 속하는 계열 이름", example = "깨달음")
    private String name;

    @Schema(
            description = "효과 설명 (HTML 포함)",
            example = "<FONT color='#83E9FF'>깨달음</FONT> 1티어 <FONT color='#83E9FF'>신성 보호 Lv.1</FONT>"
    )
    private String description;

    @Schema(
            description = "효과 아이콘 URL",
            example = "https://cdn-lostark.game.onstove.com/efui_iconatlas/ark_passive_hk/ark_passive_hk_1.png"
    )
    private String icon;
}
