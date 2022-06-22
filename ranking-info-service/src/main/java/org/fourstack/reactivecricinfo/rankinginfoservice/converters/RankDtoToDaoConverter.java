package org.fourstack.reactivecricinfo.rankinginfoservice.converters;

import org.fourstack.reactivecricinfo.rankinginfoservice.config.Format;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.IccRankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.dto.RankDTO;
import org.fourstack.reactivecricinfo.rankinginfoservice.model.IccRanking;
import org.fourstack.reactivecricinfo.rankinginfoservice.model.Rank;
import org.fourstack.reactivecricinfo.rankinginfoservice.utils.IdGenerationUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RankDtoToDaoConverter implements Converter<IccRankDTO, IccRanking> {
    @Override
    public IccRanking convert(IccRankDTO source) {
        IccRanking target = new IccRanking();
        target.setRankingId(IdGenerationUtil.generateRankingId(source.getPlayerId()));
        target.setPlayerId(source.getPlayerId());

        source.getRankings()
                .stream()
                .map(dto -> convertToRank(dto))
                .collect(Collectors.toList());

        return null;
    }

    private Rank convertToRank(RankDTO dto) {
        Rank rank = new Rank();
        rank.setBest(dto.getBestRank());
        rank.setCurrent(dto.getCurrentRank());
        rank.setFormat(Format.valueOf(dto.getFormat()));
        return rank;
    }
}
