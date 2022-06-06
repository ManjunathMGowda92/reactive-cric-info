package org.fourstack.reactivecricinfo.bowlinginfoservice.converters;

import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.StatisticsDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingInfo;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingStatistics;
import org.fourstack.reactivecricinfo.bowlinginfoservice.util.IdGenerationUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("dtoToDaoConverter")
public class BowlingDtoToModelConverter implements Converter<BowlingInfoDTO, BowlingInfo> {
    @Override
    public BowlingInfo convert(BowlingInfoDTO source) {
        BowlingInfo target = new BowlingInfo();
        target.setBowlingId(IdGenerationUtil.generateBowlingInfoId(source.getPlayerId()));
        target.setPlayerId(source.getPlayerId());

        target.setStatistics(generateBowlerStatistics(source));
        return target;
    }

    private List<BowlingStatistics> generateBowlerStatistics(BowlingInfoDTO source) {
        List<StatisticsDTO> bowlingStatistics = source.getBowlingStatistics();
        if (bowlingStatistics == null  || bowlingStatistics.isEmpty())
            return Collections.emptyList();

        return bowlingStatistics.stream()
                .map(dto -> convertToDAOStatistics(dto, source.getPlayerId()))
                .collect(Collectors.toList());
    }

    private BowlingStatistics convertToDAOStatistics(StatisticsDTO dto, String playerId) {
        BowlingStatistics statistics = new BowlingStatistics();
        statistics.setId(IdGenerationUtil.generateStatisticsIdForEachFormat(playerId, dto.getFormat().name()));
        statistics.setFormat(dto.getFormat().name());
        statistics.setMatches(dto.getMatches());
        statistics.setInnings(dto.getInnings());
        statistics.setBalls(dto.getBalls());
        statistics.setRuns(dto.getRuns());

        statistics.setMaidens(dto.getMaidens());
        statistics.setWickets(dto.getWickets());
        statistics.setAverage(dto.getAverage());
        statistics.setEconomy(dto.getAverage());
        statistics.setStrikeRate(dto.getStrikeRate());

        statistics.setBestBowlingInInnings(dto.getBestBowlingInInnings());
        statistics.setBestBowlingInMatch(dto.getBestBowlingInMatch());

        statistics.setFourWicketHaul(String.valueOf(dto.getFourWicketHaul()));
        statistics.setFiveWicketHaul(String.valueOf(dto.getFiveWicketHaul()));
        statistics.setTenWicketHaul(String.valueOf(dto.getTenWicketHaul()));
        return statistics;
    }
}
