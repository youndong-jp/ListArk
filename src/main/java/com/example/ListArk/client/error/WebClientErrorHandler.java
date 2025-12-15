package com.example.ListArk.client.error;

import com.example.ListArk.exception.ApiKeyExpiredException;
import com.example.ListArk.exception.CharacterNotFoundException;
import com.example.ListArk.exception.InvalidRequestException;
import com.example.ListArk.exception.ExternalApiException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

/**
 * LostArk API 응답 상태를 기반으로 예외 변환
 */
public class WebClientErrorHandler {

    public static Mono<? extends Throwable> handleStatus(ClientResponse response) {

        HttpStatusCode status = response.statusCode();

        // 🔥 401 API Key 오류
        if (status == HttpStatus.UNAUTHORIZED) {
            return response.bodyToMono(String.class)
                    .defaultIfEmpty("API 키가 만료되었거나 유효하지 않습니다.")
                    .flatMap(msg -> Mono.error(new ApiKeyExpiredException(msg)));
        }

// 🔥 404 캐릭터 없음
        if (status == HttpStatus.NOT_FOUND) {
            return response.bodyToMono(String.class)
                    .defaultIfEmpty("캐릭터를 찾을 수 없습니다.")
                    .flatMap(msg -> Mono.error(new CharacterNotFoundException(msg)));
        }

        // 🔥 일반적인 4xx → 클라이언트 요청 문제
        if (status.is4xxClientError()) {
            return response.bodyToMono(String.class)
                    .defaultIfEmpty("잘못된 요청입니다.")
                    .flatMap(msg -> Mono.error(new InvalidRequestException(msg)));
        }

        // 🔥 5xx → 로아 API 서버 불안정
        if (status.is5xxServerError()) {
            return response.bodyToMono(String.class)
                    .defaultIfEmpty("외부 API 서버 오류입니다.")
                    .flatMap(msg -> Mono.error(new ExternalApiException(msg)));
        }

        // 기타 응답
        return response.createException().flatMap(Mono::error);
    }
}
