package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.Role;
import com.luv2code.hairdresser.domain.User;
import com.luv2code.hairdresser.exception.EntityNotFoundException;
import com.luv2code.hairdresser.exception.IndentStatusNotReservedException;
import com.luv2code.hairdresser.exception.UserAlreadyHasReservedIndentException;
import com.luv2code.hairdresser.repository.IndentRepository;
import com.luv2code.hairdresser.service.impl.IndentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.luv2code.hairdresser.domain.enums.IndentType.FINISHED;
import static com.luv2code.hairdresser.domain.enums.IndentType.RESERVED;
import static com.luv2code.hairdresser.domain.enums.RoleType.USER;
import static com.luv2code.hairdresser.domain.enums.StatusType.ACTIVE;

@SpringBootTest
public class IndentServiceImplTest {

    @InjectMocks
    private IndentServiceImpl indentService;

    @Mock
    private IndentRepository indentRepository;

    @Mock
    private CalculationService calculationService;

    private User firstUser;
    private User secondUser;

    private Indent firstIndent;
    private Indent secondIndent;
    private Indent thirdIndent;

    @BeforeEach
    public void setup() {
        indentService = Mockito.spy(new IndentServiceImpl(indentRepository, calculationService));

        final Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName(USER);
        userRole.setDescription("The user manager can perform CRUD operations over his/her profile and indents.");

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        firstUser = new User();
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

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setRoles(roles);
        secondUser.setFirstName("Lebron");
        secondUser.setLastName("James");
        secondUser.setUsername("LBJ");
        secondUser.setPassword("theChosenOne");
        secondUser.setEmail("lebron.james23@gmail.com");
        secondUser.setPhoneNumber("+385984771631");
        secondUser.setNumberOfReservations(0);
        firstUser.setStatus(ACTIVE);
        secondUser.setHasReservedIndent(true);

        List<User> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);
//        userRole.setUsers(users);

        Accommodation firstAccommodation = new Accommodation();
        firstAccommodation.setId(1L);
        firstAccommodation.setName("Šišanje");
        firstAccommodation.setDescription("Šišanje sa škarama i/ili mašinicom");
        firstAccommodation.setPrice(30);
        firstAccommodation.setDuration(15);

        Accommodation secondAccommodation = new Accommodation();
        secondAccommodation.setId(2L);
        secondAccommodation.setName("Farbanje");
        secondAccommodation.setDescription("Farbanje kose");
        secondAccommodation.setPrice(40);
        secondAccommodation.setDuration(30);

        firstIndent = new Indent();
        firstIndent.setId(1L);
        firstIndent.setReservationDate(LocalDate.of(2020, 11, 28));
        firstIndent.setReservationTimeFrom(parseToTime("11:10"));
        firstIndent.setStatus(RESERVED);
        firstIndent.setAccommodations(Collections.singletonList(firstAccommodation));

        secondIndent = new Indent();
        secondIndent.setId(2L);
        secondIndent.setReservationDate(LocalDate.of(2020, 11, 28));
        secondIndent.setReservationTimeFrom(parseToTime("13:10"));
        secondIndent.setReservationTimeTo(parseToTime("13:40"));
        secondIndent.setStatus(FINISHED);
        secondIndent.setUser(secondUser);
        secondIndent.setAccommodations(Collections.singletonList(secondAccommodation));

        thirdIndent = new Indent();
        thirdIndent.setId(3L);
        thirdIndent.setReservationDate(LocalDate.of(2020, 11, 28));
        thirdIndent.setReservationTimeFrom(parseToTime("13:40"));
        thirdIndent.setReservationTimeTo(parseToTime("13:55"));
        thirdIndent.setStatus(FINISHED);
        thirdIndent.setUser(firstUser);
        thirdIndent.setAccommodations(Collections.singletonList(firstAccommodation));

        firstAccommodation.setIndents(Collections.singletonList(firstIndent));
        firstAccommodation.setIndents(Collections.singletonList(thirdIndent));
        secondAccommodation.setIndents(Collections.singletonList(secondIndent));

        List<Indent> indents = new ArrayList<>();
        indents.add(secondIndent);
        indents.add(thirdIndent);

        firstUser.setIndents(Collections.singletonList(firstIndent));
        firstUser.setIndents(Collections.singletonList(thirdIndent));
        secondUser.setIndents(Collections.singletonList(secondIndent));

        Mockito.when(indentRepository.save(firstIndent)).thenReturn(firstIndent);
        Mockito.when(indentRepository.findById(secondIndent.getId())).thenReturn(java.util.Optional.ofNullable(secondIndent));
        Mockito.when(indentRepository.findAll()).thenReturn(indents);

        Mockito.when(calculationService.calculateReservationTimeTo(firstIndent)).thenReturn(Time.valueOf("11:25:00"));
        Mockito.when(calculationService.calculateIsReservationDateEqualsChosenDate(secondIndent, parseToDate("2020-11-28"))).thenReturn(true);
    }

    @Test
    public void should_Save_When_Not_Have_Reserved_More_Then_One() {
        final Indent newIndent = indentService.save(firstUser, firstIndent);

        Assertions.assertNotNull(newIndent);
        Assertions.assertEquals("1", String.valueOf(newIndent.getId()));
        Assertions.assertEquals("11:25", String.valueOf(newIndent.getReservationTimeTo()));
        Assertions.assertEquals("MJ", firstIndent.getUser().getUsername());
        Assertions.assertEquals("2", String.valueOf(firstUser.getNumberOfReservations()));
        Assertions.assertEquals("RESERVED", String.valueOf(newIndent.getStatus()));
        Assertions.assertTrue(newIndent.getUser().getHasReservedIndent());
    }

    @Test
    public void should_Throw_Exception_When_Have_Reserved_More_Then_One_Save() {
        Mockito.when(indentRepository.save(secondIndent))
                .thenThrow(new UserAlreadyHasReservedIndentException(
                        "User",
                        "id",
                        String.valueOf(secondUser.getId())));

        final Exception exception = Assertions.assertThrows(
                UserAlreadyHasReservedIndentException.class,
                () -> indentService.save(secondUser, secondIndent));

        final String expectedMessage = "Entity 'User' with 'id' value '2' already has a reserved indent.";
        final String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_Return_When_Id_Is_Founded() {
        final Indent searchedIndent = indentService.findById(secondIndent.getId());

        Assertions.assertNotNull(searchedIndent);
        Assertions.assertEquals("2", String.valueOf(searchedIndent.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Founded() {
        Mockito.when(indentRepository.findById(thirdIndent.getId()))
                .thenThrow(new EntityNotFoundException(
                        "Indent",
                        "id",
                        String.valueOf(thirdIndent.getId())));

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> indentService.findById(thirdIndent.getId()));

        final String expectedMessage = "Entity 'Indent' with 'id' value '3' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_Return_All() {
        final List<Indent> searchedIndents = indentService.findAll();

        Assertions.assertNotNull(searchedIndents);
        Assertions.assertEquals(2, searchedIndents.size());
    }

    @Test
    public void should_Return_All_For_Given_Chosen_Date() {
        final List<Indent> searchedIndents = indentService.findAllForChosenDate(parseToDate("2020-11-28"));

        Assertions.assertNotNull(searchedIndents);
        Assertions.assertEquals(1, searchedIndents.size());
    }

    @Test
    public void should_Return_All_For_Given_Username() {
        final List<Indent> searchedIndents = indentService.findAllForUser(secondUser);

        Assertions.assertNotNull(searchedIndents);
        Assertions.assertEquals(1, searchedIndents.size());
    }

    @Test
    public void should_Delete_When_Not_Have_Reserved_More_Then_One() {
        indentService.delete(firstUser, thirdIndent);

        Mockito.verify(indentRepository, Mockito.times(1)).delete(thirdIndent);
        Assertions.assertEquals(1, firstUser.getIndents().size());
        Assertions.assertFalse(firstUser.getHasReservedIndent());
        Assertions.assertEquals("0", String.valueOf(firstUser.getNumberOfReservations()));
    }

    @Test
    public void should_Throw_Exception_When_Have_Reserved_More_Then_One_Delete() {
        Mockito.doThrow(new IndentStatusNotReservedException(
                "Indent", "id", String.valueOf(secondIndent.getId())))
                .when(indentService).delete(secondUser, secondIndent);

        final Exception exception = Assertions.assertThrows(
                IndentStatusNotReservedException.class,
                () -> indentService.delete(secondUser, secondIndent));

        final String expectedMessage = "Entity 'Indent' with 'id' value '2' has not reserved status so the requested action cannot be performed.";
        final String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    private static LocalTime parseToTime(String time) {
        return LocalTime.parse(time);
    }

    private Date parseToDate(final String dateString) {
        return Date.valueOf(dateString);
    }
}
