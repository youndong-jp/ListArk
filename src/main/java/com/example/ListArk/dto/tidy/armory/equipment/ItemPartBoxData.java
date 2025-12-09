package com.example.ListArk.dto.tidy.armory.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 장비의 효과 정보를 담는 DTO
 */
@Data
@Schema(description = "Tidy 장비 효과 정보 (기본/추가/연마/아크 패시브 등)")
public class ItemPartBoxData {

    @Schema(
            description = "효과 제목",
            example = "기본 효과"
    )
    private String title;

    @Schema(
            description = "효과 내용 (여러 줄 포함 가능)",
            example = "무기 공격력 +151014\n힘 +78607\n체력 +9200"
    )
    private String content;
}
