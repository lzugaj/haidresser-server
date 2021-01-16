package com.luv2code.hairdresser.service.impl;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.service.AvailableTermService;
import com.luv2code.hairdresser.service.IndentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailableTermServiceImpl implements AvailableTermService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableTermServiceImpl.class);

    private static final String WORKING_FROM = "08:00:00";
    private static final String WORKING_TO = "18:00:00";
    private static final long ONE_MINUTE_IN_MILLIS = 60000;

    private final IndentService indentService;

    @Autowired
    public AvailableTermServiceImpl(final IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public List<LocalTime> findAllAvailableTerms(final Date chosenDate, final List<Accommodation> chosenUserAccommodations) {
        LOGGER.info("Searching all available terms on chosen date: ´{}´.", chosenDate);

        final List<Time> searchedAvailableTerms = new ArrayList<>();
        final List<Indent> indents = indentService.findAllForChosenDate(chosenDate);
        LOGGER.info("Successfully founded all Indents for chosen date: ´{}´.", chosenDate);

        final Integer timeRequiredToPerformChosenAccommodations = getTotalDurationForChosenAccommodations(chosenUserAccommodations);
        LOGGER.info("Time required to perform chosen accommodations is: ´{}´.", timeRequiredToPerformChosenAccommodations);

        findAllTermsBeforeFirstTermThatDay(indents, searchedAvailableTerms, timeRequiredToPerformChosenAccommodations);
        LOGGER.info("Successfully founded all terms before first term that day.");

        for (int i = 0; i < indents.size(); i++) {
            if (indents.size() > (i + 1)) {
                Time currentDate = Time.valueOf(indents.get(i).getReservationTimeTo());
                LOGGER.info("Current Indent reservation time to is: ´{}´.", currentDate);

                Time nextDate = Time.valueOf(indents.get(i + 1).getReservationTimeTo());
                LOGGER.info("Next Indent reservation time to is: ´{}´.", nextDate);

                searchAvailableTerms(currentDate, nextDate, searchedAvailableTerms, timeRequiredToPerformChosenAccommodations);
            } else {
                searchedAvailableTerms.add(Time.valueOf(indents.get(i).getReservationTimeTo()));
            }
        }

        findAllTermsAfterLastTermThatDay(searchedAvailableTerms, timeRequiredToPerformChosenAccommodations);
        LOGGER.info("Successfully founded all terms after last term that day.");
        LOGGER.info("Founded ´{}´ available terms on chosen date: ´{}´.", searchedAvailableTerms.size(), chosenDate);
        return parseToLocalTime(searchedAvailableTerms);
    }

    private Integer getTotalDurationForChosenAccommodations(final List<Accommodation> chosenUserAccommodations) {
        Integer timeRequiredToPerformChosenAccommodations = 0;
        for (Accommodation accommodation : chosenUserAccommodations) {
            timeRequiredToPerformChosenAccommodations += accommodation.getDuration();
        }

        return timeRequiredToPerformChosenAccommodations;
    }

    private void findAllTermsBeforeFirstTermThatDay(final List<Indent> indents, final List<Time> searchedAvailableTerms, final Integer timeRequiredToPerformChosenAccommodations) {
        LOGGER.info("Finding all terms before first term that day.");

        final Time firstIndentReservationTimeToDate = Time.valueOf(indents.get(0).getReservationTimeFrom());
        LOGGER.info("First Indent reservation time to is: ´{}´.", firstIndentReservationTimeToDate);

        Time firstAvailableReservationTimeToOnWorkingDay = Time.valueOf(WORKING_FROM);
        LOGGER.info("First available reservation time to on working day is: ´{}´.", firstAvailableReservationTimeToOnWorkingDay);

        searchAvailableTerms(firstAvailableReservationTimeToOnWorkingDay, firstIndentReservationTimeToDate, searchedAvailableTerms, timeRequiredToPerformChosenAccommodations);
    }

    private void findAllTermsAfterLastTermThatDay(final List<Time> searchedAvailableTerms, final Integer timeRequiredToPerformChosenAccommodations) {
        LOGGER.info("Finding all terms after last term that day.");

        Time lastIndentReservationTimeToDate = searchedAvailableTerms.get(searchedAvailableTerms.size() - 1);
        LOGGER.info("Last Indent reservation time to is: ´{}´.", lastIndentReservationTimeToDate);

        final Time lastAvailableReservationTimeToOnWorkingDayString = Time.valueOf(WORKING_TO);
        LOGGER.info("Last available reservation time to on working day is: ´{}´.", lastAvailableReservationTimeToOnWorkingDayString);

        searchAvailableTerms(lastIndentReservationTimeToDate, lastAvailableReservationTimeToOnWorkingDayString, searchedAvailableTerms, timeRequiredToPerformChosenAccommodations);
    }

    private void searchAvailableTerms(Time availableReservationTimeTo, final Time reservationTimeTo, final List<Time> searchedAvailableTerms, final Integer timeRequiredToPerformChosenAccommodations) {
        for (Time i = availableReservationTimeTo; i.before(reservationTimeTo); calculateNextDateTerm(i, 5)) {
            final Time calculatedNextDateTerm = calculateNextDateTerm(availableReservationTimeTo, timeRequiredToPerformChosenAccommodations);
            LOGGER.info("Successfully calculated next date term is: ´{}´.", calculatedNextDateTerm);

            if (!calculatedNextDateTerm.after(reservationTimeTo)) {
                String currentIteratedDateString = String.valueOf(i);
                searchedAvailableTerms.add(Time.valueOf(currentIteratedDateString));
            }

            availableReservationTimeTo = calculateNextDateTerm(i, 5);
            i = availableReservationTimeTo;
        }
    }

    private Time calculateNextDateTerm(final Time date, final Integer minutesToIncrementGivenTime) {
        final long currentTimeInMilliseconds = date.getTime();
        return new Time(currentTimeInMilliseconds + (minutesToIncrementGivenTime * ONE_MINUTE_IN_MILLIS));
    }

    private List<LocalTime> parseToLocalTime(final List<Time> searchedAvailableTerms) {
        final List<LocalTime> localTimes = new ArrayList<>();
        for (Time time : searchedAvailableTerms) {
            final String timeString = String.valueOf(time).substring(0, 5);
            localTimes.add(LocalTime.parse(timeString));
        }

        LOGGER.info("Successfully parsed all Times to LocalTimes for searchedAvailableTerms.");
        return localTimes;
    }
}
