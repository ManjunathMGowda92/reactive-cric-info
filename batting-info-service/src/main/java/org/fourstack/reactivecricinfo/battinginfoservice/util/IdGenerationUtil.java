package org.fourstack.reactivecricinfo.battinginfoservice.util;

import static org.fourstack.reactivecricinfo.battinginfoservice.constants.BattingServiceConstants.*;

public class IdGenerationUtil {
    public static String generateBattingInfoId(String playerId) {
        return new StringBuilder(BATTING_INFO_ID_PREFIX)
                .append(playerId)
                .toString();
    }

    public static String generateStatisticsIdForEachFormat(String playerId, String format) {
        return new StringBuilder(BATTING_INFO_ID_PREFIX)
                .append(format)
                .append(HYPHEN)
                .append(playerId)
                .toString();
    }
}
