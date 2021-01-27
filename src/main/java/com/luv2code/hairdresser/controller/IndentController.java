package com.luv2code.hairdresser.controller;

import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.service.IndentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/indents")
public class IndentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndentController.class);

    private final IndentService indentService;

    @Autowired
    public IndentController(final IndentService indentService) {
        this.indentService = indentService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final Indent searchedIndent = indentService.findById(id);
        LOGGER.info("Successfully founded Indent with id: ´{}´.", id);
        return new ResponseEntity<>(searchedIndent, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        final List<Indent> indents = indentService.findAll();
        LOGGER.info("Successfully founded all Indents.");
        return new ResponseEntity<>(indents, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<?>> findAllForChosenDate(@RequestParam final String chosenDate) {
        final List<Indent> indents = indentService.findAllForChosenDate(parseDate(chosenDate));
        LOGGER.info("Successfully founded all Indents for chosen date: ´{}´.", chosenDate);
        return new ResponseEntity<>(indents, HttpStatus.OK);
    }

    private Date parseDate(final String chosenDateString) {
        return Date.valueOf(chosenDateString);
    }
}
