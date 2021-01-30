package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.Role;
import com.luv2code.hairdresser.domain.User;
import com.luv2code.hairdresser.service.impl.AvailableTermServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.luv2code.hairdresser.domain.enums.RoleType.USER;
import static com.luv2code.hairdresser.domain.enums.StatusType.ACTIVE;

@SpringBootTest
class AvailableTermServiceImplTest {

    @InjectMocks
    private AvailableTermServiceImpl availableTermService;

    @Mock
    private IndentService indentService;

    private final List<User> users = new ArrayList<>();

    private Accommodation firstAccommodation;

    @BeforeEach
    public void setup() {
        Role role = new Role();
        role.setId(1L);
        role.setName(USER);
        role.setDescription("User role");

        final List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Michael");
        user.setLastName("Jordan");
        user.setUsername("MJ23");
        user.setPassword("TheGoat");
        user.setEmail("thegoat23@gmail.com");
        user.setPhoneNumber("+385981234567");
        user.setNumberOfReservations(5);
        user.setStatus(ACTIVE);
        user.setRoles(roles);

        users.add(user);
//        role.set(users);

        firstAccommodation = new Accommodation();
        firstAccommodation.setId(1L);
        firstAccommodation.setName("Šišanje");
        firstAccommodation.setDescription("Šišanje sa škarama i/ili mašinicom");
        firstAccommodation.setPrice(30);
        firstAccommodation.setDuration(15);

        Accommodation secondAccommodation = new Accommodation();
        secondAccommodation.setId(2L);
        secondAccommodation.setName("Uređivanje brade");
        secondAccommodation.setDescription("Uređivanje brade sa škarama, mašinicom");
        secondAccommodation.setPrice(20);
        secondAccommodation.setDuration(15);

        Indent firstIndent = new Indent();
        firstIndent.setId(1L);
        firstIndent.setReservationDate(LocalDate.of(2020, 11, 20));
        firstIndent.setReservationTimeFrom(parseToTime("10:15"));
        firstIndent.setReservationTimeTo(parseToTime("10:35"));

        Indent secondIndent = new Indent();
        secondIndent.setId(2L);
        secondIndent.setReservationDate(LocalDate.of(2020, 11, 20));
        secondIndent.setReservationTimeFrom(parseToTime("11:10"));
        secondIndent.setReservationTimeTo(parseToTime("11:45"));

        Indent thirdIndent = new Indent();
        thirdIndent.setId(3L);
        thirdIndent.setReservationDate(LocalDate.of(2020, 11, 20));
        thirdIndent.setReservationTimeFrom(parseToTime("12:15"));
        thirdIndent.setReservationTimeTo(parseToTime("12:30"));

        Indent fourthIndent = new Indent();
        fourthIndent.setId(4L);
        fourthIndent.setReservationDate(LocalDate.of(2020, 11, 21));
        fourthIndent.setReservationTimeFrom(parseToTime("12:55"));
        fourthIndent.setReservationTimeTo(parseToTime("13:10"));

        Indent fifthIndent = new Indent();
        fifthIndent.setId(5L);
        fifthIndent.setReservationDate(LocalDate.of(2020, 11, 20));
        fifthIndent.setReservationTimeFrom(parseToTime("14:15"));
        fifthIndent.setReservationTimeTo(parseToTime("14:50"));

        List<Indent> indents = new ArrayList<>();
        indents.add(firstIndent);
        indents.add(secondIndent);
        indents.add(thirdIndent);
        indents.add(fourthIndent);
        indents.add(fifthIndent);

        firstAccommodation.setIndents(Collections.singletonList(firstIndent));

        Mockito.when(indentService.findAllForChosenDate(Date.valueOf(LocalDate.of(2020, 11, 20)))).thenReturn(indents);
    }

    @Test
    public void testFindAllAvailableTerms() {
        final List<LocalTime> searchedAvailableTerms = availableTermService.findAllAvailableTerms(
                Date.valueOf(LocalDate.of(2020, 11, 20)),
                Collections.singletonList(firstAccommodation));

        Assertions.assertEquals(105, searchedAvailableTerms.size());
    }

    private static LocalTime parseToTime(String time) {
        return LocalTime.parse(time);
    }
}