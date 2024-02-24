import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PruebasPhantomjsIT {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void initConf() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        wait = new WebDriverWait(driver, 10);
    }



    @Test
    void tituloIndexTest() {
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(),
                "El titulo no es correcto");
        System.out.println(driver.getTitle());
    }

    @Test
    @Disabled
    void votosCeroTest() {
        driver.navigate().to("http://localhost:8080/Baloncesto/");

        wait.until(ExpectedConditions.elementToBeClickable(By.name("B2"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("B3"))).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr")));

        List<WebElement> filas = driver.findElements(By.tagName("tr"));
        for (WebElement fila : filas) {
            List<WebElement> columnas = fila.findElements(By.tagName("td"));
            String nombreJugador = columnas.get(0).getText();
            String votos = columnas.get(1).getText();

            assertEquals("0", votos, "El jugador " + nombreJugador + " tiene " + votos + " votos");
        }
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}