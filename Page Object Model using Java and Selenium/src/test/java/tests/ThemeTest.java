// ThemeTest.java
package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ThemePage;
import pages.ThemePage.Theme;
import utilities.WebDriverManager;
import java.time.Duration;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ThemeTest {
    private static final Logger LOGGER = Logger.getLogger(ThemeTest.class.getName());
    private static WebDriver driver;
    private static ThemePage themePage;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        driver = WebDriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://developer.mozilla.org/en-US/");
        themePage = new ThemePage(driver);
        LOGGER.info("Test setup completed");
    }

    @BeforeEach
    public void beforeEach() {
        driver.navigate().refresh();
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
        LOGGER.info("Page refreshed and ready for test");
    }

    @Test
    @Order(1)
    @DisplayName("Test Light Theme Switch")
    public void testLightThemeSwitch() {
        LOGGER.info("Starting Light Theme Switch Test");
        Theme initialTheme = themePage.getCurrentTheme();
        LOGGER.info("Initial theme: " + initialTheme);

        themePage.switchToTheme(Theme.LIGHT);
        Theme resultTheme = themePage.getCurrentTheme();

        assertEquals(Theme.LIGHT, resultTheme,
                "Theme should be LIGHT after switch");
        LOGGER.info("Light theme switch test completed successfully");
    }

    @Test
    @Order(2)
    @DisplayName("Test Dark Theme Switch")
    public void testDarkThemeSwitch() {
        LOGGER.info("Starting Dark Theme Switch Test");
        Theme initialTheme = themePage.getCurrentTheme();
        LOGGER.info("Initial theme: " + initialTheme);

        themePage.switchToTheme(Theme.DARK);
        Theme resultTheme = themePage.getCurrentTheme();

        assertEquals(Theme.DARK, resultTheme,
                "Theme should be DARK after switch");
        LOGGER.info("Dark theme switch test completed successfully");
    }

    @Test
    @Order(3)
    @DisplayName("Test Complete Theme Cycle")
    public void testThemeCycle() {
        LOGGER.info("Starting Theme Cycle Test");
        Theme initialTheme = themePage.getCurrentTheme();
        LOGGER.info("Initial theme: " + initialTheme);

        themePage.cycleThemes();

        Theme finalTheme = themePage.getCurrentTheme();
        LOGGER.info("Final theme after cycle: " + finalTheme);

        assertEquals(Theme.DARK, finalTheme,
                "Theme should end in dark mode after cycle");
    }

    @AfterEach
    public void afterEach() {
        String currentTheme = themePage.getHtmlClassAttribute();
        LOGGER.info("Test completed. Final theme class: " + currentTheme);
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            WebDriverManager.quitDriver();
            LOGGER.info("WebDriver quit successfully");
        }
    }
}