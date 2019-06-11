package com.currency;

import java.time.LocalDate;
import java.util.LinkedHashMap;

public class XmlParser {
    private LinkedHashMap<String, Float> parsedValues;

    public XmlParser() {

    }

    public void parseData(String xml) {
        parsedValues = new LinkedHashMap<>();

        if(xml == null) {
            return;
        }

        String[] rates = xml.split("<Mid>");
        String[] dates = xml.split("<EffectiveDate>");

        for (int i = 1; i < rates.length; i++) {

            parsedValues.put(
                    dates[i].substring(0, 10),
                    Float.valueOf(rates[i].substring(0, 6))
            );
        }
    }

    public LinkedHashMap<String, Float> getLastWeek() {

        return getPeriodMap(1, 0, 0);
    }

    public LinkedHashMap<String, Float> getLastTwoWeeks() {

        return getPeriodMap(2, 0, 0);
    }

    public LinkedHashMap<String, Float> getLastMonth() {

        return getPeriodMap(0, 1, 0);
    }

    public LinkedHashMap<String, Float> getLastQuarter() {

        return getPeriodMap(0, 3, 0);
    }

    public LinkedHashMap<String, Float> getLastHalfOfYear() {

        return getPeriodMap(0, 6, 0);
    }

    public LinkedHashMap<String, Float> getLastYear() {

        return getPeriodMap(0, 0, 1);
    }

    public LinkedHashMap<String, Float> getAllParsed() {
        return parsedValues;
    }

    private LinkedHashMap getPeriodMap(int weeks, int months, int years) {
        LinkedHashMap<String, Float> periodMap = new LinkedHashMap<>();

        LocalDate cutDate = LocalDate.now().minusYears(years).minusMonths(months).minusWeeks(weeks);

        parsedValues.forEach((k, v) -> {
            LocalDate date = LocalDate.parse(k);
            if (date.compareTo(cutDate) >= 0) {
                periodMap.put(k, v);
            }
        });

        return periodMap;
    }
}
