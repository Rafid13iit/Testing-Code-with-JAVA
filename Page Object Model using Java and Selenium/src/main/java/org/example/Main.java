package org.example;

import org.openqa.selenium.WebDriver;
import utilities.WebDriverManager;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = WebDriverManager.getDriver();
        driver.get("https://developer.mozilla.org/en-US/");
        WebDriverManager.quitDriver();
    }
}
