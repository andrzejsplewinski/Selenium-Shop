import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {


    @Test
    public void OpenGoogle() {
        WebDriver driver = getDriver("CHROME");
        driver.get("https://www.google.com");
        System.out.println("hello");
    }

    public WebDriver getDriver(String browser) {
        switch (browser) {
            case "CHROME":
                return new ChromeDriver();
            case "FIREFOX":
                return new FirefoxDriver();
            case "EDGE":
                return new EdgeDriver();
            default:
                throw new InvalidArgumentException("Invalid browser name");
        }
    }
}
