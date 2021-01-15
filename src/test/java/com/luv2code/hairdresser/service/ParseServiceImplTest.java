package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.service.impl.ParseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@SpringBootTest
public class ParseServiceImplTest {

    @InjectMocks
    private ParseServiceImpl parseService;

    @Test
    public void should_Return_Date_To_String() {
        final String searchedString = parseService.parseToString(Date.valueOf(LocalDate.of(2020, 12, 23)));

        Assertions.assertEquals("2020-12-23", searchedString);
    }

    @Test
    public void should_Return_Time_When_String_Is_Parsed() {
        final Time searchedTime = parseService.parseToTime("2020-11-20T23:12:00.000+00:00");

        Assertions.assertEquals("23:12:00", String.valueOf(searchedTime));
    }

    @Test
    public void should_Return_Date_When_String_Is_Parsed() {
        final Date searchedDate = parseService.parseToDate("2020-12-11T23:12:00.000+00:00");

        Assertions.assertEquals("2020-12-11", String.valueOf(searchedDate));
    }
}
