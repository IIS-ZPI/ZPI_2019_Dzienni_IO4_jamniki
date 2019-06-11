package com.currency;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyCalculatorTest {

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
