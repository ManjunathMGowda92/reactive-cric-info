package org.fourstack.reactivecricinfo.bowlinginfoservice.util;

import static java.lang.Integer.parseInt;

public class CommonUtil {
    public static Integer convertToInt(String str) {
        if (str == null || str.isEmpty() || str.isBlank())
            return 0;
        else {
            try {
                return parseInt(str);
            } catch (NumberFormatException exp) {
                return 0;
            }
        }
    }
}
