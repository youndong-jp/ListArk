package com.example.ListArk.dto.tidy.armory.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 계층 구조를 가진 복잡한 효과 정보를 담는 DTO
 */
@Data
@Schema(description = "Tidy 슬롯 효과 정보 (초월/엘릭서/각인 등)")
public class IndentStringData {

    @Schema(
            description = "효과 제목",
            example = "슬롯 효과[초월] 7단계 21"
    )
    private String title;

    @Schema(
            description = "효과 상세 내용 (여러 줄 포함 가능)",
            example = "무기 공격력 +2940\n총 126개 장비에 적용됨\n5스택 - 공격력이 800 증가"
    )
    private String text;
}
