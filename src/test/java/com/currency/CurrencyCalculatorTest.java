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

    @Test public void calculateGrowingDaysWeek() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("week"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItGrew(arrayList), 1);
    }

    @Test public void calculateGrowingDaysTwoWeeks() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("two_weeks"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItGrew(arrayList), 3);
    }

    @Test public void calculateGrowingDaysMonth() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("month"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItGrew(arrayList), 11);
    }

    @Test public void calculateGrowingDaysQuarter() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("quarter"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItGrew(arrayList), 29);
    }

    @Test public void calculateGrowingDaysHalfYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("half_year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItGrew(arrayList), 45);
    }

    @Test public void calculateGrowingDaysYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItGrew(arrayList), 121);
    }
    @Test public void calculateConstantDaysWeek() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("week"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItWasConstant(arrayList), 0);
    }

    @Test public void calculateConstantDaysTwoWeeks() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("two_weeks"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItWasConstant(arrayList), 0);
    }

    @Test public void calculateConstantDaysMonth() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("month"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItWasConstant(arrayList), 0);
    }

    @Test public void calculateConstantDaysQuarter() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("quarter"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(currencyCalculator.howManyDaysItWasConstant(arrayList), 0);
    }

    @Test public void calculateConstantDaysHalfYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("half_year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(0, currencyCalculator.howManyDaysItWasConstant(arrayList));
    }

    @Test public void calculateConstantDaysYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(0, currencyCalculator.howManyDaysItWasConstant(arrayList));
    }

    @Test public void calculateAverageWeek(){
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("week"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.average(arrayList) - 4.219f) < 0.01 );
    }

    @Test public void calculateAverageTwoWeeks() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("two_weeks"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.average(arrayList) - 4.2520456f) < 0.01 );
    }

    @Test public void calculateAverageMonth() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("month"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.average(arrayList) - 4.235139f) < 0.01);
    }

    @Test public void calculateAverageQuarter() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("quarter"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.average(arrayList) - 4.2513185f) < 0.01);
    }

    @Test public void calculateAverageHalfYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("half_year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.average(arrayList) - 4.2716045f) < 0.01);
    }

    @Test public void calculateAverageYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.average(arrayList) - 4.253092f) < 0.01);
    }

    @Test public void calculateStandardDeviationWeek(){
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("week"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.standardDeviation(arrayList)-0.010018411f)<0.01);
    }

    @Test public void calculateStandardDeviationTwoWeeks() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("two_weeks"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.standardDeviation(arrayList)-0.021232888f)<0.01);
    }

    @Test public void calculateStandardDeviationMonth() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("month"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.standardDeviation(arrayList)-0.011433249f)<0.01 );
    }

    @Test public void calculateStandardDeviationQuarter() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("quarter"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.standardDeviation(arrayList)-0.033137612f)<0.01 );
    }

    @Test public void calculateStandardDeviationHalfYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("half_year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.standardDeviation(arrayList)-0.05981006f)<0.01 );
    }

    @Test public void calculateStandardDeviationYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.standardDeviation(arrayList)-0.04931992f)<0.01);
    }

    @Test public void calculateCoefficientOfVariationWeek(){
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("week"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.coefficientOfVariation(arrayList)-0.0023745939f)<0.01 );
    }

    @Test public void calculateCoefficientOfVariationTwoWeeks() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("two_weeks"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.coefficientOfVariation(arrayList)-0.00499357f)<0.01 );
    }

    @Test public void calculateCoefficientOfVariationMonth() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("month"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.coefficientOfVariation(arrayList)-0.0026996161f)<0.01 );
    }

    @Test public void calculateCoefficientOfVariationQuarter() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("quarter"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.coefficientOfVariation(arrayList)-0.007794667f)<0.01);
    }

    @Test public void calculateCoefficientOfVariationHalfYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("half_year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true, Math.abs(currencyCalculator.coefficientOfVariation(arrayList)-0.014001778f)<0.01);
    }

    @Test public void calculateCoefficientOfVariationYear() {
        xmlClass.parseData(nbpApiAdapter.requestDataFromServer("year"));
        LinkedHashMap hashMap = xmlClass.getAllParsed();
        arrayList = Main.convertToArrayList(hashMap);

        assertEquals(true,Math.abs(currencyCalculator.coefficientOfVariation(arrayList)-0.01159625f)<0.01 );
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
