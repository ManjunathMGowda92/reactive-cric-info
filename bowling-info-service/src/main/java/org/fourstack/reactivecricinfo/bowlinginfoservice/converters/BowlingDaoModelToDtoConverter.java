package org.fourstack.reactivecricinfo.bowlinginfoservice.converters;

import org.fourstack.reactivecricinfo.bowlinginfoservice.codetype.CricketFormat;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.BowlingInfoDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.dto.StatisticsDTO;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingInfo;
import org.fourstack.reactivecricinfo.bowlinginfoservice.model.BowlingStatistics;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.fourstack.reactivecricinfo.bowlinginfoservice.util.CommonUtil.convertToInt;

@Component("daoToDtoConverter")
public class BowlingDaoModelToDtoConverter implements Converter<BowlingInfo, BowlingInfoDTO> {
    @Override
    public BowlingInfoDTO convert(BowlingInfo source) {
        BowlingInfoDTO target = new BowlingInfoDTO();
        target.setBowlingInfoId(source.getBowlingId());
        target.setPlayerId(source.getPlayerId());

        target.setBowlingStatistics(generateStatisticsDTOList(source));
        return target;
    }

    private List<StatisticsDTO> generateStatisticsDTOList(BowlingInfo source) {
        List<BowlingStatistics> statistics = source.getStatistics();
        if (statistics == null || statistics.isEmpty())
            return Collections.emptyList();

        return statistics.stream()
                .map(this::convertToDTOStatistics)
                .collect(Collectors.toList());
    }

    private StatisticsDTO convertToDTOStatistics(BowlingStatistics stat) {
        StatisticsDTO dto = new StatisticsDTO();
        dto.setFormat(CricketFormat.valueOf(stat.getFormat()));

        dto.setMatches(stat.getMatches());
        dto.setInnings(stat.getInnings());
        dto.setBalls(stat.getBalls());
        dto.setRuns(stat.getRuns());

        dto.setMaidens(stat.getMaidens());
        dto.setWickets(stat.getWickets());
        dto.setAverage(stat.getAverage());
        dto.setEconomy(stat.getEconomy());
        dto.setStrikeRate(stat.getStrikeRate());

        dto.setBestBowlingInInnings(stat.getBestBowlingInInnings());
        dto.setBestBowlingInMatch(stat.getBestBowlingInMatch());

        dto.setFourWicketHaul(convertToInt(stat.getFourWicketHaul()));
        dto.setFiveWicketHaul(convertToInt(stat.getFiveWicketHaul()));
        dto.setTenWicketHaul(convertToInt(stat.getTenWicketHaul()));

        return dto;
    }
}
