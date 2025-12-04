package com.example.ListArk.mapper.armory;

import com.example.ListArk.Dto.raw.armory.ArmoryDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ArmoryColosseumDto;
import com.example.ListArk.Dto.raw.armory.colosseum.ColosseumEntryDto;
import com.example.ListArk.Dto.raw.armory.colosseum.CompetitiveDto;
import com.example.ListArk.Dto.tidy.armory.colosseum.ColosseumSeasonDto;
import com.example.ListArk.Dto.tidy.armory.colosseum.ColosseumTidyDto;
import com.example.ListArk.mapper.NullSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColosseumTidyMapper {

    public ColosseumTidyDto toTidy(ArmoryDto raw) {
        ArmoryColosseumDto col = NullSafe.get(raw::getColosseumInfo, null);

        if (col == null) {
            return new ColosseumTidyDto();
        }

        ColosseumTidyDto dto = new ColosseumTidyDto();
        dto.setRank(NullSafe.get(col::getRank, 0));
        dto.setPreRank(NullSafe.get(col::getPreRank, 0));
        dto.setExp(NullSafe.get(col::getExp, 0));

        List<ColosseumSeasonDto> seasons = NullSafe.list(col.getColosseums())
                .stream()
                .map(this::convertSeason)
                .toList();
        dto.setSeasons(seasons);

        return dto;
    }

    /**
     * Raw 시즌 → Tidy 시즌 변환
     * 경쟁전(Competitive) 정보만 추출
     */
    private ColosseumSeasonDto convertSeason(ColosseumEntryDto entry) {
        ColosseumSeasonDto dto = new ColosseumSeasonDto();
        dto.setSeasonName(NullSafe.get(entry::getSeasonName, ""));

        CompetitiveDto competitive = NullSafe.get(entry::getCompetitive, null);

        if (competitive != null) {
            dto.setRankName(NullSafe.get(competitive::getRankName,""));
            dto.setRankIcon(NullSafe.get(competitive::getRankIcon,""));
            dto.setWin(NullSafe.get(competitive::getVictoryCount, 0));
            dto.setLose(NullSafe.get(competitive::getLoseCount, 0));
            dto.setTie(NullSafe.get(competitive::getTieCount, 0));
            dto.setKill(NullSafe.get(competitive::getKillCount, 0));
            dto.setDeath(NullSafe.get(competitive::getDeathCount, 0));
            dto.setAce(NullSafe.get(competitive::getAceCount, 0));
        }

        return dto;
    }
}