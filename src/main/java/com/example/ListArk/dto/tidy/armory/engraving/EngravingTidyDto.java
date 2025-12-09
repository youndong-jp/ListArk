package com.example.ListArk.dto.tidy.armory.engraving;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 각인 정보 DTO (캐릭터 장착 각인)")
public class EngravingTidyDto {

    @Schema(
            description = "캐릭터가 장착한 각인 리스트",
            example = "[\"원한 Lv.3\", \"예리한 둔기 Lv.3\", \"아드레날린 Lv.3\", \"질량 증가 Lv.3\",\"돌격 대장 Lv.3\"]"
    )
    private List<String> engravings;
}
