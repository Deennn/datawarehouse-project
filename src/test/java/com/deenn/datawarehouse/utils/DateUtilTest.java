package com.deenn.datawarehouse.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DateUtilTest {


    @Test
    void getZonedLocalDateTime_ValidLocalDateTime() {
        LocalDateTime inputDateTime = LocalDateTime.of(2022, 1, 1, 12, 0);
        LocalDateTime expectedDateTime = LocalDateTime.of(2022, 1, 1, 12, 0);

        LocalDateTime result = DateUtil.getZonedLocalDateTime(inputDateTime);

        assertEquals(expectedDateTime, result);
    }

    @Test
    void getZonedLocalDateTime_NullLocalDateTime() {
        LocalDateTime result = DateUtil.getZonedLocalDateTime(null);
        assertNull(result);
    }

}