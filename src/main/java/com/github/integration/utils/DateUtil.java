package com.github.integration.utils;

import io.swagger.v3.oas.models.examples.Example;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil { 
    
    // Converts ISO date format to RFC 1123 format
    // Example: 2015-12-26T09:09:33Z -> Sat, 26 Dec 2015 09:09:33 GMT
    public static String formatGithubDate(String isoDate) {
        if (isoDate == null) {
            return null;
        }
        
        Instant instant = Instant.parse(isoDate);

        return DateTimeFormatter.RFC_1123_DATE_TIME
                .format(instant.atZone(ZoneId.of("GMT")));
    }
}
