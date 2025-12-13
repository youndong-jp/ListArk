package com.example.ListArk.mapper.armory.engraving;


public class EngravingTooltipParser {

    /** HTML 태그 제거 + whitespace 정리 */
    public static String cleanDescription(String raw) {
        if (raw == null) return null;

        // 1) <FONT ...>...</FONT> 같은 HTML 태그 제거
        String cleaned = raw.replaceAll("<[^>]+>", "");

        // 2) &nbsp; 같은 엔티티 치환
        cleaned = cleaned.replace("&nbsp;", " ");

        // 3) 양쪽 공백 정리
        return cleaned.trim();
    }
}
