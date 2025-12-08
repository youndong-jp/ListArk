package com.example.ListArk.exception;

import org.springframework.http.HttpStatus;

/**
 * 캐릭터가 존재하지 않을 때 발생하는 예외
 * - HTTP 404 NOT FOUND
 */
public class CharacterNotFoundException extends BusinessException {

    public CharacterNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "캐릭터를 찾을 수 없습니다: " + name);
    }
}
