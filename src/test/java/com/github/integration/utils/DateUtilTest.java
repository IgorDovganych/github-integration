package com.github.integration.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void formatValidDate() {
        String isoDate = "2015-12-26T09:09:33Z";

        String result = DateUtil.formatGithubDate(isoDate);

        assertEquals(
                "Sat, 26 Dec 2015 09:09:33 GMT",
                result
        );
    }

    @Test
    void returnNull_inputIsNull() {

        String result = DateUtil.formatGithubDate(null);

        assertNull(result);
    }

    @Test
    void throwException_incorrectDate() {

        String incorrectDate = "BLA_BLA_BLA";

        assertThrows(
                Exception.class,
                () -> DateUtil.formatGithubDate(incorrectDate)
        );
    }
    
}