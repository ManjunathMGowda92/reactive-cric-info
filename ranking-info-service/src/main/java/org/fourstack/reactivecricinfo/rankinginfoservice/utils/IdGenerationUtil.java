package org.fourstack.reactivecricinfo.rankinginfoservice.utils;

import static org.fourstack.reactivecricinfo.rankinginfoservice.constants.RankingConstants.RANKING_ID_PREFIX;

public class IdGenerationUtil {

    public static String generateRankingId(String playerId) {
        return new StringBuffer(RANKING_ID_PREFIX)
                .append(playerId)
                .toString();
    }
}
