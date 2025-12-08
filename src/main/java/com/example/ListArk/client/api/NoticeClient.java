package com.example.ListArk.client.api;

import com.example.ListArk.Dto.raw.notice.NoticeDto;import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.ListArk.client.util.RetryUtils;
import java.util.List;

@Service
@RequiredArgsConstructor

public class NoticeClient {

    private final WebClient webClient;

    public Mono<List<NoticeDto>> getNotices() {
        return webClient.get()
                .uri("/news/notices")
                .retrieve()
                .bodyToFlux(NoticeDto.class)
                .collectList()
                .transform(RetryUtils.retry3());
    }
}
