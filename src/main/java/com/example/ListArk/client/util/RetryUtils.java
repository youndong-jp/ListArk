package com.example.ListArk.client.util;

import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Function;

public class RetryUtils {

    /**
     * API용 기본 retry 전략
     * - 최대 3회 retry
     * - exponential backoff (200ms → 400ms → 800ms)
     * - jitter 적용 (서버 과부하 방지)
     * - 특정 예외만 retry
     */
    public static <T> Function<Mono<T>, Mono<T>> retry3() {
        return mono -> mono.retryWhen(
                Retry.backoff(3, Duration.ofMillis(200))
                        .jitter(0.75) // 0~75% 랜덤 지연 → 서버 안정성 향상
                        .filter(RetryUtils::isRetryableException)
                        .onRetryExhaustedThrow((spec, signal) ->
                                new RuntimeException("Lost Ark API unavailable after retries")
                        )
        );
    }

    /**
     * notices, 간단한 API용 retry 1회
     */
    public static <T> Function<Mono<T>, Mono<T>> retry1() {
        return mono -> mono.retryWhen(
                Retry.backoff(1, Duration.ofMillis(150))
                        .jitter(0.5)
                        .filter(RetryUtils::isRetryableException)
        );
    }

    /**
     * retry 없음 (어떤 API는 retry하면 오히려 문제)
     */
    public static <T> Function<Mono<T>, Mono<T>> noRetry() {
        return mono -> mono; // 아무것도 안 함
    }

    /**
     * retry 가능한 예외 목록
     */
    private static boolean isRetryableException(Throwable ex) {
        return ex instanceof WebClientResponseException.ServiceUnavailable ||  // 503
                ex instanceof WebClientResponseException.GatewayTimeout ||      // 504
                ex instanceof WebClientRequestException;                        // timeout, connection 등
    }
}
