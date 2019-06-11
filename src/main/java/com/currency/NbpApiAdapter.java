package com.currency;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    String requestDataFromServer(String targetURL) {
        HttpURLConnection connection = null;
        try {

            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                bufferedReader.close();
                return response.toString();

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
