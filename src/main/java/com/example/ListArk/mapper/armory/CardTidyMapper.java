package com.example.ListArk.mapper.armory;

import com.example.ListArk.dto.raw.armory.ArmoryDto;
import com.example.ListArk.dto.raw.armory.card.*;
import com.example.ListArk.dto.tidy.armory.card.CardSimpleDto;
import com.example.ListArk.dto.tidy.armory.card.CardTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardTidyMapper {

    public CardTidyDto toTidy(ArmoryDto raw) {
        CardTidyDto dto = new CardTidyDto();

        ArmoryCardDto cardRaw = NullSafe.get(raw::getArmoryCard, null);

        if (cardRaw == null) {
            dto.setCards(List.of());
            dto.setSetEffects(List.of());
            return dto;
        }

        // 1) 카드 목록
        List<CardSimpleDto> cards = NullSafe.list(cardRaw.getCards())
                .stream()
                .map(this::convertCard)
                .toList();
        dto.setCards(cards);

        // 2) 카드 세트 효과 (모든 세트의 모든 효과)
        List<String> effects = NullSafe.list(cardRaw.getEffects())
                .stream()
                .flatMap(effect -> convertEffectItems(effect).stream())
                .filter(e -> !e.isBlank())
                .toList();
        dto.setSetEffects(effects);

        return dto;
    }

    /**
     * RAW → SIMPLE 카드
     */
    private CardSimpleDto convertCard(CardDto c) {
        CardSimpleDto dto = new CardSimpleDto();

        dto.setSlot(NullSafe.get(c::getSlot, 0));
        dto.setName(NullSafe.get(c::getName, ""));
        dto.setIcon(NullSafe.get(c::getIcon, ""));
        dto.setGrade(NullSafe.get(c::getGrade, ""));
        dto.setAwakeCount(NullSafe.get(c::getAwakeCount, 0));
        dto.setAwakeTotal(NullSafe.get(c::getAwakeTotal, 0));

        return dto;
    }

    /**
     * RAW 세트 효과의 모든 Items를 문자열 리스트로 변환
     * 형식: "세트명 (설명)"
     */
    private List<String> convertEffectItems(CardEffectDto effect) {
        List<CardEffectItemDto> items = NullSafe.list(effect.getItems());

        return items.stream()
                .map(item -> {
                    String name = NullSafe.get(item::getName, "");
                    String description = NullSafe.get(item::getDescription, "");

                    if (name.isBlank() && description.isBlank()) {
                        return "";
                    }

                    return description.isBlank()
                            ? name
                            : name + " (" + description + ")";
                })
                .filter(s -> !s.isBlank())
                .toList();
    }
}