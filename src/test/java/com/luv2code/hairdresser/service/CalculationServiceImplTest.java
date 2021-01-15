//package com.luv2code.hairdresser.service;
//
//import com.luv2code.hairdresser.domain.Accommodation;
//import com.luv2code.hairdresser.domain.Indent;
//import com.luv2code.hairdresser.service.impl.CalculationServiceImpl;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//public class CalculationServiceImplTest {
//
//    @InjectMocks
//    private CalculationServiceImpl calculationService;
//
//    @Mock
//    private ParseService parseService;
//
//    private Indent firstIndent;
//    private Indent secondIndent;
//
//    @BeforeEach
//    public void setup() {
//        Accommodation firstAccommodation = new Accommodation();
//        firstAccommodation.setId(1L);
//        firstAccommodation.setName("Šišanje");
//        firstAccommodation.setDescription("Šišanje sa škarama i/ili mašinicom");
//        firstAccommodation.setPrice(30);
//        firstAccommodation.setDuration(15);
//
//        Accommodation secondAccommodation = new Accommodation();
//        secondAccommodation.setId(2L);
//        secondAccommodation.setName("Uređivanje brade");
//        secondAccommodation.setDescription("Uređivanje brade sa škarama, mašinicom");
//        secondAccommodation.setPrice(20);
//        secondAccommodation.setDuration(15);
//
//        List<Accommodation> firstIndentAccommodations = new ArrayList<>();
//        firstIndentAccommodations.add(firstAccommodation);
//
//        List<Accommodation> secondIndentAccommodations = new ArrayList<>();
//        firstIndentAccommodations.add(firstAccommodation);
//        firstIndentAccommodations.add(secondAccommodation);
//
//        firstIndent = new Indent();
//        firstIndent.setId(1L);
//        firstIndent.setReservationDate(LocalDateTime.of(2020, 11, 23, 12, 30));
//        firstIndent.setReservationTimeFrom(Time.valueOf(LocalTime.parse("10:15")));
//        firstIndent.setAccommodations(firstIndentAccommodations);
//
//        secondIndent = new Indent();
//        secondIndent.setId(2L);
//        secondIndent.setReservationDate(LocalDateTime.of(2020, 12, 23, 12, 30));
//        secondIndent.setReservationTimeFrom(Time.valueOf(LocalTime.parse("12:30")));
//        secondIndent.setAccommodations(secondIndentAccommodations);
//
//        Mockito.when(calculationService.calculateReservationTimeTo(firstIndent)).thenReturn(Time.valueOf("10:30:00"));
//
//        Mockito.when(parseService.parseToTime("34200000")).thenReturn(Time.valueOf("10:30:00"));
//
//        Mockito.when(parseService.parseToDate(String.valueOf(firstIndent.getReservationDate()))).thenReturn(Date.valueOf("2020-11-23"));
//        Mockito.when(parseService.parseToString(parseToDate(firstIndent.getReservationDate()))).thenReturn("2020-11-23");
//
//        Mockito.when(parseService.parseToDate(String.valueOf(secondIndent.getReservationDate()))).thenReturn(Date.valueOf("2020-12-23"));
//        Mockito.when(parseService.parseToString(parseToDate(secondIndent.getReservationDate()))).thenReturn("2020-12-23");
//    }
//
//    @Test
//    public void should_Return_Calculated_Reservation_Time_To() {
//        final Time calculatedReservationTimeTo = calculationService.calculateReservationTimeTo(firstIndent);
//
//        Assert.assertNotNull(calculatedReservationTimeTo);
//        Assert.assertEquals("10:30:00", String.valueOf(calculatedReservationTimeTo));
//    }
//
//    @Test
//    public void should_Return_True_When_ReservationDate_Is_Equals_ChosenDate() {
//        final Date date = parseToDate(secondIndent.getReservationDate());
//        final Boolean isReservationDateEqualsChosenDate = calculationService.calculateIsReservationDateEqualsChosenDate(secondIndent, date);
//
//        Assert.assertTrue(isReservationDateEqualsChosenDate);
//        Assert.assertEquals("2020-12-23", String.valueOf(date));
//    }
//
//    @Test
//    public void should_Return_False_When_ReservationDate_Is_Not_Equals_ChosenDate() {
//        final Date date = parseToDate(firstIndent.getReservationDate());
//        final Boolean isReservationDateEqualsChosenDate = calculationService.calculateIsReservationDateEqualsChosenDate(secondIndent, date);
//
//        Assert.assertFalse(isReservationDateEqualsChosenDate);
//        Assert.assertEquals("2020-11-23", String.valueOf(date));
//    }
//
//    private Date parseToDate(final LocalDateTime reservationDate) {
//        return parseService.parseToDate(String.valueOf(reservationDate));
//    }
//}
