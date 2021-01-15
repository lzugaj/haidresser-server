package com.luv2code.hairdresser.service.impl;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.service.CalculationService;
import com.luv2code.hairdresser.service.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

@Service
public class CalculationServiceImpl implements CalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationServiceImpl.class);

    private final ParseService parseService;

    @Autowired
    public CalculationServiceImpl(final ParseService parseService) {
        this.parseService = parseService;
    }

    @Override
    public Time calculateReservationTimeTo(final Indent chosenIndent) {
        Integer timeRequiredToPerformChosenAccommodations = 0;
        for (Accommodation accommodation : chosenIndent.getAccommodations()) {
            timeRequiredToPerformChosenAccommodations += accommodation.getDuration();
        }

        LOGGER.info("Time required to perform chosen accommodations is: ´{}´ minutes.", timeRequiredToPerformChosenAccommodations);

        final Calendar calendar = addMinutesToCalendar(chosenIndent, timeRequiredToPerformChosenAccommodations);
        LOGGER.info("Calculated calendar day for reservation is: ´{}´.", calendar);

        return parseService.parseToTime(String.valueOf(calendar.getTime()));
    }

    private Calendar addMinutesToCalendar(final Indent chosenIndent, final Integer timeRequiredToPerformChosenAccommodations) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(Time.valueOf(chosenIndent.getReservationTimeFrom()));
        calendar.add(Calendar.MINUTE, timeRequiredToPerformChosenAccommodations);
        return calendar;
    }

    @Override
    public Boolean calculateIsReservationDateEqualsChosenDate(final Indent currentIteratedIndent, final Date chosenDate) {
        final Date currentIteratedIndentDate = parseService.parseToDate(String.valueOf(currentIteratedIndent.getReservationDate()));
        LOGGER.info("Current iterated Indent date is: ´{}´.", currentIteratedIndentDate);

        return parseService.parseToString(currentIteratedIndentDate).equals(parseService.parseToString(chosenDate));
    }
}
