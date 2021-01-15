package com.luv2code.hairdresser.scheduler;

import com.luv2code.hairdresser.domain.Indent;
import com.luv2code.hairdresser.service.IndentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ResetDataAfterIndentIsFinishedScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetDataAfterIndentIsFinishedScheduler.class);

    private final IndentService indentService;

    @Autowired
    public ResetDataAfterIndentIsFinishedScheduler(final IndentService indentService) {
        this.indentService = indentService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void resetIndentData() {
        final List<Indent> indents = indentService.findAll();
        for (Indent indent : indents) {
            if (indent.getIsActive()) {
                int timeDiff = indent.getReservationTimeTo().compareTo(LocalTime.now());
                int dayDiff = indent.getReservationDate().compareTo(LocalDate.now());
                if (timeDiff < 0 && dayDiff < 0) {
                    indent.setIsActive(false);
                    LOGGER.info("Reset isActive to false to Indent with id: ´{}´", indent.getId());

                    indent.getUser().setHasReservedIndent(false);
                    LOGGER.info("Reset hasReservedIndent to false to User with username: ´{}´", indent.getUser().getUsername());
                }
            }
        }
    }
}
