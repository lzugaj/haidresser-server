package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Indent;

import java.sql.Date;
import java.sql.Time;

public interface CalculationService {

    Time calculateReservationTimeTo(final Indent chosenIndent);

    Boolean calculateIsReservationDateEqualsChosenDate(final Indent currentIteratedIndent, final Date chosenDate);

}
