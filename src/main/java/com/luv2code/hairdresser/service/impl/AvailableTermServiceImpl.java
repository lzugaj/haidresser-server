package com.luv2code.hairdresser.service.impl;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.service.AvailableTermService;
import com.luv2code.hairdresser.service.IndentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Service
public class AvailableTermServiceImpl implements AvailableTermService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailableTermServiceImpl.class);

    private static final String WORKING_FROM = "08:00";
    private static final String WORKING_TO = "18:00";
    private static final long ONE_MINUTE_IN_MILLIS = 60000;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    private final IndentService indentService;

    @Autowired
    public AvailableTermServiceImpl(final IndentService indentService) {
        this.indentService = indentService;
    }

    // TODO: @lzugaj - Exception handler for term before or after working hour
    // TODO: @lzugaj - Refactor!!! Prebaciti u Calculation i Parse service dio toga
    @Override
    public List<Time> findAllAvailableTerms(final Date chosenDate, final List<Accommodation> chosenUserAccommodations) throws ParseException {
        LOGGER.info("Searching all available terms on chosen date: ´{}´.", chosenDate);

        final List<Time> searchedAvailableTerms = new ArrayList<>();
        final List<Indent> indents = indentService.findAllForChosenDate(chosenDate);
        LOGGER.info("Successfully founded all Indents for chosen date: ´{}´.", chosenDate);

        final Integer timeRequiredToPerformChosenAccommodations = getTotalDurationForChosenAccommodations(chosenUserAccommodations);
        LOGGER.info("Time required to perform chosen accommodations is: ´{}´.", timeRequiredToPerformChosenAccommodations);

        findAllTermsBeforeFirstTermThatDay(indents, searchedAvailableTerms, timeRequiredToPerformChosenAccommodations);
        for (int i = 0; i < indents.size(); i++) {
            if (indents.size() > (i + 1)) {
                String currentIndentReservationTimeToString = String.valueOf(indents.get(i).getReservationTimeTo());
                Date currentDate = (Date) simpleDateFormat.parse(currentIndentReservationTimeToString);

                String nextIndentReservationTimeFromString = String.valueOf(indents.get(i + 1).getReservationTimeFrom());
                Date nextDate = (Date) simpleDateFormat.parse(nextIndentReservationTimeFromString);

                for (Date j = currentDate; j.before(nextDate); calculateNextDateTerm(j, 5)) {
                    Date calculatedNextDateTerm = calculateNextDateTerm(j, timeRequiredToPerformChosenAccommodations);
                    if (!calculatedNextDateTerm.after(nextDate)) {
                        String currentIteratedDateString = String.valueOf(j).substring(11, 16);
                        searchedAvailableTerms.add(parseTime(currentIteratedDateString));
                    }

                    currentDate = calculateNextDateTerm(j, 5);
                    j = currentDate;
                }

            } else {
                searchedAvailableTerms.add(Time.valueOf(indents.get(i).getReservationTimeTo()));
            }
        }

        findAllTermsAfterLastTermThatDay(searchedAvailableTerms, timeRequiredToPerformChosenAccommodations);

        LOGGER.info("Founded ´{}´ available terms on chosen date: ´{}´.", searchedAvailableTerms.size(), chosenDate);
        return searchedAvailableTerms;
    }

    private void findAllTermsBeforeFirstTermThatDay(final List<Indent> indents, final List<Time> searchedAvailableTerms, final Integer timeRequiredToPerformChosenAccommodations) throws ParseException {
        String firstIndentReservationTimeToString = String.valueOf(indents.get(0).getReservationTimeFrom());
        Date firstIndentReservationTimeToDate = parseDate(firstIndentReservationTimeToString);
        Date firstAvailableReservationTimeToOnWorkingDay = parseDate(WORKING_FROM);
        for (Date j = firstAvailableReservationTimeToOnWorkingDay; j.before(firstIndentReservationTimeToDate); calculateNextDateTerm(j, 5)) {
            Date calculatedNextDateTerm = calculateNextDateTerm(firstAvailableReservationTimeToOnWorkingDay, timeRequiredToPerformChosenAccommodations);
            if (!calculatedNextDateTerm.after(firstIndentReservationTimeToDate)) {
                String currentIteratedDateString = String.valueOf(j).substring(11, 16);
                searchedAvailableTerms.add(parseTime(currentIteratedDateString));
            }

            firstAvailableReservationTimeToOnWorkingDay = calculatedNextDateTerm;
            j = firstAvailableReservationTimeToOnWorkingDay;
        }
    }

    private void findAllTermsAfterLastTermThatDay(final List<Time> searchedAvailableTerms, final Integer timeRequiredToPerformChosenAccommodations) throws ParseException {
        String lastIndentReservationTimeToString = String.valueOf(searchedAvailableTerms.get(searchedAvailableTerms.size() - 1));
        Date lastIndentReservationTimeToDate = parseDate(lastIndentReservationTimeToString);
        Date lastAvailableReservationTimeToOnWorkingDayString = parseDate(WORKING_TO);
        for (Date j = lastIndentReservationTimeToDate; j.before(lastAvailableReservationTimeToOnWorkingDayString); calculateNextDateTerm(j, 5)) {
            Date calculatedNextDateTerm = calculateNextDateTerm(lastIndentReservationTimeToDate, timeRequiredToPerformChosenAccommodations);
            if (!calculatedNextDateTerm.after(lastAvailableReservationTimeToOnWorkingDayString)) {
                String currentIteratedDateString = String.valueOf(j).substring(11, 16);
                searchedAvailableTerms.add(parseTime(currentIteratedDateString));
            }

            lastIndentReservationTimeToDate = calculatedNextDateTerm;
            j = lastIndentReservationTimeToDate;
        }
    }

    private Integer getTotalDurationForChosenAccommodations(final List<Accommodation> chosenUserAccommodations) {
        Integer timeRequiredToPerformChosenAccommodations = 0;
        for (Accommodation accommodation : chosenUserAccommodations) {
            timeRequiredToPerformChosenAccommodations += accommodation.getDuration();
        }

        return timeRequiredToPerformChosenAccommodations;
    }

    private Time parseTime(final String time) {
        return Time.valueOf(LocalTime.parse(time));
    }

    private Date parseDate(final String dateInString) throws ParseException {
        return (Date) simpleDateFormat.parse(dateInString);
    }

    private Date calculateNextDateTerm(final Date date, final Integer minutesToIncrementGivenTime) {
        final long currentTimeInMilliseconds = date.getTime();
        return new Date(currentTimeInMilliseconds + (minutesToIncrementGivenTime * ONE_MINUTE_IN_MILLIS));
    }
}
