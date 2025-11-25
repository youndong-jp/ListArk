package com.example.ListArk.Dto.notice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NoticeDto {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Date")
    private String date;

    @JsonProperty("Link")
    private String link;
}
