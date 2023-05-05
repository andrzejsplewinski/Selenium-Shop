import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class QuestionnaireTests {
    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.get("http://www.selenium-shop.pl/o-nas");

    }

    @AfterEach
    void teardown() {
        // driver.quit();
    }

    @Test
    public void NameTest() {
        driver.findElement(By.id("Imiê")).sendKeys("Jan");
        driver.findElement(By.id("Nazwisk")).sendKeys("Kowalski");

        WebElement radioSexF = driver.findElement(By.cssSelector("input[type='radio'][value='Kobieta']"));
        if (!radioSexF.isSelected()) {
            radioSexF.click();
        }
        WebElement radioSexM = driver.findElement(By.cssSelector("input[type='radio'][value='Mê¿czyzna']"));
        if (!radioSexM.isSelected()) {
            radioSexM.click();
        }
        WebElement age = driver.findElement(By.cssSelector("input[id='Wiek'][value='30-39']"));
        age.click();

        WebElement product = driver.findElement(By.cssSelector("input[name='Muzyka'][value='Inna']"));
        product.click();
        WebElement productText = driver.findElement(By.cssSelector("input[name='Produkt'][type='text']"));
        productText.sendKeys("Rakieta tenisowa");

        WebElement sport = driver.findElement(By.className("moj-select")).findElement(By.cssSelector("option[value='koszykowka']"));
        sport.click();


    }

}
