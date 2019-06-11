package com.currency;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static String[] CURRENCY_CODES = {"USD", "AUD", "CAD", "EUR", "HUF", "CHF", "GBP", "JPY", "CZK", "DKK", "NOK", "SEK", "XDR"};
    private static String[] PERIOD_CODES = {"week", "two weeks", "month", "quarter", "half year", "year"};

    public static boolean verifyCurrency(String code){
        if(Arrays.asList(CURRENCY_CODES).contains(code)){
            return true;
        }
        System.out.println("Wrong currency symbol");
        return false;
    };

    public static boolean verifyPeriod(String period){
        if(Arrays.asList(PERIOD_CODES).contains(period))
            return true;
        System.out.println("Wrong analize period");
        return false;
    }

    public static LinkedHashMap getPeriodHashMap(String period, XmlParser xml) {
        switch (period) {
            case "week":
                return xml.getLastWeek();
            case "two weeks":
                return xml.getLastTwoWeeks();
            case "month":
                return xml.getLastMonth();
            case "quarter":
                return xml.getLastQuarter();
            case "half year":
                return xml.getLastHalfOfYear();
            case "year":
                return xml.getLastYear();
            default:
                return new LinkedHashMap<>();
        }
    };

    public static ArrayList convertToArrayList(LinkedHashMap values) {
        ArrayList allValues = new ArrayList();
        for (Object key : values.keySet()) {
            allValues.add(values.get(key));
        }
        return allValues;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("if you want single currency analyze type: 1\nIf you want to comapare two currencies type: 2\n");

        String functionality = sc.next();
        if(functionality.equals("1")) {
            oneCurrencyCalculator();
        } else if(functionality.equals("2")) {
            doubleCurrencyCalculator();
        } else{
            System.out.println("Wront input: " + functionality);
        }
    }

    public static void oneCurrencyCalculator() {
        NbpApiAdapter nbpApiAdapter = new NbpApiAdapter();
        XmlParser xml = new XmlParser();
        CurrencyCalculator currencyCalculator = new CurrencyCalculator();

        System.out.println("\nType currency symbol: \t" + Arrays.toString(CURRENCY_CODES));

        Scanner sc = new Scanner(System.in);
        String codeValue = sc.nextLine();
        if(!verifyCurrency(codeValue.toUpperCase())) {
            return;
        }

        System.out.println("\nSelect analize period: \t" + Arrays.toString(PERIOD_CODES));
        String periodValue = sc.nextLine();
        if(!verifyPeriod(periodValue.toLowerCase())) {
            return;
        }

        String url = nbpApiAdapter.buildUrl(codeValue);
        String rawXml = nbpApiAdapter.requestDataFromServer(url);
        xml.parseData(rawXml);

        LinkedHashMap periodMap = getPeriodHashMap(periodValue.toLowerCase(), xml);
        ArrayList periodValues = convertToArrayList(periodMap);
        currencyCalculator.calculateAll(periodValues);

        System.out.println("\nCurrency analize for: "+periodValue.toLowerCase());
        System.out.println("\n\nCurrency: "+codeValue.toUpperCase());
        System.out.println(currencyCalculator.toString());

        saveToCSV(currencyCalculator, periodMap, codeValue, periodValue);
    }

    public static void doubleCurrencyCalculator(){
        NbpApiAdapter nbpApiAdapter = new NbpApiAdapter();
        XmlParser xml = new XmlParser();
        CurrencyCalculator currencyCalculator1 = new CurrencyCalculator();
        CurrencyCalculator currencyCalculator2 = new CurrencyCalculator();

        System.out.println("\nType currency symbol of the first currency: \t" + Arrays.toString(CURRENCY_CODES));
        Scanner sc = new Scanner(System.in);
        String codeValue1 = sc.nextLine();
        if(!verifyCurrency(codeValue1.toUpperCase()))
            return;

        System.out.println("\nType currency symbol of the second currency: \t" + Arrays.toString(CURRENCY_CODES));
        String codeValue2 = sc.nextLine();
        if(!verifyCurrency(codeValue2.toUpperCase()))
            return;

        System.out.println("\nSelect analize period: \t" + Arrays.toString(PERIOD_CODES));
        String periodValue = sc.nextLine();
        if(!verifyPeriod(periodValue.toLowerCase()))
            return;

        String url1 = nbpApiAdapter.buildUrl(codeValue1);
        String url2 = nbpApiAdapter.buildUrl(codeValue2);
        String rawXml1 = nbpApiAdapter.requestDataFromServer(url1);
        String rawXml2 = nbpApiAdapter.requestDataFromServer(url2);

        xml.parseData(rawXml1);
        LinkedHashMap periodMap1 = getPeriodHashMap(periodValue.toLowerCase(), xml);
        ArrayList periodValues1 = convertToArrayList(periodMap1);
        currencyCalculator1.calculateAll(periodValues1);

        xml.parseData(rawXml2);
        LinkedHashMap periodMap2 = getPeriodHashMap(periodValue.toLowerCase(), xml);
        ArrayList periodValues2 = convertToArrayList(periodMap2);
        currencyCalculator2.calculateAll(periodValues2);

        System.out.println("\nCurrency analize for: "+periodValue.toLowerCase());
        System.out.println("\n\nCurrency: "+codeValue1.toUpperCase());
        System.out.println(currencyCalculator1.toString());
        System.out.println("\n\nCurrency: "+codeValue2.toUpperCase());
        System.out.println(currencyCalculator2.toString());

        saveToCSV2(currencyCalculator1, currencyCalculator2, periodMap1, periodMap2, codeValue1, codeValue2, periodValue);
    }

    public static void saveToCSV(CurrencyCalculator currencyCalculator, LinkedHashMap periodMap, String codeValue, String periodValue) {

        Set<Map.Entry<String, Float>> set = periodMap.entrySet();
        String filename = "exportedData/dataNBP-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Timestamp(System.currentTimeMillis())) + "-" + codeValue + "-" + periodValue + ".csv";
        String str = "";
        String dominant = "";

        if (currencyCalculator.dominantValue == null) {
            dominant = "The dominant does not occur" + ";";
        } else {
            for (Object value : currencyCalculator.dominantValue) {
                dominant = dominant + String.valueOf(value).replace('.', ',') + ";";
            }
        }

        try  {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filename));
            str = str + periodValue + ";" + codeValue + ";" + "\n" + "\n"
                    + "Median" + ";" + String.valueOf(currencyCalculator.medianValue).replace('.', ',') + ";" + "\n"
                    + "Dominant" + ";" + dominant + "\n"
                    + "Standard Deviation" + ";" + String.valueOf(currencyCalculator.standardDeviationValue).replace('.', ',') + ";" + "\n"
                    + "Coefficient Of Variation" + ";" + String.valueOf(currencyCalculator.coefficientOfVariationValue).replace('.', ',') + ";" + "\n"
                    + "How Many Days It Grew" + ";" + String.valueOf(currencyCalculator.howManyDaysItGrewValue).replace('.', ',') + ";" + "\n"
                    + "How Many Days It Dropping" + ";" + String.valueOf(currencyCalculator.calculateDroppingDaysValue).replace('.', ',') + ";" + "\n"
                    + "How Many Days It Was Constant" + ";" + String.valueOf(currencyCalculator.howManyDaysItWasConstantValue).replace('.', ',') + ";" + "\n" + "\n";

            for(Map.Entry<String, Float> entry : set) {
                str = str + entry.getKey() + ";" + String.valueOf(entry.getValue()).replace('.', ',') + ";" + "\n";
            }

            dataOutputStream.write(str.getBytes("UTF-8"));

            dataOutputStream.close();
        }catch(FileNotFoundException ioe) {
            System.out.println("File not found!");
        }catch(IOException e){
            System.out.println("jest abrdzo zle!");
        }
    }

    public static void saveToCSV2(CurrencyCalculator currencyCalculator1, CurrencyCalculator currencyCalculator2, LinkedHashMap periodMap1, LinkedHashMap periodMap2, String codeValue1, String codeValue2, String periodValue) {

        Set<Map.Entry<String, Float>> set1 = periodMap1.entrySet();
        Set<Map.Entry<String, Float>> set2 = periodMap2.entrySet();
        String filename = "exportedData/dataNBP-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Timestamp(System.currentTimeMillis())) + "-" + codeValue1 + "-" + periodValue + ".csv";
        String str = "";
        String dominant1 = "";
        String dominant2 = "";

        if (currencyCalculator1.dominantValue == null) {
            dominant1 = "The dominant does not occur" + ";";
        } else {
            for (Object value : currencyCalculator1.dominantValue) {
                dominant1 = dominant1 + String.valueOf(value).replace('.', ',') + ";";
            }
        }

        if (currencyCalculator2.dominantValue == null) {
            dominant2 = "The dominant does not occur" + ";";
        } else {
            for (Object value : currencyCalculator2.dominantValue) {
                dominant2 = dominant2 + String.valueOf(value).replace('.', ',') + ";";
            }
        }

        try {
            System.out.println("To tu");
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filename));
            System.out.println("To tam");
            str = str + periodValue + ";" + codeValue1 + ";" + codeValue2  + ";" + "\n" + "\n"
                    + "Median" + ";" + String.valueOf(currencyCalculator1.medianValue).replace('.', ',') + ";" + String.valueOf(currencyCalculator2.medianValue).replace('.', ',') + ";" + "\n"
                    + "Dominant" + ";" + dominant1 + dominant2 + "\n"
                    + "Standard Deviation" + ";" + String.valueOf(currencyCalculator1.standardDeviationValue).replace('.', ',') + ";" + String.valueOf(currencyCalculator2.standardDeviationValue).replace('.', ',') + ";" + "\n"
                    + "Coefficient Of Variation" + ";" + String.valueOf(currencyCalculator1.coefficientOfVariationValue).replace('.', ',') + ";" + String.valueOf(currencyCalculator2.coefficientOfVariationValue).replace('.', ',') + ";" + "\n"
                    + "How Many Days It Grew" + ";" + String.valueOf(currencyCalculator1.howManyDaysItGrewValue).replace('.', ',') + ";" + String.valueOf(currencyCalculator2.howManyDaysItGrewValue).replace('.', ',') + ";" + "\n"
                    + "How Many Days It Dropping" + ";" + String.valueOf(currencyCalculator1.calculateDroppingDaysValue).replace('.', ',') + ";" + String.valueOf(currencyCalculator2.calculateDroppingDaysValue).replace('.', ',') + ";" + "\n"
                    + "How Many Days It Was Constant" + ";" + String.valueOf(currencyCalculator1.howManyDaysItWasConstantValue).replace('.', ',') + ";" + String.valueOf(currencyCalculator2.howManyDaysItWasConstantValue).replace('.', ',') + ";" + "\n" + "\n";

            str = str + codeValue1 + ";" + "\n";
            for(Map.Entry<String, Float> entry : set1) {
                str = str + entry.getKey() + ";" + String.valueOf(entry.getValue()).replace('.', ',') + ";" + "\n";
            }

            str = str + "\n" + codeValue2 + ";" + "\n";
            for(Map.Entry<String, Float> entry : set2) {
                str = str + entry.getKey() + ";" + String.valueOf(entry.getValue()).replace('.', ',') + ";" + "\n";
            }

            dataOutputStream.write(str.getBytes("UTF-8"));

            dataOutputStream.close();
        }catch(FileNotFoundException ioe) {
            System.out.println("File not found!");
        }catch(IOException e){
            System.out.println("jest bardzo zle!");
        }
    }

}
