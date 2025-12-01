package com.example.ListArk.Dto.tidy.armory.colosseum;

import lombok.Data;

@Data
public class ColosseumSeasonDto {

    private String seasonName;

    private Integer win;
    private Integer lose;
    private Integer tie;

    private Integer kill;
    private Integer death;
    private Integer assist;

}
