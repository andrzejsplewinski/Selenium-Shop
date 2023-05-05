import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SelectorTest {
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
//        driver.get("https://testeroprogramowania.github.io/selenium/basics.html");

    }

    @Test
    public void findElements() {
        //clickOnMe
//        By buttonId = By.id("clickOnMe");
//        WebElement clickOnMeButton = driver.findElement(buttonId);
//
//        //fname
//        driver.findElement(By.name("fname"));
//
//        //topSecret
//        driver.findElement(By.className("topSecret"));
//
//        driver.findElement(By.tagName("input")).sendKeys("tra la la");
//
//        List<WebElement> inputs = driver.findElements(By.tagName("input"));
//        System.out.println(inputs.size());


        driver.get("https://www.google.com");
        driver.findElement(By.id("L2AGLb")).click();
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium");
        searchBox.submit();

        String clickLink = Keys.chord(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/div/div/div/div[1]/a")).sendKeys(clickLink);

        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        String newTabHandle = windowHandles.get(1);
        driver.switchTo().window(newTabHandle);

        String expectedTitle = "Selenium";
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, actualTitle);
    }
}
