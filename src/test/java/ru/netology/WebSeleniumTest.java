package ru.netology;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebSeleniumTest {
    private WebDriver driver;

    @BeforeAll
    static void setDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void setDown() {
        driver.quit();
        driver = null;
    }

    //все валидные значения
    @Test
    void shouldAcceptValidFields()  {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Алешин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79216780021");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    // невалидные значения

    @Test
    void shouldReturnFailAfterEnglishName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Aleksey Aleshin");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFailAfterSymbolName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("***");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFailAfterEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFailAfterLongPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Алешин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+792133322211");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFailAfterShortPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Алешин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7921333221");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFailAfterIncorrectPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Алешин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("!@#$%^&*()_");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFailAfterEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Алешин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFailAfterEmptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Алешин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText().trim();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expected, actual);
    }
}