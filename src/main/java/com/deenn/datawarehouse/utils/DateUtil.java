package com.deenn.datawarehouse.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {


    private final static ZoneId westAfricaZoneId = ZoneId.of("Africa/Lagos");
    private final static ZoneId systemZoneId = ZoneId.systemDefault();

    public static LocalDateTime getZonedLocalDateTime (LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return localDateTime.atZone(systemZoneId)
                .withZoneSameInstant(westAfricaZoneId)
                .toLocalDateTime();
    }


}