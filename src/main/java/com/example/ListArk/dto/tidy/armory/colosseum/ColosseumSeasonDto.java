package com.example.ListArk.dto.tidy.armory.colosseum;

import lombok.Data;

@Data
public class ColosseumSeasonDto {

    private String seasonName;

    private String rankName;
    private String rankIcon;
    private Integer win;
    private Integer lose;
    private Integer tie;

    private Integer kill;
    private Integer death;
    private Integer ace;

}
