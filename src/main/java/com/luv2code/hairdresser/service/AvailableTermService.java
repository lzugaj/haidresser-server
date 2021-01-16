package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Accommodation;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.List;

public interface AvailableTermService {

    List<LocalTime> findAllAvailableTerms(final Date chosenDate, final List<Accommodation> chosenUserAccommodations) throws ParseException;

}
