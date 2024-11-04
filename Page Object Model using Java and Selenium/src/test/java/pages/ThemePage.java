// ThemePage.java
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.logging.Logger;

public class ThemePage {
    private static final Logger LOGGER = Logger.getLogger(ThemePage.class.getName());
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    @FindBy(tagName = "html")
    private WebElement htmlTag;

    @FindBy(css = "button.theme-switcher-menu")
    private WebElement themeButton;

    public ThemePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public enum Theme {
        LIGHT,
        DARK,
        OS_DEFAULT
    }

    public Theme getCurrentTheme() {
        try {
            String themeClass = getHtmlClassAttribute();
            if (themeClass.contains("dark")) {
                return Theme.DARK;
            } else if (themeClass.contains("light")) {
                return Theme.LIGHT;
            } else {
                return Theme.OS_DEFAULT;
            }
        } catch (Exception e) {
            LOGGER.warning("Error detecting current theme: " + e.getMessage());
            return Theme.OS_DEFAULT;
        }
    }

    public String getHtmlClassAttribute() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("html")))
                .getAttribute("class");
    }

    public void openThemeMenu() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(themeButton)).click();
            LOGGER.info("Theme menu opened successfully");
        } catch (ElementClickInterceptedException e) {
            LOGGER.warning("Click intercepted, trying JavaScript click");
            js.executeScript("arguments[0].click();", themeButton);
        }
        // Wait for menu to be visible
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//li/button[span[contains(text(), 'Light')] or contains(text(), 'Dark')]")));
    }

    public void switchToTheme(Theme targetTheme) {
        Theme currentTheme = getCurrentTheme();
        LOGGER.info("Current theme: " + currentTheme + ", Target theme: " + targetTheme);

        if (currentTheme == targetTheme) {
            LOGGER.info("Already in target theme state");
            return;
        }

        try {
            openThemeMenu();
            String themeText = targetTheme.toString().charAt(0) +
                    targetTheme.toString().substring(1).toLowerCase();

            WebElement themeOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li/button[span[contains(text(), '" + themeText + "')]]")));

            js.executeScript("arguments[0].scrollIntoView(true);", themeOption);
            try {
                themeOption.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", themeOption);
            }

            // Wait for theme change to complete
            if (targetTheme == Theme.DARK) {
                wait.until(ExpectedConditions.attributeContains(
                        By.tagName("html"), "class", "dark"));
            } else {
                wait.until(ExpectedConditions.not(
                        ExpectedConditions.attributeContains(By.tagName("html"), "class", "dark")));
            }

            LOGGER.info("Theme switched successfully to: " + targetTheme);
        } catch (Exception e) {
            LOGGER.severe("Error switching theme: " + e.getMessage());
            throw new RuntimeException("Failed to switch theme", e);
        }
    }

    public void cycleThemes() {
        Theme[] themes = {Theme.LIGHT, Theme.DARK};
        Theme currentTheme = getCurrentTheme();
        LOGGER.info("Starting theme cycle from: " + currentTheme);

        for (Theme theme : themes) {
            switchToTheme(theme);
            try {
                Thread.sleep(2000); // Allow time for transition
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Theme cycle interrupted", e);
            }
        }
    }
}

