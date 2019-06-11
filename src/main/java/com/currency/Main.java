package com.currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

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
    }

}
