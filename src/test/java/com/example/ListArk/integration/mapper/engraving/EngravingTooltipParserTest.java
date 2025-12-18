package com.example.ListArk.integration.mapper.engraving;

import com.example.ListArk.mapper.armory.engraving.EngravingTooltipParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EngravingTooltipParserTest {

    @Test
    @DisplayName("HTML 태그 제거 테스트 - <FONT>, <BR> 등 제거")
    void clean_html_tags() {
        String raw = "<FONT COLOR='#ff0000'>피해량 증가</FONT><BR>테스트&nbsp;문장";

        String cleaned = EngravingTooltipParser.cleanDescription(raw);

        assertThat(cleaned).isEqualTo("피해량 증가테스트 문장");
    }

    @Test
    @DisplayName("null 입력 시 null 반환")
    void clean_null() {
        assertThat(EngravingTooltipParser.cleanDescription(null)).isNull();
    }

    @Test
    @DisplayName("불필요한 공백 제거")
    void clean_whitespace() {
        String raw = "   <FONT>테스트</FONT>   ";

        String cleaned = EngravingTooltipParser.cleanDescription(raw);

        assertThat(cleaned).isEqualTo("테스트");
    }

    @Test
    @DisplayName("&nbsp; → 공백으로 치환")
    void replace_html_entities() {
        String raw = "테스트&nbsp;문장";

        String cleaned = EngravingTooltipParser.cleanDescription(raw);

        assertThat(cleaned).isEqualTo("테스트 문장");
    }
}
