package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebInterfaceValidateTest {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("-disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void Test_1() { ///ввод допустимого имени

        driver.get("http://localhost:9999");

        driver.findElement(By.className("input__control")).sendKeys("Владимир");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Укажите точно как в паспорте", text.trim());

    }

    @Test
    public void Test_2() { ///ввод НЕ допустимого имени

        driver.get("http://localhost:9999");

        driver.findElement(By.className("input__control")).sendKeys("Vladimir");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test
    public void Test_3() { ///поле имени пустое

        driver.get("http://localhost:9999");

        driver.findElement(By.name("phone")).sendKeys("+79030000001");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    public void Test_4() { ///ввод  НЕ допустимого № телефона

        driver.get("http://localhost:9999");

        driver.findElement(By.className("input__control")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("-7903");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    public void Test_5() { ///поле № телефона пустое

        driver.get("http://localhost:9999");

        driver.findElement(By.className("input__control")).sendKeys("Владимир");
        //driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79011114038");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    public void Test_6() { ///тест на нажатие check-box

        driver.get("http://localhost:9999");

        driver.findElement(By.className("input__control")).sendKeys("Владимир");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79011114038");
        //driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        //Check-box не нажат (галочка отсутствует)//
        //element - наш объект (check-box), обращаемся по атрибуту data-test-id= agreement
        WebElement element = driver.findElement(By.cssSelector("[data-test-id= agreement] input"));
        // проверяем у нашего element наличие класса. Если класс "checkbox_checked" есть - галка поставлена
        //если класса "checkbox_checked" нет - галка НЕ поставлена
        //getAttribute - проверка отсутствия проверяемого класса
        String classValue = element.getAttribute("checkbox_checked");
        assertNull(null, classValue);

    }
}
