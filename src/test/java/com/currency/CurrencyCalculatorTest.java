package com.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyCalculatorTest {

    private ArrayList arrayList;
    private CurrencyCalculator currencyCalculator;
    private XmlParser xmlClass;

    @Mock
    private NbpApiAdapter nbpApiAdapter;

    @Before
    public void initialize() throws IOException {
        currencyCalculator = new CurrencyCalculator();
        xmlClass = new XmlParser();
        when(nbpApiAdapter.requestDataFromServer("week")).thenReturn(getXmlFile("week.xml"));
        when(nbpApiAdapter.requestDataFromServer("two_weeks")).thenReturn(getXmlFile("two_weeks.xml"));
        when(nbpApiAdapter.requestDataFromServer("month")).thenReturn(getXmlFile("month.xml"));
        when(nbpApiAdapter.requestDataFromServer("quarter")).thenReturn(getXmlFile("quarter.xml"));
        when(nbpApiAdapter.requestDataFromServer("half_year")).thenReturn(getXmlFile("half_year.xml"));
        when(nbpApiAdapter.requestDataFromServer("year")).thenReturn(getXmlFile("year.xml"));
    }
    @Test public void calculateDroppingDaysWeek() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("week"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.calculateDroppingDays(arrayList), 4);
    }

    @Test public void calculateDroppingDaysTwoWeeks() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("two_weeks"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.calculateDroppingDays(arrayList), 7);
    }

    @Test public void calculateDroppingDaysMonth() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("month"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.calculateDroppingDays(arrayList), 14);
    }

    @Test public void calculateDroppingDaysQuarter() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("quarter"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.calculateDroppingDays(arrayList), 34);
    }

    @Test public void calculateDroppingDaysHalfYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("half_year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.calculateDroppingDays(arrayList), 59);
    }

    @Test
    public void calculateDroppingDaysYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.calculateDroppingDays(arrayList), 129);
    }

    private String getXmlFile(String filename) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        String sp = File.separator;

        String path = "./"+sp+"src"+sp+"test"+sp+"java"+sp+"com"+sp+"currency"+sp+"assets"+sp+filename;
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        while((line=br.readLine())!= null)
            sb.append(line.trim());

        return sb.toString();
    }
}
