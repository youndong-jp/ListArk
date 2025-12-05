package com.example.ListArk.mapper.armory.arkgrid;

import com.example.ListArk.Dto.tidy.armory.arkgrid.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 아크 그리드 Tooltip JSON을 파싱하여 TidyDto로 변환하는 파서
 *
 * 지원 타입:
 * - Slot (코어): 코어 타입, 의지력, 코어 옵션
 * - Gem (젬): 젬 타입, 포인트, 효과
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ArkGridTooltipParser {

    private final ObjectMapper objectMapper;

    // ============================================
    // 1. Slot Tooltip 파싱
    // ============================================

    /**
     * 슬롯(코어) Tooltip 파싱
     *
     * @param tooltip Tooltip JSON 문자열
     * @param dto 파싱 결과를 저장할 DTO
     */
    public void parseSlotTooltip(String tooltip, ArkGridSlotTidyDto dto) {
        if (tooltip == null || tooltip.isBlank()) {
            dto.setOptions(List.of());
            return;
        }

        try {
            JsonNode root = objectMapper.readTree(tooltip);

            // 모든 Element를 한 번만 순회하며 파싱
            root.fields().forEachRemaining(entry -> {
                JsonNode element = entry.getValue();

                // type이 없거나 ItemPartBox가 아니면 스킵
                if (!element.has("type")) return;
                if (!element.path("type").asText().equals("ItemPartBox")) return;

                String title = stripHtmlTags(element.path("value").path("Element_000").asText());

                // 제목으로 구분하여 파싱
                if (title.contains("코어 타입")) {
                    parseCoreType(element, dto);
                } else if (title.contains("코어 공급 의지력")) {
                    parseWillpower(element, dto);
                } else if (title.contains("코어 옵션")) {
                    parseCoreOptions(element, dto);
                }
            });

        } catch (Exception e) {
            log.error("슬롯 tooltip 파싱 중 에러 발생: {}", e.getMessage(), e);
            dto.setOptions(List.of());
        }
    }

    /**
     * 코어 타입 파싱 (예: "질서 - 해")
     */
    private void parseCoreType(JsonNode element, ArkGridSlotTidyDto dto) {
        String text = element.path("value").path("Element_001").asText();
        if (!text.isBlank()) {
            dto.setCoreType(stripHtmlTags(text));
        }
    }

    /**
     * 의지력 파싱 (예: "15 포인트")
     */
    private void parseWillpower(JsonNode element, ArkGridSlotTidyDto dto) {
        String html = element.path("value").path("Element_001").asText();
        if (html.isBlank()) return;

        Pattern pattern = Pattern.compile(">(\\d+)</FONT>\\s*포인트");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            dto.setWillpower(Integer.parseInt(matcher.group(1)));
        }
    }

    /**
     * 코어 옵션 목록 파싱
     * [10P] 피해 증가, [14P] 특수 효과 등
     */
    private void parseCoreOptions(JsonNode element, ArkGridSlotTidyDto dto) {
        String html = element.path("value").path("Element_001").asText();

        if (html.isBlank()) {
            dto.setOptions(List.of());
            return;
        }

        List<CoreOptionDto> options = new ArrayList<>();

        // <BR> 태그를 줄바꿈으로 변환
        html = html.replaceAll("(?i)<br>", "\n");
        String cleanText = stripHtmlTags(html);

        // [NP] 패턴으로 파싱
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

    /**
     * 옵션 타입 및 수치 파싱
     */
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

    /**
     * 젬 Tooltip 파싱
     *
     * @param tooltip Tooltip JSON 문자열
     * @param dto 파싱 결과를 저장할 DTO
     */
    public void parseGemTooltip(String tooltip, ArkGridGemTidyDto dto) {
        if (tooltip == null || tooltip.isBlank()) {
            dto.setEffects(List.of());
            return;
        }

        try {
            JsonNode root = objectMapper.readTree(tooltip);

            // 모든 Element를 순회하며 파싱
            root.fields().forEachRemaining(entry -> {
                JsonNode element = entry.getValue();

                if (!element.has("type")) return;
                String type = element.path("type").asText();

                switch (type) {
                    case "NameTagBox":
                        parseGemName(element, dto);
                        break;
                    case "ItemPartBox":
                        parseGemItemPartBox(element, dto);
                        break;
                }
            });

        } catch (Exception e) {
            log.error("젬 tooltip 파싱 중 에러 발생: {}", e.getMessage(), e);
            dto.setEffects(List.of());
        }
    }

    /**
     * 젬 이름 파싱
     */
    private void parseGemName(JsonNode element, ArkGridGemTidyDto dto) {
        String html = element.path("value").asText();
        if (!html.isBlank()) {
            dto.setName(stripHtmlTags(html));
        }
    }

    /**
     * 젬 ItemPartBox 파싱 (기본 정보 또는 효과)
     */
    private void parseGemItemPartBox(JsonNode element, ArkGridGemTidyDto dto) {
        String title = stripHtmlTags(element.path("value").path("Element_000").asText());

        if (title.contains("젬 기본 정보")) {
            parseGemBasicInfo(element, dto);
        } else if (title.contains("젬 효과")) {
            parseGemEffects(element, dto);
        }
    }

    /**
     * 젬 기본 정보 파싱 (타입, 포인트)
     */
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

    /**
     * 젬 효과 파싱
     */
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

    /**
     * 젬 효과 목록 파싱
     * [효과명] Lv.N +수치% 형태
     */
    private List<GemEffectDto> parseGemEffectList(String html) {
        List<GemEffectDto> effects = new ArrayList<>();

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

    // ========================================
    // 유틸리티: HTML 태그 제거
    // ========================================

    /**
     * HTML 태그 및 특수 문자 제거
     *
     * @param html HTML이 포함된 문자열
     * @return 순수 텍스트
     */
    private String stripHtmlTags(String html) {
        if (html == null) return null;

        return html
                .replaceAll("<[^>]*>", "")           // HTML 태그 제거
                .replace("&nbsp;", " ")               // 공백 문자 변환
                .trim();
    }
}