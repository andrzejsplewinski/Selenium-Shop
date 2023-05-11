import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class QuestionnaireTests {
    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "start-maximized");
        driver = new ChromeDriver(options);
        driver.get("http://www.selenium-shop.pl/o-nas");

    }

    @AfterEach
    void teardown() {
         driver.quit();
    }

    @Test
    public void FillingTheQuestionnaireWithCorrectData() throws InterruptedException {

        driver.findElement(By.id("Imię")).sendKeys("Jan");
        driver.findElement(By.id("Nazwisk")).sendKeys("Kowalski");
        driver.findElement(By.cssSelector("input[type='radio'][value='Kobieta']")).click();
        driver.findElement(By.cssSelector("input[id='Wiek'][value='30-39']")).click();
        driver.findElement(By.cssSelector("input[name='Muzyka'][value='Inna']")).click();
        driver.findElement(By.cssSelector("input[name='Produkt'][type='text']")).sendKeys("Rakieta tenisowa");
        driver.findElement(By.className("moj-select")).findElement(By.cssSelector("option[value='koszykowka']")).click();
        driver.findElement(By.cssSelector("input[class='form-control white'][type='text']")).click();
        driver.findElement(By.className("datepicker-switch")).click();
        driver.findElement(By.xpath("//span[@class='month' and text()='Jun']")).click();
        driver.findElements(By.xpath("//td[@class='day' and text()='20']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        WebElement multiSelect = driver.findElement(By.cssSelector("option[value='nike']"));
        if (multiSelect.isSelected()) {
            multiSelect.click();
        }
        driver.findElement(By.cssSelector("option[value='kappa']")).click();
        driver.findElement(By.cssSelector("option[value='puma']")).click();
        driver.findElement(By.id("Komentarz")).sendKeys("To jest komentarz do ankiety");
        driver.findElement(By.id("file")).sendKeys("C:\\sample.txt");
        driver.findElement(By.id("Wyslij")).click();

        WebElement info = driver.findElement(By.id("info"));
        Assertions.assertTrue(info.isDisplayed());
        Assertions.assertEquals(
                "Wysłane dane :\n" +
                        "\n" +
                        "Imię : Jan\n" +
                        "Nazwisko : Kowalski\n" +
                        "Płeć : Kobieta\n" +
                        "Wiek : 30-39\n" +
                        "Produkty jakie szukasz:\n" +
                        "Rakieta tenisowa\n" +
                        "Sport : koszykowka\n" +
                        "Marki :\n" +
                        "kappa\n" +
                        "puma\n" +
                        "\n" +
                        "Komenatrz:\n" +
                        "To jest komentarz do ankiety",
                info.getText());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='moj-przycisk przycisk-zielony']")));
        driver.findElement(By.cssSelector("button[class='moj-przycisk przycisk-zielony']")).click();
    }

    @Test
    public void Alert() {

        WebElement alertButton = driver.findElement(By.id("alertPrzycisk"));
        alertButton.click();
        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("To jest okno „Allert” strony www.selenium-shop.pl", alert.getText());
        alert.accept();
    }

    @Test
    public void PromptAlert() throws InterruptedException {
        WebElement alertButton = driver.findElement(By.id("promtAlertPrzycisk"));
        alertButton.click();
        Alert promptAlert = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.alertIsPresent());
        promptAlert.sendKeys("Madrid");
        Assertions.assertEquals("Podaj nazwę miasta w któym mieszkasz", promptAlert.getText());
        promptAlert.accept();
    }

    @Test
    public void ConfirmAlert() throws InterruptedException {
        WebElement confirmButton = driver.findElement(By.id("confimationAlertPrzycisk"));
        confirmButton.click();
        Alert confirmAlert = driver.switchTo().alert();
        Assertions.assertEquals("Czy chcesz zatwierdzić operację usunięcia Twoich danych osobowych?", confirmAlert.getText());
        confirmAlert.accept();
        confirmButton.click();
        Assertions.assertEquals("Czy chcesz zatwierdzić operację usunięcia Twoich danych osobowych?", confirmAlert.getText());
        confirmAlert.dismiss();
    }

    @Test
    public void RightClickAlert() {
        Actions action = new Actions(driver);
        action.contextClick(driver.findElement(By.id("rightClick"))).perform();
        Assertions.assertTrue(driver.findElement(By.id("rightClickInfo")).isDisplayed());
    }

    @Test
    public void ProcessAlert() {
        Actions action = new Actions(driver);
        action.click(driver.findElement(By.id("proces"))).perform();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("procesText")));
        Assertions.assertTrue(driver.findElement(By.id("procesText")).isDisplayed());
    }

    @Test
    public void DoubleClickAlert() {
        Actions action = new Actions(driver);
        action.doubleClick(driver.findElement(By.cssSelector("input[value='Dwuklik pokaż komunikat']"))).perform();
        Assertions.assertTrue(driver.findElement(By.id("p-doubleClick")).isDisplayed());
    }

    @Test
    public void OpenNewWindow() {
        String currentWindow = driver.getWindowHandle();
        driver.findElement(By.cssSelector("input[value='Otwórz nowe okno']")).click();
        Set<String> windowNames = driver.getWindowHandles();
        for (String window : windowNames) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
            }
        }
        Assertions.assertEquals("Warto wykonywać testy automatyczne – Selenium Shop Automatyzacja Testów", driver.getTitle());
        driver.findElement(By.id("imie_nazwisko")).sendKeys("Andrzej S");
        driver.findElement(By.cssSelector("input[value='Zamknij okno przeglądarki']")).click();
        driver.switchTo().window(currentWindow);
        Assertions.assertEquals("Ankieta – Selenium Shop Automatyzacja Testów", driver.getTitle());
    }
}
