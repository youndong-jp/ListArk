package com.example.ListArk.Dto.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeViewDto {
    private String title;
    private String type;
    private String date;
    private String time;
    private String ago;
    private String link;
}
