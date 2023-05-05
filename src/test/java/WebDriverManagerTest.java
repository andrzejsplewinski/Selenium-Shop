import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverManagerTest {

    WebDriver driver;
    public static String BUTTON = "L2AGLb";

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("alert('Hello')");
        driver.get("https://www.google.com");
        driver.findElement(By.id(BUTTON)).click();
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("selenium ");
        searchField.submit();
        WebElement result = driver.findElement(By.xpath("//a[contains(@href, 'selenium.dev')]"));
        Assertions.assertTrue(result.isDisplayed());
    }

    @Test
    public void secondTest() {
        driver.get("http://www.seleniumdemo.com");
        driver.findElement(By.xpath("//span[text()='Shop']")).click();
        WebElement seleniumProduct = driver.findElement(By.xpath("//h2[text()='Java Selenium WebDriver']"));
        Assertions.assertTrue(seleniumProduct.isDisplayed());
    }
}
