package com.luv2code.hairdresser.scheduler;

import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.domain.User;
import com.luv2code.hairdresser.service.IndentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IndentScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndentScheduler.class);

    private final IndentService indentService;

    @Autowired
    public IndentScheduler(final IndentService indentService) {
        this.indentService = indentService;
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void changeUserIndentFlagWhenIndentFinished() {
        final List<Indent> indents = indentService.findAll();
        LOGGER.info("Successfully founded all Indents.");

        for (Indent indent : indents) {
            if (indent.getReservationDate().isBefore(LocalDate.now())) {
                final User searchedUser = indent.getUser();
                searchedUser.setHasReservedIndent(false);
                LOGGER.info("Successfully set flag hasReservedIndent variable to false for User with id: ´{}´.", searchedUser.getId());


            }
        }
    }
}
