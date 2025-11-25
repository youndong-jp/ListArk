package com.example.ListArk.service;


import com.example.ListArk.Dto.notice.NoticeDto;
import com.example.ListArk.Dto.notice.NoticeViewDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NoticeService {

    private static final DateTimeFormatter FLEXIBLE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true).optionalEnd()
                    .toFormatter();

    public List<NoticeViewDto> convertToViewDto(List<NoticeDto> notices) {

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
