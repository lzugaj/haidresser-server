package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.Role;
import com.luv2code.hairdresser.domain.User;
import com.luv2code.hairdresser.service.impl.CalculationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.luv2code.hairdresser.domain.enums.IndentType.RESERVED;
import static com.luv2code.hairdresser.domain.enums.RoleType.USER;
import static com.luv2code.hairdresser.domain.enums.StatusType.ACTIVE;

@SpringBootTest
public class CalculationServiceImplTest {

    @InjectMocks
    private CalculationServiceImpl calculationService;

    private Indent firstIndent;
    private Indent secondIndent;

    private static final String chosenDate = "2020-12-23";

    @BeforeEach
    public void setup() {
        final Accommodation firstAccommodation = new Accommodation();
        firstAccommodation.setId(1L);
        firstAccommodation.setName("Šišanje");
        firstAccommodation.setDescription("Šišanje sa škarama i/ili mašinicom");
        firstAccommodation.setPrice(30);
        firstAccommodation.setDuration(15);

        final Accommodation secondAccommodation = new Accommodation();
        secondAccommodation.setId(2L);
        secondAccommodation.setName("Uređivanje brade");
        secondAccommodation.setDescription("Uređivanje brade sa škarama, mašinicom");
        secondAccommodation.setPrice(20);
        secondAccommodation.setDuration(15);

        List<Accommodation> firstIndentAccommodations = new ArrayList<>();
        firstIndentAccommodations.add(firstAccommodation);

        List<Accommodation> secondIndentAccommodations = new ArrayList<>();
        secondIndentAccommodations.add(firstAccommodation);
        secondIndentAccommodations.add(secondAccommodation);

        final Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName(USER);
        userRole.setDescription("The user manager can perform CRUD operations over his/her profile and indents.");

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setRoles(roles);
        firstUser.setFirstName("Michael");
        firstUser.setLastName("Jordan");
        firstUser.setUsername("MJ");
        firstUser.setPassword("michael23");
        firstUser.setEmail("mj23@gmail.com");
        firstUser.setPhoneNumber("+385914541239");
        firstUser.setNumberOfReservations(1);
        firstUser.setStatus(ACTIVE);
        firstUser.setHasReservedIndent(false);

        firstIndent = new Indent();
        firstIndent.setId(1L);
        firstIndent.setReservationDate(LocalDate.of(2020, 11, 23));
        firstIndent.setReservationTimeFrom(parseToTime("11:10"));
        firstIndent.setStatus(RESERVED);
        firstIndent.setAccommodations(firstIndentAccommodations);
        firstIndent.setUser(firstUser);

        secondIndent = new Indent();
        secondIndent.setId(2L);
        secondIndent.setReservationDate(LocalDate.of(2020, 12, 23));
        secondIndent.setReservationTimeFrom(parseToTime("12:30"));
        secondIndent.setStatus(RESERVED);
        secondIndent.setAccommodations(secondIndentAccommodations);
        secondIndent.setUser(firstUser);

        final List<Indent> indents = new ArrayList<>();
        indents.add(firstIndent);
        indents.add(secondIndent);

        firstUser.setIndents(indents);
    }

    @Test
    public void should_Return_Calculated_Reservation_Time_To() {
        final Time timeRequiredToPerformChosenAccommodations = calculationService.calculateReservationTimeTo(firstIndent);

        Assertions.assertNotNull(timeRequiredToPerformChosenAccommodations);
        Assertions.assertEquals("11:25:00", String.valueOf(timeRequiredToPerformChosenAccommodations));
    }

    @Test
    public void should_Check_If_Reservation_Date_Is_Equals_Chosen_Date() {
        final Boolean isReservationDateEqualsChosenDate = calculationService.calculateIsReservationDateEqualsChosenDate(secondIndent, parseToDate(chosenDate));

        Assertions.assertNotNull(isReservationDateEqualsChosenDate);
        Assertions.assertTrue(isReservationDateEqualsChosenDate);
    }

    private static LocalTime parseToTime(String time) {
        return LocalTime.parse(time);
    }

    private Date parseToDate(final String dateString) {
        return Date.valueOf(dateString);
    }
}
