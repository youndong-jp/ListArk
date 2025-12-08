package com.example.ListArk.client.util;

import com.example.ListArk.exception.CharacterNotFoundException;
import reactor.core.publisher.Mono;

public class RawResponseValidator {

    /**
     * LostArk API는 존재하지 않는 캐릭터에도 200 OK + null 반환한다.
     * 따라서 RawClient 단계에서 null이면 CharacterNotFoundException을 던져야 한다.
     */
    public static <T> Mono<T> validate(String characterName, T dto) {

        // JSON null → bodyToMono(String.class) 결과 = "null"
        if (dto == null) {
            return Mono.error(new CharacterNotFoundException(characterName));
        }

        if (dto instanceof String body) {
            if (body.isBlank() || body.equalsIgnoreCase("null")) {
                return Mono.error(new CharacterNotFoundException(characterName));
            }
        }

        return Mono.just(dto);
    }
}