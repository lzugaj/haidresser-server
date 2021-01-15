package com.luv2code.hairdresser.service;

import java.sql.Date;
import java.sql.Time;

public interface ParseService {

    String parseToString(final Date date);

    Time parseToTime(final String timeString);

    Date parseToDate(final String localDateString);

}
