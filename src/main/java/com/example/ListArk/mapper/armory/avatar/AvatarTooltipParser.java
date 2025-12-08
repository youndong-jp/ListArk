package com.example.ListArk.mapper.armory.avatar;

import com.example.ListArk.Dto.tidy.armory.avatar.AvatarTidyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 아바타 툴팁 파서
 * Tooltip에만 있는 추가 정보 파싱
 */
@Slf4j
@Component
public class AvatarTooltipParser {

    /** 🔹 성향 정규식 */
    private static final Pattern TENDENCY_PATTERN =
            Pattern.compile("(지성|담력|매력|친절)\\s*:?\\s*(\\d+)");

    /**
     * Tooltip 추가 정보 파싱
     * (Raw에 없는 정보만)
     */
    public void parseTooltipData(Object tooltipObj, AvatarTidyDto dto) {

        if (!(tooltipObj instanceof Map<?, ?> tooltip)) {
            log.debug("툴팁이 Map 형태가 아님");
            return;
        }

        // 기본값 설정
        dto.setIsTradable(true);
        dto.setIsSellable(true);
        dto.setIsDecomposable(true);
        dto.setIsBound(false);

        // Element 순회
        for (Object entryObj : tooltip.entrySet()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) entryObj;
            Object element = entry.getValue();

            if (!(element instanceof Map<?, ?> elemMap)) continue;

            String type = (String) elemMap.get("type");
            Object value = elemMap.get("value");

            if (type == null) continue;

            switch (type) {
                case "SingleTextBox"  -> parseSingleTextBox(dto, clean(value));
                case "MultiTextBox"   -> parseMultiTextBox(dto, clean(value));
                case "ItemPartBox"    -> parseEffects(dto, value);
                case "SymbolString"   -> parseTendency(dto, value);
            }
        }
    }

    // ──────────────────────────────────────────────
    //  SingleTextBox: 전용 클래스, 귀속
    // ──────────────────────────────────────────────
    private void parseSingleTextBox(AvatarTidyDto dto, String text) {
        // 전용 클래스
        if (text.contains("전용")) {
            dto.setExclusiveClass(text.replace("전용", "").trim());
        }

        // 귀속
        if (text.contains("귀속")) {
            dto.setIsBound(true);
        }

        // 거래/판매/분해
        scanTradeFlags(dto, text);
    }

    // ──────────────────────────────────────────────
    //  MultiTextBox: 거래/판매/분해
    // ──────────────────────────────────────────────
    private void parseMultiTextBox(AvatarTidyDto dto, String text) {
        scanTradeFlags(dto, text);
    }

    /** 거래/판매/분해 플래그 공통 처리 */
    private void scanTradeFlags(AvatarTidyDto dto, String text) {
        if (text.contains("거래 불가")) {
            dto.setIsTradable(false);
        }
        if (text.contains("판매불가")) {
            dto.setIsSellable(false);
        }
        if (text.contains("분해불가")) {
            dto.setIsDecomposable(false);
        }
    }

    // ──────────────────────────────────────────────
    //  ItemPartBox: 기본 효과
    // ──────────────────────────────────────────────
    private void parseEffects(AvatarTidyDto dto, Object valueObj) {
        if (!(valueObj instanceof Map<?, ?> valueMap)) return;

        List<String> effects = new ArrayList<>();

        for (Object v : valueMap.values()) {
            String text = clean(v);

            // "기본 효과" 제목은 건너뛰기
            if (text.contains("효과")) continue;

            // 실제 효과만 추가
            if (!text.isEmpty()) {
                effects.add(text);
            }
        }

        if (!effects.isEmpty()) {
            dto.setEffects(effects);
        }
    }

    // ──────────────────────────────────────────────
    //  SymbolString: 성향
    // ──────────────────────────────────────────────
    private void parseTendency(AvatarTidyDto dto, Object valueObj) {
        if (!(valueObj instanceof Map<?, ?> valueMap)) return;

        Object contentStr = valueMap.get("contentStr");
        if (!(contentStr instanceof String raw)) return;

        raw = clean(raw);
        if (raw.isEmpty()) return;

        Matcher matcher = TENDENCY_PATTERN.matcher(raw);
        while (matcher.find()) {
            String key = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));

            switch (key) {
                case "지성" -> dto.setIntellect(value);
                case "담력" -> dto.setCourage(value);
                case "매력" -> dto.setCharm(value);
                case "친절" -> dto.setKindness(value);
            }
        }
    }

    // ──────────────────────────────────────────────
    //  HTML 태그 정리
    // ──────────────────────────────────────────────
    private String clean(Object v) {
        if (v == null) return "";
        return v.toString()
                .replaceAll("<[^>]+>", "")
                .replace("&nbsp;", " ")
                .replace("|", "")
                .trim();
    }
}