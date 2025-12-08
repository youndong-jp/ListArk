package com.example.ListArk.client.api;

import com.example.ListArk.client.util.WebClientHelper;
import com.example.ListArk.dto.raw.notice.NoticeDto;import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import static com.example.ListArk.client.util.WebClientHelper.*;

@Service
@RequiredArgsConstructor
public class NoticeClient {

    private final WebClient webClient;

    public Mono<List<NoticeDto>> getNotices() {
        return WebClientHelper.getList(webClient,
                "/news/notices",
                NoticeDto.class,
               "notice");
    }
}
