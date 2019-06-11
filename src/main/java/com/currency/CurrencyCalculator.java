package com.currency;

import java.util.ArrayList;

public class CurrencyCalculator {

    public void CurrencyCalculator() {
        
    }


    public int howManyDaysItGrew(ArrayList values) {
        int days = 0;
        Float firstValue = (Float) values.get(0);
        for (int i = 1; i < values.size(); i++) {
            if (firstValue < (Float) values.get(i)) {
                days++;
            }
            firstValue = (Float) values.get(i);
        }

        return days;
    }

    public int calculateDroppingDays(ArrayList valuesList) {
        int counter = 0;
        Float prevVal = new Float(0.0);
        for (Object value: valuesList) {
            if(prevVal > (Float) value)
                counter++;

            prevVal = (Float) value;
        }

        return counter;
    }

    public int howManyDaysItWasConstant(ArrayList values) {
        int days = 0;
        int inSequence = 0;
        Float firstValue = (Float) values.get(0);
        for (int i = 1; i < values.size(); i++) {
            if (firstValue == (Float) values.get(i)) {
                if (inSequence == 0) {
                    inSequence++;
                    days += 2;
                } else {
                    days += 1;
                }
            } else {
                inSequence = 0;
            }
            firstValue = (Float) values.get(i);
        }

        return days;
    }
}
