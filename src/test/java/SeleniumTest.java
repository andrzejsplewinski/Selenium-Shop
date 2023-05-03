import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
    }
    @Test
    public void SampleTest(){
        System.out.println("hello");
    }
}
