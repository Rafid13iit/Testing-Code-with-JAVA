package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MozillaDocsPage;
import utilities.WebDriverManager;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MozillaThemeTest {
    private static WebDriver driver;
    private static MozillaDocsPage mozillaDocsPage;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        driver = WebDriverManager.getDriver();
        // Set implicit wait to 0 to avoid conflicts with explicit waits
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the page
        driver.get("https://developer.mozilla.org/en-US/");
        mozillaDocsPage = new MozillaDocsPage(driver);
    }

    @BeforeEach
    public void beforeEach() {
        // Refresh the page before each test to ensure a clean state
        driver.navigate().refresh();
        // Wait for page to be fully loaded
        wait.until(driver -> ((String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.readyState")).equals("complete"));
    }

    @Test
    @Order(1)
    @DisplayName("Verify page title")
    public void testPageTitle() {
        assertEquals("MDN Web Docs", mozillaDocsPage.getPageTitle(),
                "Page title should be 'MDN Web Docs'");
    }

    @Test
    @Order(2)
    @DisplayName("Test theme switching functionality")
    public void testThemeSwitching() {
        try {
            // Log initial theme state
            boolean initialDarkTheme = mozillaDocsPage.isDarkTheme();
            System.out.println("Initial theme is dark: " + initialDarkTheme);
            System.out.println("Initial theme class: " + mozillaDocsPage.getCurrentThemeClass());

            // Switch theme
            mozillaDocsPage.switchTheme();

            // Add small delay to ensure theme change is complete
            Thread.sleep(500);

            // Log final theme state
            String updatedThemeClass = mozillaDocsPage.getCurrentThemeClass();
            System.out.println("Updated theme class: " + updatedThemeClass);

            // Verify theme switch
            boolean finalDarkTheme = mozillaDocsPage.isDarkTheme();
            if (initialDarkTheme) {
                assertFalse(finalDarkTheme,
                        "Theme should have switched to light mode");
            } else {
                assertTrue(finalDarkTheme,
                        "Theme should have switched to dark mode");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test was interrupted during theme switch verification");
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test complete theme cycle")
    public void testThemeCycle() {
        try {
            // Record initial state
            boolean initialDarkTheme = mozillaDocsPage.isDarkTheme();
            System.out.println("Starting theme cycle test - Initial dark theme: " + initialDarkTheme);

            // First switch
            mozillaDocsPage.switchTheme();
            Thread.sleep(500); // Wait for theme change
            boolean intermediateState = mozillaDocsPage.isDarkTheme();
            assertEquals(!initialDarkTheme, intermediateState,
                    "Theme should have switched to opposite state");
            System.out.println("After first switch - Dark theme: " + intermediateState);

            // Switch back
            mozillaDocsPage.switchTheme();
            Thread.sleep(500); // Wait for theme change
            boolean finalState = mozillaDocsPage.isDarkTheme();
            assertEquals(initialDarkTheme, finalState,
                    "Theme should have returned to initial state");
            System.out.println("After second switch - Dark theme: " + finalState);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test was interrupted during theme cycle");
        }
    }

    @AfterEach
    public void afterEach() {
        // Log any console errors that occurred during the test
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("console.log('Test completed: ' + document.documentElement.className)");
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            WebDriverManager.quitDriver();
        }
    }
}