package com.luv2code.hairdresser.service.impl;

import com.luv2code.hairdresser.service.ParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Service
public class ParseServiceImpl implements ParseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseServiceImpl.class);

    @Override
    public String parseToString(final Date date) {
        LOGGER.info("Parse date to string.");
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    @Override
    public Time parseToTime(final String timeString) {
        LOGGER.info("Parse string to time.");
        return Time.valueOf(timeString.substring(11, 19));
    }

    @Override
    public Date parseToDate(final String localDateString) {
        LOGGER.info("Parse string to date.");
        return Date.valueOf(localDateString.substring(0, 10));
    }
}
