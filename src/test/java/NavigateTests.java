import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class NavigateTests {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless","start-maximized");
        driver = new ChromeDriver(options);
        driver.get("http://www.selenium-shop.pl");

    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void NavigateToQuestionnaire() {
        driver.navigate().to("http://www.selenium-shop.pl/o-nas");
        Assertions.assertEquals("Ankieta – Selenium Shop Automatyzacja Testów", driver.getTitle());
        driver.navigate().back();
        Assertions.assertEquals("Selenium Shop Automatyzacja Testów", driver.getTitle());
    }

    @Test
    public void NavigateToCart() {
        driver.navigate().to("http://www.selenium-shop.pl/koszyk/");
        Assertions.assertEquals("Koszyk – Selenium Shop Automatyzacja Testów", driver.getTitle());
        driver.navigate().back();
        Assertions.assertEquals("Selenium Shop Automatyzacja Testów", driver.getTitle());
    }
    @Test
    public void NavigateToMyAccount() {
        driver.navigate().to("http://www.selenium-shop.pl/moje-konto/");
        Assertions.assertEquals("Moje konto – Selenium Shop Automatyzacja Testów", driver.getTitle());
        driver.navigate().back();
        Assertions.assertEquals("Selenium Shop Automatyzacja Testów", driver.getTitle());
    }
    @Test
    public void NavigateToShop() {
        driver.navigate().to("http://www.selenium-shop.pl/sklep/");
        Assertions.assertEquals("Produkty – Selenium Shop Automatyzacja Testów", driver.getTitle());
        driver.navigate().back();
        Assertions.assertEquals("Selenium Shop Automatyzacja Testów", driver.getTitle());
    }
}
