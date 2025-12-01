package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ColosseumEntryDto;
import com.example.ListArk.Dto.tidy.armory.colosseum.ColosseumSeasonDto;
import com.example.ListArk.Dto.tidy.armory.colosseum.ColosseumTidyDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColosseumTidyMapper {

    public ColosseumTidyDto toTidy(ArmoryDto raw) {

        if (raw == null || raw.getColosseumInfo() == null) {
            return null;
        }

        ArmoryColosseumDto col = raw.getColosseumInfo();

        ColosseumTidyDto dto = new ColosseumTidyDto();
        dto.setRank(col.getRank());
        dto.setPreRank(col.getPreRank());
        dto.setExp(col.getExp());

        if (col.getColosseums() != null) {
            List<ColosseumSeasonDto> seasons = col.getColosseums().stream()
                    .map(this::convertSeason)
                    .toList();

            dto.setSeasons(seasons);
        }

        return dto;
    }

    /** Raw 시즌 -> Tidy 시즌 변환 */
    private ColosseumSeasonDto convertSeason(ColosseumEntryDto s) {

        ColosseumSeasonDto dto = new ColosseumSeasonDto();

        dto.setSeasonName(s.getSeasonName());

        // 경쟁전 정보가 있을 때만
        if (s.getCompetitive() != null) {
            dto.setWin(s.getCompetitive().getVictoryCount());
            dto.setLose(s.getCompetitive().getLoseCount());
            dto.setTie(s.getCompetitive().getTieCount());

            dto.setKill(s.getCompetitive().getKillCount());
            dto.setDeath(s.getCompetitive().getDeathCount());
            dto.setAssist(s.getCompetitive().getAceCount());
        }

        return dto;
    }
}
