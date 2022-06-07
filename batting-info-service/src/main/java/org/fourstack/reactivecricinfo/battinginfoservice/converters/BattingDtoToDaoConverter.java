package org.fourstack.reactivecricinfo.battinginfoservice.converters;

import org.fourstack.reactivecricinfo.battinginfoservice.dto.BattingInfoDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.dto.StatisticsDTO;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingInfo;
import org.fourstack.reactivecricinfo.battinginfoservice.model.BattingStatistics;

import static org.fourstack.reactivecricinfo.battinginfoservice.util.IdGenerationUtil.*;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BattingDtoToDaoConverter implements Converter<BattingInfoDTO, BattingInfo> {
    @Override
    public BattingInfo convert(BattingInfoDTO source) {
        BattingInfo target = new BattingInfo();

        String battingId = source.getBattingId();
        String playerId = source.getPlayerId();
        target.setId((battingId == null || battingId.isBlank()) ?
                generateBattingInfoId(playerId) : battingId);
        target.setPlayerId(playerId);

        target.setStatistics(getStatistics(source));


        return target;
    }

    private List<BattingStatistics> getStatistics(BattingInfoDTO source) {
        List<StatisticsDTO> statistics = source.getStatistics();
        if (statistics == null || statistics.isEmpty())
            return Collections.emptyList();

        return statistics.stream()
                .map(dto -> convertToDAOStatistics(dto, source.getPlayerId()))
                .collect(Collectors.toList());
    }

    private BattingStatistics convertToDAOStatistics(StatisticsDTO dto, String playerId) {
        BattingStatistics statistics = new BattingStatistics();
        statistics.setId(generateStatisticsIdForEachFormat(playerId, dto.getFormat().toString()));
        statistics.setFormat(dto.getFormat().toString());
        statistics.setMatches(dto.getMatches());
        statistics.setInnings(dto.getInnings());
        statistics.setRuns(dto.getRuns());
        statistics.setBalls(dto.getBalls());
        statistics.setHighest(dto.getHighest());

        statistics.setAverage(dto.getAverage());
        statistics.setStrikeRate(dto.getStrikeRate());

        statistics.setNumberOfNotOuts(dto.getNumberOfNotOuts());
        statistics.setFours(dto.getFours());
        statistics.setSixes(dto.getSixes());
        statistics.setDucks(dto.getDucks());

        statistics.setNoOfHalfCenturies(dto.getNoOfHalfCenturies());
        statistics.setNoOfCenturies(dto.getNoOfCenturies());
        statistics.setNoOfDoubleCenturies(dto.getNoOfDoubleCenturies());
        statistics.setNoOfQuadrupleCenturies(dto.getNoOfQuadrupleCenturies());
        statistics.setNoOfTripleCenturies(dto.getNoOfTripleCenturies());
        return statistics;

    }
}
