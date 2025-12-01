package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.card.ArmoryCardDto;
import com.example.ListArk.Dto.raw.armory.card.CardDto;
import com.example.ListArk.Dto.raw.armory.card.CardEffectDto;
import com.example.ListArk.Dto.tidy.armory.card.CardSimpleDto;
import com.example.ListArk.Dto.tidy.armory.card.CardTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardTidyMapper {

    public CardTidyDto toTidy(ArmoryDto raw) {

        CardTidyDto dto = new CardTidyDto();

        if (raw == null || raw.getArmoryCard() == null) {
            dto.setCards(List.of());
            dto.setSetEffects(List.of());
            return dto;
        }

        ArmoryCardDto cardRaw = raw.getArmoryCard();

        // 1) 카드 목록 tidy
        if (cardRaw.getCards() != null) {
            List<CardSimpleDto> cards = cardRaw.getCards().stream()
                    .map(this::convertCard)
                    .toList();
            dto.setCards(cards);
        } else {
            dto.setCards(List.of());
        }

        // 2) 세트 효과 tidy
        if (cardRaw.getEffects() != null) {
            List<String> effects = cardRaw.getEffects().stream()
                    .map(this::convertEffect)
                    .toList();

            dto.setSetEffects(effects);
        } else {
            dto.setSetEffects(List.of());
        }

        return dto;
    }


    /** RAW 카드 → SIMPLE 카드 */
    private CardSimpleDto convertCard(CardDto c) {

        CardSimpleDto dto = new CardSimpleDto();

        dto.setName(c.getName());
        dto.setAwakeCount(c.getAwakeCount());
        dto.setAwakeTotal(c.getAwakeTotal());
        dto.setGrade(c.getGrade());
        dto.setIcon(c.getIcon());

        return dto;
    }


    /** RAW 카드 세트 효과 → "세구빛 2세트 (각성 12)" 같은 tidy 문장 */
    private String convertEffect(CardEffectDto effect) {

        if (effect.getItems() == null || effect.getItems().isEmpty()) {
            return "";
        }

        // items 안에는 실제 세트 효과 설명들이 들어있음
        return effect.getItems().stream()
                .map(item -> item.getName())  // 예: "세구빛 12각"
                .findFirst()
                .orElse("");
    }

}
