package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Accommodation;
import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.User;

import java.sql.Date;
import java.util.List;

public interface IndentService {

    Indent save(final User user, final Indent indent);

    Indent findById(final Long id);

    List<Indent> findAll();

    List<Indent> findAllForChosenDate(final Date chosenDate);

    List<Indent> findAllForUser(final String username);

    Indent update(final Indent oldIndent, final Indent newIndent, final List<Accommodation> accommodations);

    void delete(final User user, final Indent indent);

}
