package com.example.ListArk.client.util;

import com.example.ListArk.client.error.WebClientErrorHandler;
import com.example.ListArk.exception.CharacterNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.ListArk.client.util.RawResponseValidator.validate;

public class WebClientHelper {

    public static <T> Mono<T> getMono(
            WebClient client,
            String uri,
            Class<T> responseType,
            String name
    ) {
        return client.get()
                .uri(uri, name)
                .retrieve()
                .onStatus(HttpStatusCode::isError, WebClientErrorHandler::handleStatus)
                .bodyToMono(responseType)
                .switchIfEmpty(Mono.error(new CharacterNotFoundException(name)))  // ★ 핵심
                .flatMap(body -> RawResponseValidator.validate(name, body))       // ★ 수정
                .transform(RetryUtils.retry3());
    }


    public static <T> Mono<List<T>> getList(
            WebClient client,
            String uri,
            Class<T> responseType,
            String name
    ) {
        return client.get()
                .uri(uri, name)   // ★ 같은 이유
                .retrieve()
                .onStatus(HttpStatusCode::isError, WebClientErrorHandler::handleStatus)
                .bodyToFlux(responseType)
                .collectList()
                .transform(RetryUtils.retry3());
    }

    public static Mono<String> getRaw(
            WebClient client,
            String uri,
            String name
    ) {
        return client.get()
                .uri(uri, name)
                .retrieve()
                .onStatus(HttpStatusCode::isError, WebClientErrorHandler::handleStatus)
                .bodyToMono(String.class)
                .flatMap(body -> RawResponseValidator.validate(name, body))
                .transform(RetryUtils.retry3());
    }

}
