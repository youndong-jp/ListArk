package com.example.ListArk.dto.tidy.armory.engraving;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Tidy 각인 정보 전체 DTO")
public class EngravingTidyDto {

    @Schema(
            description = "각인 상세 정보 목록",
            example = "[\n" +
                    "  {\n" +
                    "    \"name\": \"원한\",\n" +
                    "    \"level\": 3,\n" +
                    "    \"stoneLevel\": 1,\n" +
                    "    \"grade\": \"유물\",\n" +
                    "    \"description\": \"보스에게 주는 피해가 21% 증가하지만 받는 피해가 20% 증가한다.\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"name\": \"기습의 대가\",\n" +
                    "    \"level\": 3,\n" +
                    "    \"stoneLevel\": null,\n" +
                    "    \"grade\": \"유물\",\n" +
                    "    \"description\": \"백어택 시 피해량이 증가한다.\"\n" +
                    "  }\n" +
                    "]"
    )
    private List<EngravingDetailDto> engravings;
}
