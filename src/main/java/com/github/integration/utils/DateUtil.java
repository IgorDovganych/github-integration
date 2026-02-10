package com.github.integration.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String formatGithubDate(String isoDate) {

        if (isoDate == null) {
            return null;
        }

        Instant instant = Instant.parse(isoDate);

        return DateTimeFormatter.RFC_1123_DATE_TIME
                .format(instant.atZone(ZoneId.of("GMT")));
    }
}
