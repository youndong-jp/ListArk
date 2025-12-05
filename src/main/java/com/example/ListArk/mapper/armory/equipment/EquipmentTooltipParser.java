package com.example.ListArk.mapper.armory.equipment;

import com.example.ListArk.Dto.tidy.armory.equipment.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 장비 Tooltip JSON을 파싱하여 EquipmentTidyDto로 변환하는 파서
 *
 * Lost Ark API의 Tooltip은 Element_XXX 형태의 계층 구조로 되어있으며,
 * 각 Element는 type(타입)과 value(값)를 가지고 있습니다.
 *
 * 지원하는 Element 타입:
 * - NameTagBox: 아이템 이름 (강화 단계 포함)
 * - ItemTitle: 기본 정보 (등급, 품질, 레벨, 아이콘 등)
 * - SingleTextBox: 단일 텍스트 (제한 정보, 귀속 정보 등)
 * - MultiTextBox: 다중 텍스트 (거래 정보)
 * - ItemPartBox: 효과 정보 (기본/추가/연마 효과 등)
 * - IndentStringGroup: 계층 효과 (초월, 엘릭서, 각인 등)
 * - ShowMeTheMoney: 내구도 정보
 *
 * @see EquipmentTidyDto 파싱 결과가 저장되는 DTO
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EquipmentTooltipParser {

    private final ObjectMapper objectMapper;

    /**
     * Tooltip JSON 문자열을 파싱하여 EquipmentTidyDto에 데이터 설정
     *
     * @param tooltipJson Tooltip JSON 문자열
     * @param dto 파싱 결과를 저장할 DTO
     * @throws Exception JSON 파싱 실패 시
     */
    public void parseAndSetTooltip(String tooltipJson, EquipmentTidyDto dto) throws Exception {

        JsonNode root = objectMapper.readTree(tooltipJson);

        // Element_XXX 순회하면서 type별로 처리
        root.fields().forEachRemaining(entry -> {
            JsonNode element = entry.getValue();

            // null이거나 type이 없는 경우 스킵
            if (element == null || element.isNull()) return;
            if (!element.has("type")) return;

            String type = element.get("type").asText();
            JsonNode value = element.get("value");

            // value가 null인 경우 스킵
            if (value == null || value.isNull()) return;

            parseByType(type, value, dto);
        });
    }

    /**
     * Element type에 따라 적절한 파싱 메소드 호출
     *
     * @param type Element 타입
     * @param value Element 값
     * @param dto 파싱 결과를 저장할 DTO
     */
    private void parseByType(String type, JsonNode value, EquipmentTidyDto dto) {
        switch (type) {
            case "NameTagBox":
                parseNameTag(value, dto);
                break;
            case "ItemTitle":
                parseItemTitle(value, dto);
                break;
            case "SingleTextBox":
                parseSingleTextBox(value, dto);
                break;
            case "MultiTextBox":
                parseMultiTextBox(value, dto);
                break;
            case "ItemPartBox":
                parseItemPartBox(value, dto);
                break;
            case "IndentStringGroup":
                parseIndentStringGroup(value, dto);
                break;
            case "ShowMeTheMoney":
                parseShowMeTheMoney(value, dto);
                break;
            default:
                log.debug("알 수 없는 Element 타입: {}", type);
        }
    }

    // ========================================
    // NameTagBox: 아이템 표시 이름
    // ========================================
    /**
     * NameTagBox 파싱 (현재는 사용하지 않음)
     * 기본 name 필드에 이미 동일한 정보가 있음
     */
    private void parseNameTag(JsonNode value, EquipmentTidyDto dto) {
        // displayName은 현재 사용하지 않으므로 주석 처리
        // dto.setDisplayName(stripHtmlTags(value.asText()));
    }

    // ========================================
    // ItemTitle: 기본 정보 (레벨, 품질, 장착 상태 등)
    // ========================================
    /**
     * ItemTitle 파싱
     * - leftStr2: 아이템 레벨 정보
     * - qualityValue: 품질 수치
     * - rightStr0: 장착 상태
     * - slotData.iconPath: 아이콘 경로 (기존 icon이 없을 경우)
     */
    private void parseItemTitle(JsonNode value, EquipmentTidyDto dto) {

        // 아이템 레벨
        if (value.has("leftStr2")) {
            String level = stripHtmlTags(value.get("leftStr2").asText());
            dto.setItemLevel(level);
        }

        // 품질 수치
        if (value.has("qualityValue")) {
            dto.setQualityValue(value.get("qualityValue").asInt());
        }

        // 장착 상태
        if (value.has("rightStr0")) {
            dto.setEquipStatus(stripHtmlTags(value.get("rightStr0").asText()));
        }

        // 아이콘 경로 (기존 icon이 없을 경우 대비)
        if (value.has("slotData") && value.get("slotData").has("iconPath")) {
            String iconPath = value.get("slotData").get("iconPath").asText();
            if (dto.getIcon() == null || dto.getIcon().isEmpty()) {
                dto.setIcon(iconPath);
            }
        }
    }

    // ========================================
    // SingleTextBox: 제한/귀속 정보
    // ========================================
    /**
     * SingleTextBox 파싱
     * 직업 전용, 캐릭터 귀속, 재련 단계, 분해 불가 등의 정보
     */
    private void parseSingleTextBox(JsonNode value, EquipmentTidyDto dto) {
        dto.getRestrictionInfoList().add(stripHtmlTags(value.asText()));
    }

    // ========================================
    // MultiTextBox: 거래 정보
    // ========================================
    /**
     * MultiTextBox 파싱
     * 거래 가능/불가 정보
     */
    private void parseMultiTextBox(JsonNode value, EquipmentTidyDto dto) {
        dto.getTradeInfoList().add(stripHtmlTags(value.asText()));
    }

    // ========================================
    // ItemPartBox: 효과 정보
    // ========================================
    /**
     * ItemPartBox 파싱
     * 다양한 효과 정보를 담고 있음:
     * - 무기/방어구: 기본 효과, 추가 효과, 장비 업그레이드 효과
     * - 장신구: 기본 효과, 연마 효과, 아크 패시브
     * - 어빌리티 스톤: 기본 효과, 세공 단계 보너스
     * - 팔찌: 팔찌 효과, 아크 패시브
     * - 보주: 특수 효과
     */
    private void parseItemPartBox(JsonNode value, EquipmentTidyDto dto) {

        ItemPartBoxData data = new ItemPartBoxData();

        // 효과 제목 (Element_000)
        if (value.has("Element_000")) {
            data.setTitle(stripHtmlTags(value.get("Element_000").asText()));
        }

        // 효과 내용 (Element_001)
        if (value.has("Element_001")) {
            String content = value.get("Element_001").asText();
            // <BR> 태그를 줄바꿈으로 변환 후 HTML 태그 제거
            data.setContent(stripHtmlTags(content.replace("<BR>", "\n")));
        }

        dto.getEffectList().add(data);
    }

    // ========================================
    // IndentStringGroup: 계층 효과 (초월, 엘릭서, 각인 등)
    // ========================================
    /**
     * IndentStringGroup 파싱
     * 복잡한 계층 구조의 효과 정보:
     * - 무기/방어구: 슬롯 효과 (초월)
     * - 방어구: 엘릭서 효과, 연성 추가 효과
     * - 어빌리티 스톤: 무작위 각인 효과
     *
     * 구조:
     * - Element_000.topStr: 제목
     * - Element_000.contentStr: 상세 내용들 (Element_000, Element_001, ...)
     */
    private void parseIndentStringGroup(JsonNode value, EquipmentTidyDto dto) {

        if (!value.has("Element_000")) return;

        JsonNode e0 = value.get("Element_000");
        IndentStringData indent = new IndentStringData();

        // 제목 파싱
        if (e0.has("topStr")) {
            indent.setTitle(stripHtmlTags(e0.get("topStr").asText()));
        }

        // 내용 파싱 (여러 개의 contentStr을 하나로 합침)
        if (e0.has("contentStr")) {
            StringBuilder sb = new StringBuilder();

            e0.get("contentStr").fields().forEachRemaining(inner -> {
                JsonNode contentObj = inner.getValue();
                if (contentObj.has("contentStr")) {
                    String content = contentObj.get("contentStr").asText();
                    // <BR> 태그를 줄바꿈으로 변환 후 HTML 태그 제거
                    sb.append(stripHtmlTags(content.replace("<BR>", "\n")))
                            .append("\n");
                }
            });

            indent.setText(sb.toString().trim());
        }

        dto.getSlotEffect().add(indent);
    }

    // ========================================
    // ShowMeTheMoney: 내구도 정보
    // ========================================
    /**
     * ShowMeTheMoney 파싱
     * 내구도 정보 (무기/방어구만 존재)
     */
    private void parseShowMeTheMoney(JsonNode value, EquipmentTidyDto dto) {
        dto.setDurability(stripHtmlTags(value.asText()));
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
                .replaceAll("^\\|+|\\|+$", "")       // 앞뒤 | 문자 제거
                .trim();
    }
}