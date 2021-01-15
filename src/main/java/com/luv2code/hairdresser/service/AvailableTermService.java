package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Accommodation;

import java.sql.Time;
import java.text.ParseException;
import java.sql.Date;
import java.util.List;

public interface AvailableTermService {

    List<Time> findAllAvailableTerms(final Date chosenDate, final List<Accommodation> chosenUserAccommodations) throws ParseException;

}
