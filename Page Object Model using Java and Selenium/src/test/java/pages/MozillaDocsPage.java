package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MozillaDocsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(tagName = "html")
    private WebElement htmlTag;

    @FindBy(css = "button.theme-switcher-menu")
    private WebElement themeButton;

    @FindBy(xpath = "//li/button[span[contains(text(), 'Light')]]")
    private WebElement lightThemeOption;

    @FindBy(xpath = "//li/button[span[contains(text(), 'Dark')]]")
    private WebElement darkThemeOption;

    public MozillaDocsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isDarkTheme() {
        try {
            // Refresh the html tag reference before checking
            return driver.findElement(By.tagName("html"))
                    .getAttribute("class")
                    .contains("dark");
        } catch (StaleElementReferenceException e) {
            // Retry once if the reference is stale
            return driver.findElement(By.tagName("html"))
                    .getAttribute("class")
                    .contains("dark");
        }
    }

    public void clickThemeButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.theme-switcher-menu"))).click();
        } catch (ElementClickInterceptedException e) {
            // If click is intercepted, try JavaScript click
            WebElement button = driver.findElement(
                    By.cssSelector("button.theme-switcher-menu"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }

        // Wait for menu to be visible
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//li/button[span[contains(text(), 'Light')]] | //li/button[span[contains(text(), 'Dark')]]")));
    }

    public void switchToLightTheme() {
        try {
            WebElement lightOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li/button[span[contains(text(), 'Light')]]")));
            lightOption.click();

            // Wait for theme change to complete
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.attributeContains(By.tagName("html"), "class", "dark")));
        } catch (ElementClickInterceptedException e) {
            // Fallback to JavaScript click if needed
            WebElement lightOption = driver.findElement(
                    By.xpath("//li/button[span[contains(text(), 'Light')]]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lightOption);
        }
    }

    public void switchToDarkTheme() {
        try {
            WebElement darkOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li/button[span[contains(text(), 'Dark')]]")));
            darkOption.click();

            // Wait for theme change to complete
            wait.until(ExpectedConditions.attributeContains(
                    By.tagName("html"), "class", "dark"));
        } catch (ElementClickInterceptedException e) {
            // Fallback to JavaScript click if needed
            WebElement darkOption = driver.findElement(
                    By.xpath("//li/button[span[contains(text(), 'Dark')]]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", darkOption);
        }
    }

    public String getCurrentThemeClass() {
        return driver.findElement(By.tagName("html")).getAttribute("class");
    }

    public void switchTheme() {
        try {
            clickThemeButton();
            // Small delay to ensure menu is fully rendered
            Thread.sleep(100);
            if (isDarkTheme()) {
                switchToLightTheme();
            } else {
                switchToDarkTheme();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Theme switch interrupted", e);
        }
    }
}