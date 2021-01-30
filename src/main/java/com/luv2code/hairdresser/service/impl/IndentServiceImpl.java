package com.luv2code.hairdresser.service.impl;

import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.User;
import com.luv2code.hairdresser.exception.EntityNotFoundException;
import com.luv2code.hairdresser.exception.IndentStatusNotReservedException;
import com.luv2code.hairdresser.exception.UserAlreadyHasReservedIndentException;
import com.luv2code.hairdresser.repository.IndentRepository;
import com.luv2code.hairdresser.service.CalculationService;
import com.luv2code.hairdresser.service.IndentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.luv2code.hairdresser.domain.enums.IndentType.RESERVED;

@Service
public class IndentServiceImpl implements IndentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndentServiceImpl.class);

    private final IndentRepository indentRepository;

    private final CalculationService calculationService;

    @Autowired
    public IndentServiceImpl(final IndentRepository indentRepository,
                             final CalculationService calculationService) {
        this.indentRepository = indentRepository;
        this.calculationService = calculationService;
    }

    // TODO: @lzugaj - Scheduler for setting User hasReservedIndent variable to false.
    // TODO: @lzugaj - Scheduler for changing Indent status to IN_PROGRESS and FINISHED when Indent is currently in progress and then finished.
    // TODO: @lzugaj - Push notification for save, update and delete methods on User email.

    @Override
    public Indent save(final User user, final Indent indent) {
        if (!user.getHasReservedIndent()) {
            setupVariablesCreate(user, indent);
            LOGGER.info("Successfully setup variables for creating an Indent for User with id: ´{}´.", user.getId());

            final Indent newIndent = indentRepository.save(indent);
            LOGGER.info("Creating Indent with id: ´{}´.", indent.getId());
            return newIndent;
        } else {
            LOGGER.error("Cannot have more than one indent at same time for User with id: ´{}´.", user.getId());
            throw new UserAlreadyHasReservedIndentException("User", "id", String.valueOf(user.getId()));
        }
    }

    private void setupVariablesCreate(final User user, final Indent indent) {
        LOGGER.info("Setting up Indent parameters for Use with id: ´{}´.", user.getId());
        indent.setReservationTimeTo(calculateReservationTimeTo(indent));
        indent.setStatus(RESERVED);
        indent.setUser(user);

        LOGGER.info("Setting up parameters for User with id: ´{}´.", user.getId());
        user.setNumberOfReservations(user.getNumberOfReservations() + 1);
        user.setHasReservedIndent(true);
        user.setIndents(Collections.singletonList(indent));
    }

    @Override
    public Indent findById(final Long id) {
        final Optional<Indent> searchedIndent = indentRepository.findById(id);
        if (searchedIndent.isPresent()) {
            LOGGER.info("Searching Indent with id: ´{}´.", id);
            return searchedIndent.get();
        } else {
            LOGGER.error("Indent was not found with id: ´{}´.", id);
            throw new EntityNotFoundException("Indent", "id", String.valueOf(id));
        }
    }

    @Override
    public List<Indent> findAll() {
        final List<Indent> searchedIndents = indentRepository.findAll();
        LOGGER.info("Searching all Indents.");
        return searchedIndents;
    }

    @Override
    public List<Indent> findAllForUser(final User user) {
        final List<Indent> indents = findAll();
        LOGGER.info("Successfully founded all Indents.");

        LOGGER.info("Searching all Indents for User with id: ´{}´", user.getId());
        return indents.stream()
                .filter(order -> order.getUser().getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Indent> findAllForChosenDate(final Date chosenDate) {
        final List<Indent> indents = findAll();
        LOGGER.info("Searching all Indents for chosen date: ´{}´.", chosenDate);
        return indents.stream()
                .filter(indent -> calculationService.calculateIsReservationDateEqualsChosenDate(indent, chosenDate))
                .collect(Collectors.toList());
    }

    @Override
    public Indent update(final Indent oldIndent, final Indent newIndent) {
        if (oldIndent.getStatus().equals(RESERVED)) {
            setupVariablesUpdate(oldIndent, newIndent);
            LOGGER.info("Successfully setup variables for deleting an Indent with id: ´{}´.", oldIndent.getId());

            indentRepository.save(oldIndent);
            LOGGER.info("Updating Indent with id: ´{}´", oldIndent.getId());
            return oldIndent;
        } else {
            LOGGER.error("Cannot perform update action for inactive Indent with id: ´{}´.", oldIndent.getId());
            throw new IndentStatusNotReservedException("Indent", "id", String.valueOf(oldIndent.getId()));
        }
    }

    private void setupVariablesUpdate(final Indent oldIndent, final Indent newIndent) {
        LOGGER.info("Setting up parameters for Indent  with id: ´{}´.", oldIndent.getId());
        oldIndent.setReservationDate(newIndent.getReservationDate());
        oldIndent.setReservationTimeFrom(newIndent.getReservationTimeFrom());
        oldIndent.setReservationTimeTo(calculateReservationTimeTo(newIndent));
        oldIndent.setAccommodations(newIndent.getAccommodations());
    }

    private LocalTime calculateReservationTimeTo(final Indent indent) {
        final LocalTime calculatedReservationTimeTo = calculationService.calculateReservationTimeTo(indent).toLocalTime();
        LOGGER.info("Successfully calculated reservation time to is: ´{}´.", calculatedReservationTimeTo);

        return calculatedReservationTimeTo;
    }

    @Override
    public void delete(final User user, final Indent indent) {
        if (indent.getStatus().equals(RESERVED)) {
            setupVariablesDelete(user);
            LOGGER.info("Successfully setup variables for deleting an Indent for User with id: ´{}´.", user.getId());

            indentRepository.delete(indent);
            LOGGER.info("Deleting Indent with id: ´{}´", indent.getId());
        } else {
            LOGGER.error("Cannot perform delete action for inactive Indent with id: ´{}´.", indent.getId());
            throw new IndentStatusNotReservedException("Indent", "id", String.valueOf(indent.getId()));
        }
    }

    private void setupVariablesDelete(final User user) {
        LOGGER.info("Setting up parameters for User with username: ´{}´.", user.getUsername());
        user.setHasReservedIndent(false);
        user.setNumberOfReservations(user.getNumberOfReservations() - 1);
    }
}
