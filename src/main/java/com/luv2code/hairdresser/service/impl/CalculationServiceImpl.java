package com.luv2code.hairdresser.service.impl;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.service.CalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

@Service
public class CalculationServiceImpl implements CalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationServiceImpl.class);

    @Override
    public Time calculateReservationTimeTo(final Indent chosenIndent) {
        Integer timeRequiredToPerformChosenAccommodations = 0;
        for (Accommodation accommodation : chosenIndent.getAccommodations()) {
            LOGGER.info("Currently iterated Accommodation is: ´{}´ - ´{}´ min.", accommodation.getName(), accommodation.getDuration());
            timeRequiredToPerformChosenAccommodations += accommodation.getDuration();
        }

        LOGGER.info("Time required to perform chosen accommodations is: ´{}´ minutes.", timeRequiredToPerformChosenAccommodations);

        final Calendar calendar = addMinutesToCalendar(chosenIndent, timeRequiredToPerformChosenAccommodations);
        LOGGER.info("Calculated calendar day for reservation is: ´{}´.", calendar);
        return Time.valueOf(String.valueOf(calendar.getTime()).substring(11, 19));
    }

    private Calendar addMinutesToCalendar(final Indent chosenIndent, final Integer timeRequiredToPerformChosenAccommodations) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(Time.valueOf(chosenIndent.getReservationTimeFrom()));
        calendar.add(Calendar.MINUTE, timeRequiredToPerformChosenAccommodations);
        return calendar;
    }

    @Override
    public Boolean calculateIsReservationDateEqualsChosenDate(final Indent currentIteratedIndent, final Date chosenDate) {
        final Date currentIteratedIndentDate = Date.valueOf(currentIteratedIndent.getReservationDate());
        LOGGER.info("Current iterated Indent date is: ´{}´.", currentIteratedIndentDate);
        return currentIteratedIndentDate.compareTo(chosenDate) == 0;
    }
}
