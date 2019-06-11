package com.currency;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class XmlParserTest {

    @Test
    public void testParsing() {
        String xml = "<ExchangeRatesSeries><Table>A</Table><Currency>funt szterling</Currency><Code>GBP</Code><Rates><Rate><No>101/A/NBP/2019</No><EffectiveDate>2019-05-27</EffectiveDate><Mid>4.8759</Mid></Rate><Rate><No>102/A/NBP/2019</No><EffectiveDate>2019-05-28</EffectiveDate><Mid>4.8641</Mid></Rate><Rate><No>103/A/NBP/2019</No><EffectiveDate>2019-05-29</EffectiveDate><Mid>4.8744</Mid></Rate><Rate><No>104/A/NBP/2019</No><EffectiveDate>2019-05-30</EffectiveDate><Mid>4.8672</Mid></Rate><Rate><No>105/A/NBP/2019</No><EffectiveDate>2019-05-31</EffectiveDate><Mid>4.8572</Mid></Rate><Rate><No>106/A/NBP/2019</No><EffectiveDate>2019-06-03</EffectiveDate><Mid>4.8410</Mid></Rate><Rate><No>107/A/NBP/2019</No><EffectiveDate>2019-06-04</EffectiveDate><Mid>4.8144</Mid></Rate><Rate><No>108/A/NBP/2019</No><EffectiveDate>2019-06-05</EffectiveDate><Mid>4.8221</Mid></Rate><Rate><No>109/A/NBP/2019</No><EffectiveDate>2019-06-06</EffectiveDate><Mid>4.8274</Mid></Rate><Rate><No>110/A/NBP/2019</No><EffectiveDate>2019-06-07</EffectiveDate><Mid>4.8270</Mid></Rate></Rates></ExchangeRatesSeries>";
        XmlParser parse = new XmlParser();

        parse.parseData(xml);

        LinkedHashMap<String, Float> map = parse.getLastYear();

        float sum = 0;

        for (Map.Entry<String, Float> entry : map.entrySet()) {
            String key = entry.getKey();
            float value = entry.getValue();

            sum += value;
        }

        assertEquals(true, Math.abs(sum-48.470703) < 0.01);
    }
}
