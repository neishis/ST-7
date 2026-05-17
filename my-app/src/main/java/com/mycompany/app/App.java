package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Выполнение Задания №1 ===");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        try {
            System.out.println("Переходим на страницу генератора паролей...");
            webDriver.get("https://www.calculator.net/password-generator.html");
            
            WebElement passwordElement = webDriver.findElement(By.cssSelector("#resultid .verybigtext b"));
            String generatedPassword = passwordElement.getText();
            System.out.println("Успешно получен сгенерированный пароль: " + generatedPassword);
            System.out.println();
            
            Task2.run(webDriver);
            
            Task3.run(webDriver);
            
        } catch (Exception e) {
            System.err.println("Произошла ошибка в основном цикле выполнения: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Завершение работы. Закрываем браузер...");
            webDriver.quit();
        }
    }
}
