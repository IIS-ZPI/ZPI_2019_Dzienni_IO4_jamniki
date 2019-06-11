package com.currency;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NbpApiAdapter {
    String buildUrl(String code) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendarStart = Calendar.getInstance();
        String currentDate = dateFormat.format(calendarStart.getTime());

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.add(Calendar.YEAR, -1);
        String prevYear = dateFormat.format(calendarEnd.getTime());

        return String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/%s?format=xml", code, prevYear, currentDate);
    }
}
