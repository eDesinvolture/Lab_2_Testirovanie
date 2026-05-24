package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lab2Test {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "https://ru.wikipedia.org/";

    @BeforeAll
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("1. Проверка заголовка главной страницы")
    void testPageTitle() {
        driver.get(baseUrl);
        assertTrue(driver.getTitle().contains("Википедия"));
    }

    @Test
    @Order(2)
    @DisplayName("2. Проверка видимости логотипа")
    void testLogoVisibility() {
        driver.get(baseUrl);
        WebElement logo = driver.findElement(By.className("mw-logo"));
        assertTrue(logo.isDisplayed());
    }

    @Test
    @Order(3)
    @DisplayName("3. Проверка видимости поля поиска")
    void testSearchInputVisibility() {
        driver.get(baseUrl);
        WebElement searchInput = driver.findElement(By.name("search"));
        assertTrue(searchInput.isDisplayed());
    }

    @Test
    @Order(4)
    @DisplayName("4. Заполнение поля поиска")
    void testFillSearch() {
        driver.get(baseUrl);
        WebElement searchInput = driver.findElement(By.name("search"));
        searchInput.clear();
        searchInput.sendKeys("Java");
        assertEquals("Java", searchInput.getDomProperty("value"));
    }

    @Test
    @Order(5)
    @DisplayName("5. Эмуляция нажатия кнопки поиска")
    void testSubmitSearch() {
        driver.get(baseUrl);
        WebElement searchInput = driver.findElement(By.name("search"));
        searchInput.clear();

        // Отправляем поисковый запрос с \n
        searchInput.sendKeys("Java\n");

        wait.until(ExpectedConditions.titleContains("Java"));
        assertTrue(driver.getCurrentUrl().contains("Java"));
    }

    @Test
    @Order(6)
    @DisplayName("6. Проверка заголовка открывшейся статьи")
    void testArticleHeading() {
        // Переходим сразу на страницу статьи для независимости теста
        driver.get("https://ru.wikipedia.org/wiki/Java");
        WebElement heading = driver.findElement(By.id("firstHeading"));
        assertTrue(heading.isDisplayed());
        assertEquals("Java", heading.getText());
    }

    @Test
    @Order(7)
    @DisplayName("7. Проверка видимости области основного контента")
    void testContentAreaVisibility() {
        driver.get(baseUrl);
        WebElement content = driver.findElement(By.id("content"));
        assertTrue(content.isDisplayed());
    }

    @Test
    @Order(8)
    @DisplayName("8. Переход по ссылке 'Войти' (форма авторизации)")
    void testLoginLinkNavigation() {
        driver.get(baseUrl);
        WebElement loginLink = driver.findElement(By.linkText("Войти"));
        loginLink.click();

        wait.until(ExpectedConditions.titleContains("Войти"));
        assertTrue(driver.getTitle().contains("Войти"));
    }

    @Test
    @Order(9)
    @DisplayName("9. Возврат на главную страницу при клике на логотип")
    void testLogoNavigationToMain() {
        driver.get("https://ru.wikipedia.org/wiki/Java");
        WebElement logo = driver.findElement(By.className("mw-logo"));
        logo.click();

        // На главной странице заголовок это Википедия — свободная энциклопедия
        wait.until(ExpectedConditions.titleContains("свободная энциклопедия"));
        assertTrue(driver.getTitle().contains("свободная энциклопедия"));
    }

    @Test
    @Order(10)
    @DisplayName("10. Переход по ссылке в футере ('Описание Википедии')")
    void testFooterLinkNavigation() {
        driver.get(baseUrl);
        WebElement aboutLink = driver.findElement(By.linkText("Описание Википедии"));
        aboutLink.click();

        wait.until(ExpectedConditions.titleContains("Описание"));
        assertTrue(driver.getTitle().contains("Описание"));
    }
}