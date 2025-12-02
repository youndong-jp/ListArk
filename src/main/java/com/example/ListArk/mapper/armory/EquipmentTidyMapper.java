package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.equipment.EquipmentDto;
import com.example.ListArk.Dto.tidy.armory.equipment.EquipmentTidyDto;
import com.example.ListArk.mapper.NullSafe;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipmentTidyMapper {

    private final ObjectMapper objectMapper;

    public EquipmentTidyMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<EquipmentTidyDto> toTidy(ArmoryDto raw) {

        List<EquipmentDto> equipments = NullSafe.get(
                raw::getArmoryEquipment,
                List.of()
        );

        return equipments.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private EquipmentTidyDto convert(EquipmentDto e) {

        EquipmentTidyDto dto = new EquipmentTidyDto();

        dto.setSlot(NullSafe.get(e::getType, ""));
        dto.setName(NullSafe.get(e::getName, ""));
        dto.setIcon(NullSafe.get(e::getIcon, ""));
        dto.setGrade(NullSafe.get(e::getGrade, ""));

        String tooltip = NullSafe.get(e::getTooltip, "");
        dto.setTooltip(tooltip);

        // 🎯 JSON 1번만 파싱해서 재활용
        JsonNode root = parseTooltip(tooltip);

        dto.setQuality(extractQuality(root));
        dto.setItemLevel(extractItemLevel(root));

        return dto;
    }

    /** Tooltip JSON 안전 파싱 */
    private JsonNode parseTooltip(String tooltip) {
        try {
            return objectMapper.readTree(tooltip);
        } catch (Exception e) {
            return null;
        }
    }

        private String extractQuality(JsonNode root) {
            if (root == null) return "0";

            // path()가 이미 안전하므로 try-catch 제거
            return root.path("Element_001")
                    .path("value")
                    .path("qualityValue")
                    .asText("0");
        }

        private String extractItemLevel(JsonNode root) {
            if (root == null) return "0";

            String raw = root.path("Element_001")
                    .path("value")
                    .path("leftStr0")
                    .asText("");

            // 빈 문자열이면 "0" 반환
            return raw.isEmpty() ? "0" : raw.replaceAll("[^0-9]", "");
        }
}

