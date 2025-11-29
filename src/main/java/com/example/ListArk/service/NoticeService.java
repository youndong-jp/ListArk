package com.example.ListArk.service;


import com.example.ListArk.Dto.raw.notice.NoticeDto;
import com.example.ListArk.Dto.raw.notice.NoticeViewDto;
import com.example.ListArk.client.api.NoticeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeClient noticeClient;

    private static final DateTimeFormatter FLEXIBLE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true).optionalEnd()
                    .toFormatter();

    // 전체 공지 (Mono로 전달)
    public Mono<List<NoticeDto>> getNotices() {
        return noticeClient.getNotices();
    }

    // 필터링된 공지
    public Mono<List<NoticeDto>> getFilteredNotices(String type) {
        return noticeClient.getNotices()
                .map(list -> list.stream()
                        .filter(n -> type == null || n.getType().equals(type))
                        .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                        .toList());
    }

    // View 용 DTO 변환
    public Mono<List<NoticeViewDto>> getNoticeViews() {
        return noticeClient.getNotices()
                .map(this::convertToViewDto);
    }

    // Map → View 변환
    private List<NoticeViewDto> convertToViewDto(List<NoticeDto> notices) {
        return notices.stream()
                .map(n -> {
                    LocalDateTime dateTime = LocalDateTime.parse(n.getDate(), FLEXIBLE_FORMATTER);

                    String date = dateTime.toLocalDate().toString();
                    String time = dateTime.toLocalTime().withNano(0).toString();

                    long days = ChronoUnit.DAYS.between(
                            dateTime.toLocalDate(),
                            LocalDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate()
                    );

                    String ago = (days == 0) ? "오늘" : days + "일 전";

                    return NoticeViewDto.builder()
                            .title(n.getTitle())
                            .type(n.getType())
                            .date(date)
                            .time(time)
                            .ago(ago)
                            .link(n.getLink())
                            .build();
                })
                .toList();
    }
}

