//package com.luv2code.hairdresser.controller;
//
//import com.luv2code.hairdresser.domain.Accommodation;
//import com.luv2code.hairdresser.domain.Indent;
//import com.luv2code.hairdresser.domain.Role;
//import com.luv2code.hairdresser.domain.User;
//import com.luv2code.hairdresser.service.IndentService;
//import org.hamcrest.CoreMatchers;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.BDDMockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@WebMvcTest(IndentController.class)
//public class IndentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private IndentService indentService;
//
//    private User firstUser;
//    private User secondUser;
//
//    private Indent firstIndent;
//    private Indent secondIndent;
//    private Indent thirdIndent;
//
//    List<Indent> indents;
//    List<Indent> indentsForChosenDate;
//    List<Indent> indentsForUser;
//
//    @BeforeEach
//    public void setup() {
//        Role role = new Role();
//        role.setId(1L);
//        role.setName("USER");
//        role.setDescription("User role");
//
//        firstUser = new User();
//        firstUser.setId(1L);
//        firstUser.setRole(role);
//        firstUser.setFirstName("Michael");
//        firstUser.setLastName("Jordan");
//        firstUser.setUsername("MJ");
//        firstUser.setPassword("michael23");
//        firstUser.setEmail("mj23@gmail.com");
//        firstUser.setPhoneNumber("+385914541239");
//        firstUser.setNumberOfReservations(0);
//        firstUser.setIsActive(true);
//        firstUser.setHasReservedIndent(false);
//
//        secondUser = new User();
//        secondUser.setId(1L);
//        secondUser.setRole(role);
//        secondUser.setFirstName("Lebron");
//        secondUser.setLastName("James");
//        secondUser.setUsername("LBJ");
//        secondUser.setPassword("theChosenOne");
//        secondUser.setEmail("lebron.james23@gmail.com");
//        secondUser.setPhoneNumber("+385984771631");
//        secondUser.setNumberOfReservations(0);
//        secondUser.setIsActive(true);
//        secondUser.setHasReservedIndent(true);
//
//        List<User> users = new ArrayList<>();
//        users.add(firstUser);
//        users.add(secondUser);
//        role.setUsers(users);
//
//        Accommodation firstAccommodation = new Accommodation();
//        firstAccommodation.setId(1L);
//        firstAccommodation.setName("Šišanje");
//        firstAccommodation.setDescription("Šišanje sa škarama i/ili mašinicom");
//        firstAccommodation.setPrice(30);
//        firstAccommodation.setDuration(15);
//
//        Accommodation secondAccommodation = new Accommodation();
//        secondAccommodation.setId(2L);
//        secondAccommodation.setName("Farbanje");
//        secondAccommodation.setDescription("Farbanje kose");
//        secondAccommodation.setPrice(40);
//        secondAccommodation.setDuration(30);
//
//        firstIndent = new Indent();
//        firstIndent.setId(1L);
//        firstIndent.setReservationDate(LocalDateTime.of(2020, 12, 22, 11, 30, 20));
//        firstIndent.setReservationTimeFrom(parseToTime("11:10"));
//        firstIndent.setReservationTimeTo(parseToTime("11:25"));
//        firstIndent.setUser(firstUser);
//        firstIndent.setAccommodations(Collections.singletonList(firstAccommodation));
//
//        secondIndent = new Indent();
//        secondIndent.setId(2L);
//        secondIndent.setReservationDate(LocalDateTime.of(2020, 12, 22, 11, 30, 20));
//        secondIndent.setReservationTimeFrom(parseToTime("13:10"));
//        secondIndent.setReservationTimeTo(parseToTime("13:40"));
//        secondIndent.setUser(secondUser);
//        secondIndent.setAccommodations(Collections.singletonList(secondAccommodation));
//
//        thirdIndent = new Indent();
//        thirdIndent.setId(3L);
//        thirdIndent.setReservationDate(LocalDateTime.of(2020, 12, 22, 11, 30, 20));
//        thirdIndent.setReservationTimeFrom(parseToTime("13:40"));
//        thirdIndent.setReservationTimeTo(parseToTime("13:55"));
//        thirdIndent.setUser(firstUser);
//        thirdIndent.setAccommodations(Collections.singletonList(firstAccommodation));
//
//        firstAccommodation.setIndents(Collections.singletonList(firstIndent));
//        firstAccommodation.setIndents(Collections.singletonList(thirdIndent));
//        secondAccommodation.setIndents(Collections.singletonList(secondIndent));
//
//        indents = new ArrayList<>();
//        indents.add(firstIndent);
//        indents.add(secondIndent);
//        indents.add(thirdIndent);
//
//        indentsForChosenDate = new ArrayList<>();
//        indentsForChosenDate.add(firstIndent);
//
//        indentsForUser = new ArrayList<>();
//        indentsForUser.add(firstIndent);
//        indentsForUser.add(thirdIndent);
//
//        firstUser.setIndents(Collections.singletonList(firstIndent));
//        firstUser.setIndents(Collections.singletonList(thirdIndent));
//        secondUser.setIndents(Collections.singletonList(secondIndent));
//
//        BDDMockito.given(indentService.findById(firstIndent.getId())).willReturn(firstIndent);
//        BDDMockito.given(indentService.findAll()).willReturn(indents);
//        BDDMockito.given(indentService.findAllForChosenDate(parseToDate("2020-12-22"))).willReturn(indentsForChosenDate);
//        BDDMockito.given(indentService.findAllForUser(firstUser.getUsername())).willReturn(indentsForUser);
//    }
//
//    @Test
//    public void testFindById() throws Exception {
//        this.mockMvc
//                .perform(
//                    MockMvcRequestBuilders.get("/indent/{id}", firstIndent.getId()))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath(
//                        "id", CoreMatchers.is(1)));
//    }
//
//    @Test
//    public void testFindAll() throws Exception {
//        this.mockMvc
//                .perform(
//                        MockMvcRequestBuilders.get("/indent"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(3)));
//    }
//
//    @Test
//    public void testFindForChosenDate() throws Exception {
//        this.mockMvc
//                .perform(
//                        MockMvcRequestBuilders.get("/indent")
//                    .param("chosenDate", String.valueOf(parseToDate("2020-12-22"))))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(1)));
//    }
//
//    @Test
//    public void testFindForUser() throws Exception {
//        this.mockMvc
//                .perform(
//                        MockMvcRequestBuilders.get("/indent/user/{username}", firstUser.getUsername()))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)));
//    }
//
//    private static Time parseToTime(final String time) {
//        return Time.valueOf(LocalTime.parse(time));
//    }
//
//    private Date parseToDate(final String dateString) {
//        return Date.valueOf(dateString);
//    }
//}
