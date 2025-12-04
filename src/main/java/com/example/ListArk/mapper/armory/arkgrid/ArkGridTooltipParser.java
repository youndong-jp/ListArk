package com.example.ListArk.mapper.armory.arkgrid;

import com.example.ListArk.Dto.tidy.armory.arkgrid.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ArkGridTooltipParser {

    private final ObjectMapper objectMapper;

    public ArkGridTooltipParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // ============================================
    // 1. Slot Tooltip 파싱
    // ============================================

    public void parseSlotTooltip(String tooltip, ArkGridSlotTidyDto dto) {
        if (tooltip == null || tooltip.isBlank()) {
            dto.setOptions(List.of());
            return;
        }

        try {
            JsonNode root = objectMapper.readTree(tooltip);

            // Element 번호가 다를 수 있으므로 텍스트로 찾기
            parseCoreTypeByText(root, dto);
            parseWillpowerByText(root, dto);
            parseCoreOptionsByText(root, dto);

        } catch (Exception e) {
            dto.setOptions(List.of());
        }
    }

    private void parseCoreTypeByText(JsonNode root, ArkGridSlotTidyDto dto) {
        for (int i = 0; i <= 12; i++) {
            JsonNode element = root.path("Element_" + String.format("%03d", i));
            if (element.has("value")) {
                String title = element.path("value").path("Element_000").asText();
                if (title.contains("코어 타입")) {
                    parseCoreType(element, dto);
                    return;
                }
            }
        }
    }

    private void parseWillpowerByText(JsonNode root, ArkGridSlotTidyDto dto) {
        for (int i = 0; i <= 12; i++) {
            JsonNode element = root.path("Element_" + String.format("%03d", i));
            if (element.has("value")) {
                String title = element.path("value").path("Element_000").asText();
                if (title.contains("코어 공급 의지력")) {
                    parseWillpower(element, dto);
                    return;
                }
            }
        }
    }

    private void parseCoreOptionsByText(JsonNode root, ArkGridSlotTidyDto dto) {
        for (int i = 0; i <= 12; i++) {
            JsonNode element = root.path("Element_" + String.format("%03d", i));
            if (element.has("value")) {
                String title = element.path("value").path("Element_000").asText();
                if (title.contains("코어 옵션")) {
                    parseCoreOptions(element, dto);
                    return;
                }
            }
        }
        dto.setOptions(List.of());
    }

    private void parseCoreType(JsonNode element, ArkGridSlotTidyDto dto) {
        String text = element.path("value").path("Element_001").asText();
        if (!text.isBlank()) {
            String coreType = text.replaceAll("<[^>]*>", "").trim();
            dto.setCoreType(coreType);
        }
    }

    private void parseWillpower(JsonNode element, ArkGridSlotTidyDto dto) {
        String html = element.path("value").path("Element_001").asText();
        if (html.isBlank()) return;

        Pattern pattern = Pattern.compile(">(\\d+)</FONT>\\s*포인트");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            dto.setWillpower(Integer.parseInt(matcher.group(1)));
        }
    }

    private void parseCoreOptions(JsonNode element, ArkGridSlotTidyDto dto) {
        String html = element.path("value").path("Element_001").asText();

        if (html.isBlank()) {
            dto.setOptions(List.of());
            return;
        }

        List<CoreOptionDto> options = new ArrayList<>();

        // 1. <br> → 줄바꿈으로 변경
        html = html.replaceAll("(?i)<br>", "\n");

        // 2. 모든 HTML 태그 제거
        String cleanText = html.replaceAll("<[^>]*>", "");

        // 3. [NP] 패턴으로 파싱
        Pattern pointPattern = Pattern.compile("\\[(\\d+)P\\]([^\n\\[]+)");
        Matcher matcher = pointPattern.matcher(cleanText);

        while (matcher.find()) {
            int point = Integer.parseInt(matcher.group(1));
            String description = matcher.group(2).trim();

            CoreOptionDto option = new CoreOptionDto();
            option.setPoint(point);
            option.setDescription(description);
            parseOptionTypeAndValue(description, option);

            options.add(option);
        }

        dto.setOptions(options);
    }

    private void parseOptionTypeAndValue(String description, CoreOptionDto option) {
        // 피해 증가
        Pattern damagePattern = Pattern.compile("피해.*?([0-9.]+)%\\s*증가");
        Matcher damageMatcher = damagePattern.matcher(description);
        if (damageMatcher.find()) {
            option.setType("damage");
            option.setValue(Double.parseDouble(damageMatcher.group(1)));
            return;
        }

        // 재사용 대기 시간 감소
        Pattern cooldownPattern = Pattern.compile("재사용\\s*대기\\s*시간.*?([0-9.]+)%\\s*감소");
        Matcher cooldownMatcher = cooldownPattern.matcher(description);
        if (cooldownMatcher.find()) {
            option.setType("cooldown");
            option.setValue(Double.parseDouble(cooldownMatcher.group(1)));
            return;
        }

        // 특수 효과
        option.setType("special");
        option.setValue(null);
    }

    // ============================================
    // 2. Gem Tooltip 파싱
    // ============================================

    public void parseGemTooltip(String tooltip, ArkGridGemTidyDto dto) {
        if (tooltip == null || tooltip.isBlank()) {
            dto.setEffects(List.of());
            return;
        }

        try {
            JsonNode root = objectMapper.readTree(tooltip);

            parseGemName(root.path("Element_000"), dto);
            parseGemBasicInfo(root.path("Element_004"), dto);
            parseGemEffects(root.path("Element_005"), dto);

        } catch (Exception e) {
            dto.setEffects(List.of());
        }
    }

    private void parseGemName(JsonNode element, ArkGridGemTidyDto dto) {
        String html = element.path("value").asText();
        if (!html.isBlank()) {
            String name = html.replaceAll("<[^>]*>", "").trim();
            dto.setName(name);
        }
    }

    private void parseGemBasicInfo(JsonNode element, ArkGridGemTidyDto dto) {
        String text = element.path("value").path("Element_001").asText();
        if (text.isBlank()) return;

        // 젬 타입
        Pattern typePattern = Pattern.compile("젬\\s*타입\\s*:\\s*([^<\\r\\n]+)");
        Matcher typeMatcher = typePattern.matcher(text);
        if (typeMatcher.find()) {
            dto.setGemType(typeMatcher.group(1).trim());
        }

        // 젬 포인트
        Pattern pointPattern = Pattern.compile("젬\\s*포인트\\s*:.*?>(\\d+)</FONT>");
        Matcher pointMatcher = pointPattern.matcher(text);
        if (pointMatcher.find()) {
            dto.setGemPoint(Integer.parseInt(pointMatcher.group(1)));
        }
    }

    private void parseGemEffects(JsonNode element, ArkGridGemTidyDto dto) {
        String html = element.path("value").path("Element_001").asText();

        if (html.isBlank()) {
            dto.setEffects(List.of());
            return;
        }

        // 필요 의지력
        Pattern willpowerPattern = Pattern.compile("필요\\s*의지력\\s*:.*?'>(\\d+)</FONT>");
        Matcher willpowerMatcher = willpowerPattern.matcher(html);
        if (willpowerMatcher.find()) {
            dto.setRequiredWillpower(Integer.parseInt(willpowerMatcher.group(1)));
        }

        // 질서/혼돈/파괴 포인트
        Pattern orderPattern = Pattern.compile("(질서|혼돈|파괴)\\s*포인트\\s*:.*?'>(\\d+)</FONT>");
        Matcher orderMatcher = orderPattern.matcher(html);
        if (orderMatcher.find()) {
            dto.setOrderPoint(Integer.parseInt(orderMatcher.group(2)));
        }

        // 효과 목록
        List<GemEffectDto> effects = parseGemEffectList(html);
        dto.setEffects(effects);
    }

    private List<GemEffectDto> parseGemEffectList(String html) {
        List<GemEffectDto> effects = new ArrayList<>();

        // [효과명] Lv.N ... +수치% 패턴
        // <br> 다음에 <img> 태그가 있을 수 있음
        Pattern pattern = Pattern.compile(
                "\\[([^\\]]+)\\]\\s*<FONT[^>]*>Lv\\.(\\d+)</FONT>.*?\\+([0-9.]+)%"
        );
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String name = matcher.group(1).trim();
            int level = Integer.parseInt(matcher.group(2));
            double value = Double.parseDouble(matcher.group(3));

            GemEffectDto effect = new GemEffectDto();
            effect.setName(name);
            effect.setLevel(level);
            effect.setValue(value);
            effect.setDescription(String.format("%s +%.2f%%", name, value));

            effects.add(effect);
        }

        return effects;
    }
}