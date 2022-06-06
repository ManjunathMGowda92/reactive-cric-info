package org.fourstack.reactivecricinfo.bowlinginfoservice.util;

import static org.fourstack.reactivecricinfo.bowlinginfoservice.constants.BowlingServiceConstants.HYPHEN;
import static org.fourstack.reactivecricinfo.bowlinginfoservice.constants.BowlingServiceConstants.BOWLING_INFO_ID_PREFIX;

public class IdGenerationUtil {

    public static String generateBowlingInfoId(String playerId) {
        return new StringBuilder(BOWLING_INFO_ID_PREFIX)
                .append(playerId)
                .toString();
    }

    public static String generateStatisticsIdForEachFormat(String playerId, String format) {
        return new StringBuilder(BOWLING_INFO_ID_PREFIX)
                .append(format)
                .append(HYPHEN)
                .append(playerId)
                .toString();
    }
}
