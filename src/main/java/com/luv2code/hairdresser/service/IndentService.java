package com.luv2code.hairdresser.service;

import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.User;

import java.sql.Date;
import java.util.List;

public interface IndentService {

    Indent save(final User user, final Indent indent);

    Indent findById(final Long id);

    List<Indent> findAll();

    List<Indent> findAllForUser(final User user);

    List<Indent> findAllForChosenDate(final Date chosenDate);

    Indent update(final Indent oldIndent, final Indent newIndent);

    void delete(final User user, final Indent indent);

}
