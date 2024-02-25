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
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
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
    void votosCeroTest() throws InterruptedException {
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        WebElement resetButton = driver.findElement(By.name("B2"));
        resetButton.click();
        Thread.sleep(3000);

        driver.navigate().to("http://localhost:8080/Baloncesto/");
        WebElement verVotosButton = driver.findElement(By.name("B3"));
        verVotosButton.click();
        Thread.sleep(3000);

        List<WebElement> filas = driver.findElements(By.tagName("tr"));
        boolean entro = false;
        for (WebElement fila : filas) {
            List<WebElement> columnas = fila.findElements(By.tagName("td"));
            if (!columnas.isEmpty()) {
                String nombre = columnas.get(0).getText();
                String votos = columnas.get(1).getText();
                entro = true;
                assertEquals("0", votos, "El jugador " + nombre + " tiene " + votos + " votos");
            }
        }
        assertTrue(entro, "No se cargaron los datos de los votos");

    }

    @Test
    void votarNuevoJugadorTest() throws InterruptedException {

        driver.navigate().to("http://localhost:8080/Baloncesto/");

        WebElement nombreInput = driver.findElement(By.name("txtOtros"));
        nombreInput.sendKeys("Pepe");
        WebElement otroRadio = driver.findElement(By.id("Otros"));
        otroRadio.click();
        WebElement votarButton = driver.findElement(By.name("B1"));
        votarButton.click();
        Thread.sleep(2000);

        driver.navigate().to("http://localhost:8080/Baloncesto/");
        WebElement verVotosButton = driver.findElement(By.name("B3"));
        verVotosButton.click();
        Thread.sleep(2000);

        boolean visto = false;
        List<WebElement> filas = driver.findElements(By.tagName("tr"));
        for (WebElement fila : filas) {
            List<WebElement> columnas = fila.findElements(By.tagName("td"));
            if (!columnas.isEmpty()) {
                String nombre = columnas.get(0).getText();
                String votos = columnas.get(1).getText();

                if (nombre.equals("Pepe") && votos.equals("1")) {
                    visto = true;
                    break;
                }
            }
        }
        assertTrue(visto, "El nuevo jugador Pepe no tiene 1 voto");
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}