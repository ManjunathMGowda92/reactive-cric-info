package org.fourstack.reactivecricinfo.rankinginfoservice.converters;

import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.RankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.model.IccRanking;
import org.fourstack.reactivecricinfo.rankinginfoservice.model.Rank;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RankDaoToDtoConverter implements Converter<IccRanking, IccRankDTO> {
    @Override
    public IccRankDTO convert(IccRanking source) {
        IccRankDTO target = new IccRankDTO();
        target.setRankId(source.getRankingId());
        target.setPlayerId(source.getPlayerId());

        List<RankDTO> rankDTOS = source.getRankings().stream()
                .map(this::convertToRankDTO)
                .collect(Collectors.toList());
        target.setRankings(rankDTOS);
        return target;
    }

    private RankDTO convertToRankDTO(Rank rank) {
        RankDTO rankDTO = new RankDTO();
        rankDTO.setFormat(rank.getFormat().name());
        rankDTO.setBestRank(rank.getBest());
        rankDTO.setCurrentRank(rank.getCurrent());
        return rankDTO;
    }
}
