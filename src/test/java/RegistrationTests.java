import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Random;


public class RegistrationTest {
    static WebDriver driver;
    public static final String BUTTONMYACCOUNT = "menu-item-136";
    public static final String CONFIRMREGISTRATION = "//*[@class='woocommerce-Button woocommerce-button button woocommerce-form-register__submit' and @name='register']";
    public static final String ALERT = "//*[@class='woocommerce-error' and @role='alert']";
    public static final String EMAILFIELD = "reg_email";
    public String msg;

    @BeforeAll
    static void setupAll() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--window-size=1920,1200");
        driver = new ChromeDriver(options);
    }

    @BeforeEach
    void setup() {
        driver.get("http://www.selenium-shop.pl/");
        driver.findElement(By.id(BUTTONMYACCOUNT)).click();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    @Description("Próba wys³ania formularza bez wype³nienia pola e-mail")
    public void RegistrationNoData() {

        driver.findElement(By.xpath(CONFIRMREGISTRATION)).click();

        Assertions.assertEquals("B³¹d: Podaj poprawny adres e-mail.", driver.findElement(By.xpath(ALERT)).getText());
    }

    @Test
    @Description("Poprawny wpis e-mail")
    public void CorrectEmail() {
        Random random = new Random();
        int n = random.nextInt(1000);

        driver.findElement(By.id(EMAILFIELD)).sendKeys("username" + n + "@gmail.com");
        driver.findElement(By.xpath(CONFIRMREGISTRATION)).click();

        Assertions.assertTrue(driver.findElement(By.className("woocommerce-MyAccount-navigation")).isDisplayed());

        driver.findElement(By.linkText("Wyloguj siê")).click();
        Assertions.assertTrue(driver.findElement(By.xpath(CONFIRMREGISTRATION)).isDisplayed());
    }

    @Test
    @Description("Próba logowania 2x tym samym mailem")
    public void TwiceWithTheSameEmail() {
        driver.findElement(By.id(EMAILFIELD)).sendKeys("username@gmail.com");

        driver.findElement(By.xpath(CONFIRMREGISTRATION)).click();

        Assertions.assertEquals("B³¹d: Konto z tym adresem e-mail ju¿ istnieje. Zaloguj siê.", driver.findElement(By.xpath(ALERT)).getText());
    }

    @Test
    @Description("Niepoprawny email bez @")
    public void IncorrectEmail() {

        driver.findElement(By.id(EMAILFIELD)).sendKeys("username.gmail.com");
        driver.findElement(By.xpath(CONFIRMREGISTRATION)).click();

        msg = driver.findElement(By.id(EMAILFIELD)).getAttribute("validationMessage");

        Assertions.assertEquals("Uwzglêdnij znak „@” w adresie e-mail. W adresie „username.gmail.com” brakuje znaku „@”.", msg);
    }

    @Test
    @Description("Zbyt d³ugi adres mail")
    public void TooLongMail() {
        driver.findElement(By.id(EMAILFIELD)).sendKeys("ussssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssser@gmail.com");
        driver.findElement(By.xpath(CONFIRMREGISTRATION)).click();

        Assertions.assertEquals("B³¹d: Nazwa u¿ytkownika nie mo¿e byæ d³u¿sza ni¿ 60 znaków.", driver.findElement(By.xpath(ALERT)).getText());
    }

    @Test
    @Description("Mail z niedozwolonymi znakami")
    public void MailWithForbiddenSymbols() {
        driver.findElement(By.id(EMAILFIELD)).sendKeys("use!#$%^&((@mail.com");
        driver.findElement(By.xpath(CONFIRMREGISTRATION)).click();

        msg = driver.findElement(By.id(EMAILFIELD)).getAttribute("validationMessage");

        Assertions.assertEquals("Czêœæ przed znakiem „@” nie mo¿e zawieraæ symbolu „(”.", msg);
    }

    @Test
    @Description()
    public void TestSQLInjectionRisk() {
        driver.findElement(By.id(EMAILFIELD)).sendKeys("'or 1 = 1; -@gmail.com");
        driver.findElement(By.xpath(CONFIRMREGISTRATION)).click();

        msg = driver.findElement(By.id(EMAILFIELD)).getAttribute("validationMessage");

        Assertions.assertEquals("Czêœæ przed znakiem „@” nie mo¿e zawieraæ symbolu „ ”.", msg);
    }
}
