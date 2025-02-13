package id.ac.ui.cs.advprog.eshop.functional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("%s:%d/products/create", testBaseUrl, serverPort);
    }

    @Test
    void verifyCreateProduct(ChromeDriver driver) {
        driver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement nameInput = driver.findElement(By.id("product-name"));
        nameInput.sendKeys("Sampo Cap Bambang");

        WebElement quantityInput = driver.findElement(By.id("product-quantity"));
        quantityInput.sendKeys("100");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebElement productElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Sampo Cap Bambang')]")));
        assertTrue(productElement.isDisplayed());

    }
}
