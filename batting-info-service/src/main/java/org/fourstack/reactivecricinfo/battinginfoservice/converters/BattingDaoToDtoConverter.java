package org.fourstack.reactivecricinfo.battinginfoservice.converters;

import org.fourstack.reactivecricinfo.battinginfoservice.codetype.CricketFormat;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.StatisticsDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;

import static org.fourstack.reactivecricinfo.battinginfoservice.util.IdGenerationUtil.generateBattingInfoId;

import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingStatistics;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BattingDaoToDtoConverter implements Converter<BattingInfo, BattingInfoDTO> {
    @Override
    public BattingInfoDTO convert(BattingInfo source) {
        BattingInfoDTO target = new BattingInfoDTO();

        String battingId = source.getId();
        String playerId = source.getPlayerId();
        target.setBattingId((battingId == null || battingId.isBlank()) ?
                generateBattingInfoId(playerId) : battingId);
        target.setPlayerId(playerId);
        
        target.setStatistics(generateStatistics(source));
        return target;
    }

    private List<StatisticsDTO> generateStatistics(BattingInfo source) {
        List<BattingStatistics> statistics = source.getStatistics();
        if (statistics == null || statistics.isEmpty())
            return Collections.emptyList();
        
        return statistics.stream()
                .map(daoStat -> convertToDtoStatistics(daoStat))
                .collect(Collectors.toList());
    }

    private StatisticsDTO convertToDtoStatistics(BattingStatistics daoStat) {
        StatisticsDTO target = new StatisticsDTO();
        target.setFormat(CricketFormat.valueOf(daoStat.getFormat()));
        target.setMatches(daoStat.getMatches());
        target.setInnings(daoStat.getInnings());
        target.setRuns(daoStat.getRuns());
        target.setBalls(daoStat.getBalls());

        target.setHighest(daoStat.getHighest());
        target.setAverage(daoStat.getAverage());
        target.setStrikeRate(daoStat.getStrikeRate());

        target.setNumberOfNotOuts(daoStat.getNumberOfNotOuts());
        target.setFours(daoStat.getFours());
        target.setSixes(daoStat.getSixes());
        target.setDucks(daoStat.getDucks());

        target.setNoOfHalfCenturies(daoStat.getNoOfHalfCenturies());
        target.setNoOfCenturies(daoStat.getNoOfCenturies());
        target.setNoOfDoubleCenturies(daoStat.getNoOfDoubleCenturies());
        target.setNoOfTripleCenturies(daoStat.getNoOfTripleCenturies());
        target.setNoOfQuadrupleCenturies(daoStat.getNoOfQuadrupleCenturies());

        return target;
    }
}
