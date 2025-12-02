package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.gem.ArmoryGemDto;
import com.example.ListArk.Dto.raw.armory.gem.GemDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemEffectTidyDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemSimpleDto;
import com.example.ListArk.Dto.tidy.armory.gem.GemTidyDto;
import com.example.ListArk.mapper.NullSafe;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GemTidyMapper {

    private final ObjectMapper objectMapper;

    public GemTidyMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GemTidyDto toTidy(ArmoryDto raw) {

        GemTidyDto dto = new GemTidyDto();

        ArmoryGemDto gemRoot = NullSafe.get(raw::getArmoryGem);
        if (gemRoot == null) {
            dto.setGems(List.of());
            dto.setEffects(List.of());
            return dto;
        }

        List<GemDto> gems = NullSafe.get(gemRoot::getGems, List.of());

        // 한 번만 순회하면서 두 리스트 모두 생성
        List<GemSimpleDto> gemSimpleList = new ArrayList<>();
        List<GemEffectTidyDto> gemEffectList = new ArrayList<>();

        for (GemDto gem : gems) {
            gemSimpleList.add(convertToSimple(gem));
            gemEffectList.add(convertToEffect(gem));
        }

        dto.setGems(gemSimpleList);
        dto.setEffects(gemEffectList);

        return dto;
    }

    /** Gem → 기본 정보 DTO 변환 */
    private GemSimpleDto convertToSimple(GemDto g) {

        GemSimpleDto dto = new GemSimpleDto();

        dto.setSlot(NullSafe.get(g::getSlot, 0));
        dto.setType(extractGemType(NullSafe.get(g::getName, "")));
        dto.setLevel(NullSafe.get(g::getLevel, 0));
        dto.setIcon(NullSafe.get(g::getIcon, ""));

        return dto;
    }

    /** Gem → 효과 정보 DTO 변환 */
    private GemEffectTidyDto convertToEffect(GemDto g) {
        GemEffectTidyDto dto = new GemEffectTidyDto();

        String tooltip = NullSafe.get(g::getTooltip, "");
        JsonNode root = parseTooltip(tooltip);

        // effectText를 한 번만 추출해서 재사용
        String effectText = extractEffectText(root);
        String skill = extractSkill(root);

        dto.setText(effectText);
        dto.setSkill(skill);
        dto.setEffectType(extractEffectTypeFromText(effectText));
        dto.setValue(extractEffectValueFromText(effectText));

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

    /** "멸화의 보석" → "멸화" */
    private String extractGemType(String name) {
        if (name.contains("멸화")) return "멸화";
        if (name.contains("홍염")) return "홍염";
        return name;
    }

    /** Tooltip에서 전체 효과 텍스트 추출 */
    private String extractEffectText(JsonNode root) {
        if (root == null) return "";
        return root.path("Element_000")
                .path("value")
                .path("Element_000")
                .asText("");
    }

    /** Tooltip에서 스킬명 추출 */
    private String extractSkill(JsonNode root) {
        if (root == null) return "";
        return root.path("Element_000")
                .path("value")
                .path("Element_005")
                .asText("");
    }

    /** 텍스트에서 효과 타입 추출 (멸화=damage, 홍염=cooldown) */
    private String extractEffectTypeFromText(String text) {
        if (text.contains("피해")) return "damage";
        if (text.contains("재사용")) return "cooldown";
        return "";
    }

    /** 텍스트에서 "20%" → 20 추출 */
    private int extractEffectValueFromText(String text) {
        String digits = text.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return 0;

        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}