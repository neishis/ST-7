package com.mycompany.app;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task3 {
    public static void run(WebDriver webDriver) {
        System.out.println("=== Выполнение Задания №3 ===");
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
            System.out.println("Запрос погоды по адресу: " + url);
            webDriver.get(url);
            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();
            
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject hourly = (JSONObject) obj.get("hourly");
            JSONArray times = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains = (JSONArray) hourly.get("rain");
            
            StringBuilder sb = new StringBuilder();
            sb.append("|№   |  Дата/время   | Температура | Осадки (мм)  |\n");
            sb.append("| -- | ------------- | ----------- | ------------ |\n");
            
            for (int i = 0; i < times.size(); i++) {
                String time = (String) times.get(i);
                Number tempNum = (Number) temperatures.get(i);
                Number rainNum = (Number) rains.get(i);
                
                double temp = tempNum.doubleValue();
                double rain = rainNum.doubleValue();
                
                sb.append(String.format("|%-4d|%-15s|%-13.1f|%-14.2f|\n", (i + 1), time, temp, rain));
            }
            
            String table = sb.toString();
            System.out.println("Сформированная таблица:");
            System.out.print(table);
            
            writeTableToFile(table, "result/forecast.txt");
            writeTableToFile(table, "../result/forecast.txt");
            
            System.out.println();
        } catch (Exception e) {
            System.err.println("Ошибка в Задании №3: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void writeTableToFile(String content, String filePath) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.print(content);
            }
            System.out.println("Таблица успешно сохранена в файл: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Не удалось сохранить файл " + filePath + ": " + e.getMessage());
        }
    }
}
