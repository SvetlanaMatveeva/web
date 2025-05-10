package main;

import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class WebTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl = "http://localhost:8080";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebElement findElement(By path) {
        return wait.until(ExpectedConditions.presenceOfElementLocated
                (path));
    }

    private void click(By path) {
        wait.until(ExpectedConditions.elementToBeClickable(
                path)).click();
    }

    private void clickInElement(By path, WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(
                webElement.findElement(path))).click();
    }

    private void clickWaitUrl(By path, String url) {
        wait.until(ExpectedConditions.elementToBeClickable(
                path)).click();
        wait.until(ExpectedConditions.urlContains(url));
    }

    private void clickWaitFullUrl(By path, String url) {
        wait.until(ExpectedConditions.elementToBeClickable(
                path)).click();
        wait.until(ExpectedConditions.urlToBe(url));
    }

    private void loginAsModerator() {
        driver.get(baseUrl + "/web_login");
        findElement(By.id("username")).sendKeys("moderator1");
        findElement(By.id("password")).sendKeys("modpass1");
        click(By.xpath("//button[@type='submit']"));
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/index"));
    }

    private void loginAsUser() {
        driver.get(baseUrl + "/web_login");
        findElement(By.id("username")).sendKeys("user2");
        findElement(By.id("password")).sendKeys("secure456");
        click(By.xpath("//button[@type='submit']"));
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/index"));
    }

    @Test
    void loginPageWithValidUserTest() {
        driver.get(baseUrl + "/web_login");
        assertEquals("Вход", driver.getTitle());

        findElement(By.id("username")).sendKeys("moderator1");
        findElement(By.id("password")).sendKeys("modpass1");

        clickWaitFullUrl(By.xpath("//button[@type='submit']"), baseUrl + "/index");

        assertEquals("Веб-форум", driver.getTitle());

        WebElement header = findElement(By.tagName("h1"));
        assertEquals("Добро пожаловать на веб-форум!", header.getText());

        WebElement navbarBrand = driver.findElement(By.className("navbar-brand"));
        assertEquals("Панель навигации", navbarBrand.getText());

        click(By.linkText("Главная"));
        assertEquals("Веб-форум", driver.getTitle());

        click(By.linkText("Пользователи"));
        assertEquals("Пользователи", driver.getTitle());

        click(By.linkText("Разделы"));
        assertEquals("Разделы", driver.getTitle());

        WebElement logoutButton = driver.findElement(By.cssSelector("form button[type='submit']"));
        assertEquals("Выйти", logoutButton.getText());
    }

    @Test
    void addUserTest() {
        loginAsModerator();
        click(By.linkText("Пользователи"));
        click(By.linkText("Добавить пользователя"));

        String newLogin = "user14";
        findElement(By.id("login")).sendKeys(newLogin);
        findElement(By.id("password")).sendKeys("pass14");
        findElement(By.id("rights")).sendKeys("1");
        click(By.xpath("//button[@type='submit' and normalize-space(text())='Сохранить']"));

        wait.until(ExpectedConditions.urlToBe(baseUrl + "/users"));
        assertEquals(baseUrl + "/users", driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains(newLogin));
    }

    @Test
    void viewUserDetailsTest() {
        loginAsModerator();
        click(By.linkText("Пользователи"));

        WebElement viewButton = driver.findElement(By.xpath("//a[contains(text(), 'Просмотр')]"));
        viewButton.click();

        assertTrue(driver.getTitle().contains("Пользователь"));
        assertTrue(driver.getPageSource().contains("Информация о пользователе"));

        click(By.linkText("Назад к списку"));
    }

    @Test
    void deleteUserTest() {
        loginAsModerator();
        click(By.linkText("Пользователи"));

        String loginToDelete = "user14";
        WebElement deleteLink = driver.findElement(By.xpath("//tr[td[contains(text(), '" + loginToDelete + "')]]//a[contains(text(),'Удалить пользователя')]"));

        deleteLink.click();

        wait.until(ExpectedConditions.titleIs("Пользователи"));
        assertFalse(driver.getPageSource().contains(loginToDelete));
    }


    @Test
    void addSectionTest() {
        loginAsModerator();
        click(By.linkText("Разделы"));
        click(By.linkText("Добавить раздел"));

        String sectionName = "Культура";
        findElement(By.id("name")).sendKeys(sectionName);
        click(By.xpath("//button[@type='submit' and normalize-space(text())='Сохранить']"));

        wait.until(ExpectedConditions.urlToBe(baseUrl + "/sections"));
        assertTrue(driver.getPageSource().contains(sectionName));


        WebElement deleteLink = driver.findElement(By.xpath("//tr[td[contains(text(),'" + sectionName + "')]]//a[contains(text(), 'Удалить раздел')]"));
        deleteLink.click();

        wait.until(ExpectedConditions.urlToBe(baseUrl + "/sections"));
        assertFalse(driver.getPageSource().contains(sectionName));
    }

    @Test
    void filterSectionByNameTest() {
        loginAsModerator();
        driver.get(baseUrl + "/sections");

        String filterName = "Наука";
        WebElement input = findElement(By.id("name"));
        input.clear();
        input.sendKeys(filterName);
        click(By.xpath("//button[@type='submit' and normalize-space(text())='Поиск']"));

        assertTrue(driver.getPageSource().contains(filterName));
    }

    @Test
    void addThemeAndMessageAsUserTest() {
        loginAsUser();

        click(By.linkText("Разделы"));
        wait.until(ExpectedConditions.urlMatches(".*/sections"));

        String sectionName = "Путешествия";
        WebElement viewSecLink = driver.findElement(By.xpath("//tr[td[contains(text(),'" + sectionName + "')]]//a[contains(text(), 'Просмотр')]"));
        viewSecLink.click();
        String themeName = "Кухня";
        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F"));

        click(By.linkText("Добавить тему"));
        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F/user_panel_add_theme"));

        findElement(By.id("name")).sendKeys(themeName);
        click(By.xpath("//button[@type='submit' and normalize-space(text())='Сохранить']"));

        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + themeName + "')]")));
        assertTrue(driver.getPageSource().contains(themeName));

        WebElement viewLink = driver.findElement(By.xpath("//tr[td[contains(text(),'" + themeName + "')]]//a[contains(text(), 'Просмотр')]"));
        viewLink.click();

        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F/%D0%9A%D1%83%D1%85%D0%BD%D1%8F"));
        click(By.linkText("Написать сообщение"));

        String messageHeader = "Блюда";
        String messageText = "Блины";

        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F/%D0%9A%D1%83%D1%85%D0%BD%D1%8F/user_panel_add_message"));
        findElement(By.id("header")).sendKeys(messageHeader);
        findElement(By.id("text")).sendKeys(messageText);
        WebElement fileInput = findElement(By.id("file"));
        fileInput.sendKeys("/Users/svetlanamatveeva/Downloads/Russkie-bliny-3_Sn.jpg");

        click(By.xpath("//button[@type='submit' and text()='Сохранить']"));

        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F/%D0%9A%D1%83%D1%85%D0%BD%D1%8F"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + messageHeader + "')]")));
        assertTrue(driver.getPageSource().contains(messageHeader));
        assertTrue(driver.getPageSource().contains(messageText));


        WebElement logoutButton = driver.findElement(By.xpath("//form[@action='/logout']//button[text()='Выйти']"));
        logoutButton.click();
        wait.until(ExpectedConditions.urlMatches(".*logout"));

        loginAsModerator();
        click(By.linkText("Разделы"));
        wait.until(ExpectedConditions.urlMatches(".*/sections"));

        viewSecLink = driver.findElement(By.xpath("//tr[td[contains(text(),'" + sectionName + "')]]//a[contains(text(), 'Просмотр')]"));
        viewSecLink.click();
        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F"));

        viewLink = driver.findElement(By.xpath("//tr[td[contains(text(),'" + themeName + "')]]//a[contains(text(), 'Просмотр')]"));
        viewLink.click();

        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F/%D0%9A%D1%83%D1%85%D0%BD%D1%8F"));

        WebElement messageRow = findElement(By.xpath("//table/tbody/tr[1]"));

        WebElement deleteLink = messageRow.findElement(By.linkText("Удалить сообщение"));
        deleteLink.click();

        wait.until(ExpectedConditions.urlContains("/sections/"));
        assertFalse(driver.getPageSource().contains(messageHeader));

        click(By.linkText("Назад к списку тем"));
        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F"));

        WebElement deleteThLink = driver.findElement(
                By.xpath("//tr[td[contains(text(), '" + themeName + "')]]//a[contains(text(),'Удалить тему')]")
        );
        deleteThLink.click();

        assertFalse(driver.getPageSource().contains(themeName));
    }

    @Test
    void filterThemesByNameTest() {
        loginAsModerator();
        click(By.linkText("Разделы"));

        String sectionName = "Путешествия";
        WebElement viewSecLink = driver.findElement(By.xpath("//tr[td[contains(text(),'" + sectionName + "')]]//a[contains(text(), 'Просмотр')]"));
        viewSecLink.click();

        wait.until(ExpectedConditions.urlMatches(".*/sections/%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D1%8F"));

        String themeName = "маршруты";

        WebElement searchField = findElement(By.id("th_name"));
        searchField.clear();
        searchField.sendKeys(themeName);

        click(By.xpath("//button[@type='submit' and text()='Поиск']"));

        assertTrue(driver.getPageSource().contains("маршруты"));
    }
}
