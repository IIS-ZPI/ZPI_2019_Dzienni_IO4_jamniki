package com.currency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CurrencyCalculator {

    public Float averageValue,standardDeviationValue,coefficientOfVariationValue;
    public Float medianValue;
    public ArrayList<Float> dominantValue;
    public int howManyDaysItGrewValue, calculateDroppingDaysValue,howManyDaysItWasConstantValue;

    public void CurrencyCalculator() {
        dominantValue = new ArrayList<>();
    }

    public void calculateAll(ArrayList values)
    {
        averageValue = average(values);
        standardDeviationValue = standardDeviation(values);
        coefficientOfVariationValue = coefficientOfVariation(values);
        medianValue = median(values);
        dominantValue = dominant(values);
        howManyDaysItGrewValue = howManyDaysItGrew(values);
        calculateDroppingDaysValue = calculateDroppingDays(values);
        howManyDaysItWasConstantValue = howManyDaysItWasConstant(values);
    }

    public String toString(){
        String dominant;
        if (dominantValue == null) {
            dominant = "The dominant does not occur" + ";";
        } else {
            dominant = String.valueOf(dominantValue);
        }
        return "Standard deviation: "+standardDeviationValue+"\nCoefficient of variation: "+coefficientOfVariationValue+"\nExchange rate rising days: "+howManyDaysItGrewValue+"\nExchange rate dropping days: "+calculateDroppingDaysValue+"\nExchange rate constant days: "+howManyDaysItWasConstantValue+ "\nMedian: "+medianValue+"\nDominant: "+dominant;
    }

    public Float average(ArrayList values){

        Float sum = 0.0f;
        for (int i=0; i< values.size(); i++) {
            sum += Float.valueOf(values.get(i).toString());
        }
        Float avg = sum / values.size();
        return avg;
    }

    public Float standardDeviation(ArrayList values){

        Float avg = average(values);
        Float sum = 0.0f;
        for (int i=0; i< values.size(); i++) {
            sum += new Float(Math.pow(Float.valueOf(values.get(i).toString()) - avg, 2));
        }
        Float variance = sum / values.size();
        Float deviation = new Float(Math.sqrt(variance));
        return deviation;
    }

    public Float coefficientOfVariation(ArrayList values){

        Float deviation = standardDeviation(values);
        Float avg = average(values);
        Float coefficient = deviation / avg;
        return coefficient;
    }

    public Float median(ArrayList valuesList) {
        int quantity = valuesList.size();
        ArrayList<Float> tempArr = new ArrayList<Float>(valuesList);

        Collections.sort(tempArr);

        if (quantity % 2 != 0) {
            return tempArr.get(((quantity / 2)));
        } else {
            return (tempArr.get((quantity / 2) - 1) + (Float) tempArr.get((quantity / 2))) / 2;
        }
    }

    public ArrayList<Float> dominant(ArrayList valuesList) {

        ArrayList<Float> dominantValue = new ArrayList<Float>();
        Map<Float,Integer> duplicates = new HashMap<Float,Integer>();

        for (Object value : valuesList) {
            if (duplicates.containsKey(value)) {
                duplicates.put((Float) value, duplicates.get(value) + 1);
            } else {
                duplicates.put((Float) value, 1);
            }
        }

        int maxValueInMap = (Collections.max(duplicates.values()));

        if (maxValueInMap == 1) {
            return null;
        } else {
            for (Map.Entry<Float,Integer> entry : duplicates.entrySet()) {
                if (entry.getValue() == maxValueInMap) {
                    dominantValue.add(entry.getKey());
                }
            }

            return dominantValue;
        }
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
