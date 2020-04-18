package mx.tec.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestHtml2ApplicationTests {

	private static WebDriver driver;
	
	@BeforeAll
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", "/Users/andres/Downloads/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}
	@Test
	public void givenAClient_whenEnteringAutomationPractice_thenPageTitleIsCorrect() throws Exception{
		//when
		driver.get("http://automationpractice.com/index.php");
		String title = driver.getTitle();
		
		//Then
		assertEquals("My Store", title);
		
	}
	@Test
	public void givenAClient_whenEnteringLoginCredentials_thenAccountPageIsDisplayed() throws Exception{
		//When
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01187827@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("marioandres");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		String title = driver.getTitle();
		
		//Then
		assertEquals("My account - My Store", title);
		WebElement link = driver.findElement(By.xpath("//a[@class='logout']"));
		link.click();
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticatinPageIsDisplayed() throws Exception {
		//When
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01187827@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("wrongpassword");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		String title = driver.getTitle();
		
		//Then
		assertEquals("Login - My Store", title);
	}
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed() throws Exception {
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01187827@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("wrongpassword");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		WebElement error = driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li"));
		 
		 
		 assertEquals("Authentication failed.",error.getText());
	}
	@Test
	public void givenAClient_whenSearchingNotExistingProduct_thenNoResultsIsDisplayed() throws Exception {
		driver.get("http://automationpractice.com/index.php");
		WebElement searchField = driver.findElement(By.id("search_query_top"));
		searchField.sendKeys("nothing");
		WebElement searchButton = driver.findElement(By.name("submit_search"));
		searchButton.click();
		
		WebElement paragraph = driver.findElement(By.xpath("//p[@class='alert alert-warning']"));
		assertTrue(paragraph.getText().contains("No results were found"), "not found log");
		
	}
	@Test
	public void givenAClient_whenSearchingEmptyString_thenPleaseEnterIsDisplayed() throws Exception {
		driver.get("http://automationpractice.com/index.php");
		WebElement searchField = driver.findElement(By.id("search_query_top"));
		searchField.sendKeys("");
		WebElement searchButton = driver.findElement(By.name("submit_search"));
		searchButton.click();
		
		WebElement paragraph = driver.findElement(By.xpath("//p[@class='alert alert-warning']"));
		assertTrue(paragraph.getText().contains("Please enter a search keyword"), "please enter log");
		
	}
	@Test
	public void givenAClient_whenSigingOut_thenAuthenticationPageIsDisplayed() throws Exception {
		//When
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.sendKeys("a01187827@itesm.mx");
		WebElement passwordField = driver.findElement(By.id("passwd"));
		passwordField.sendKeys("marioandres");
		WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
		submitButton.click();
		WebElement link = driver.findElement(By.xpath("//a[@class='logout']"));
		link.click();
		String title = driver.getTitle();
		//Then
		assertEquals("Login - My Store", title);
	}
}
