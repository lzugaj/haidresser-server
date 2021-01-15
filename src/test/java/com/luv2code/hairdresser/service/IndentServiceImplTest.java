package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.Role;
import com.luv2code.hairdresser.domain.User;
import com.luv2code.hairdresser.exception.EntityNotFoundException;
import com.luv2code.hairdresser.exception.IndentNotActiveException;
import com.luv2code.hairdresser.exception.UserAlreadyHasReservedIndentException;
import com.luv2code.hairdresser.repository.IndentRepository;
import com.luv2code.hairdresser.service.impl.IndentServiceImpl;
import org.junit.Assert;
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

        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        role.setDescription("User role");

        firstUser = new User();
        firstUser.setId(1L);
        firstUser.setRole(role);
        firstUser.setFirstName("Michael");
        firstUser.setLastName("Jordan");
        firstUser.setUsername("MJ");
        firstUser.setPassword("michael23");
        firstUser.setEmail("mj23@gmail.com");
        firstUser.setPhoneNumber("+385914541239");
        firstUser.setNumberOfReservations(1);
        firstUser.setIsActive(true);
        firstUser.setHasReservedIndent(false);

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setRole(role);
        secondUser.setFirstName("Lebron");
        secondUser.setLastName("James");
        secondUser.setUsername("LBJ");
        secondUser.setPassword("theChosenOne");
        secondUser.setEmail("lebron.james23@gmail.com");
        secondUser.setPhoneNumber("+385984771631");
        secondUser.setNumberOfReservations(0);
        secondUser.setIsActive(true);
        secondUser.setHasReservedIndent(true);

        List<User> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);
        role.setUsers(users);

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
        firstIndent.setIsActive(false);
        firstIndent.setAccommodations(Collections.singletonList(firstAccommodation));

        secondIndent = new Indent();
        secondIndent.setId(2L);
        secondIndent.setReservationDate(LocalDate.of(2020, 11, 28));
        secondIndent.setReservationTimeFrom(parseToTime("13:10"));
        secondIndent.setReservationTimeTo(parseToTime("13:40"));
        secondIndent.setIsActive(true);
        secondIndent.setUser(secondUser);
        secondIndent.setAccommodations(Collections.singletonList(secondAccommodation));

        thirdIndent = new Indent();
        thirdIndent.setId(3L);
        thirdIndent.setReservationDate(LocalDate.of(2020, 11, 28));
        thirdIndent.setReservationTimeFrom(parseToTime("13:40"));
        thirdIndent.setReservationTimeTo(parseToTime("13:55"));
        thirdIndent.setIsActive(true);
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
    public void should_Save_When_User_Not_Have_Reserved_Indent() {
        final Indent newIndent = indentService.save(firstUser, firstIndent);

        Assertions.assertNotNull(newIndent);
        Assertions.assertEquals("1", String.valueOf(newIndent.getId()));
        Assertions.assertEquals("11:25", String.valueOf(newIndent.getReservationTimeTo()));
        Assertions.assertEquals("MJ", firstIndent.getUser().getUsername());
        Assertions.assertEquals("2", String.valueOf(firstUser.getNumberOfReservations()));
        Assertions.assertTrue(newIndent.getIsActive());
        Assertions.assertTrue(newIndent.getUser().getHasReservedIndent());
    }

    @Test
    public void should_Thrown_UserAlreadyHasReservedIndentException_When_User_Want_Reserved_Another_Indent() {
        Mockito.when(indentRepository.save(secondIndent))
                .thenThrow(new UserAlreadyHasReservedIndentException(
                        "User",
                        "hasReservedIndent",
                        String.valueOf(secondUser.getHasReservedIndent())));

        final Exception exception = Assert.assertThrows(
                UserAlreadyHasReservedIndentException.class,
                () -> indentService.save(secondUser, secondIndent));

        final String expectedMessage = "Entity 'User' with 'hasReservedIndent' value 'true' already has an reserved indent.";
        final String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_Return_Indent_When_Id_Is_Founded() {
        final Indent searchedIndent = indentService.findById(secondIndent.getId());

        Assertions.assertNotNull(searchedIndent);
        Assertions.assertEquals("2", String.valueOf(searchedIndent.getId()));
    }

    @Test
    public void should_Thrown_EntityNotFoundException_When_Id_Is_Not_Founded() {
        Mockito.when(indentRepository.findById(thirdIndent.getId()))
                .thenThrow(new EntityNotFoundException(
                        "Indent",
                        "id",
                        String.valueOf(thirdIndent.getId())));

        final Exception exception = Assert.assertThrows(
                EntityNotFoundException.class,
                () -> indentService.findById(thirdIndent.getId()));

        final String expectedMessage = "Entity 'Indent' with 'id' value '3' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_Return_All_Indents() {
        final List<Indent> searchedIndents = indentService.findAll();

        Assertions.assertNotNull(searchedIndents);
        Assertions.assertEquals(2, searchedIndents.size());
    }

    @Test
    public void should_Return_All_Indents_For_Given_Chosen_Date() {
        final List<Indent> searchedIndents = indentService.findAllForChosenDate(parseToDate("2020-11-28"));

        Assertions.assertNotNull(searchedIndents);
        Assertions.assertEquals(1, searchedIndents.size());
    }

    @Test
    public void should_Return_All_Indents_For_User_With_Given_Username() {
        final List<Indent> searchedIndents = indentService.findAllForUser(secondUser.getUsername());

        Assertions.assertNotNull(searchedIndents);
        Assertions.assertEquals(1, searchedIndents.size());
    }

    @Test
    public void should_Delete_Indent_Successfully() {
        indentService.delete(firstUser, thirdIndent);

        Mockito.verify(indentRepository, Mockito.times(1)).delete(thirdIndent);
        Assertions.assertEquals(1, firstUser.getIndents().size());
        Assertions.assertFalse(firstUser.getHasReservedIndent());
        Assertions.assertEquals("0", String.valueOf(firstUser.getNumberOfReservations()));
    }

    @Test
    public void should_Delete_Indent_Successfaully() {
        Mockito.doThrow(new IndentNotActiveException("Indent", "id", String.valueOf(secondIndent.getId())))
                .when(indentService).delete(secondUser, secondIndent);
    }

    private static LocalTime parseToTime(String time) {
        return LocalTime.parse(time);
    }

    private Date parseToDate(final String dateString) {
        return Date.valueOf(dateString);
    }
}
