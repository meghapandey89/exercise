package testcases;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTests {
	
	String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();

	String driverPath = relativePath + "\\drivers\\chromedriver.exe";

	WebDriver driver; 

	@BeforeMethod
	public void start() {

		System.setProperty("webdriver.chrome.driver", driverPath);

		driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("http://automationpractice.com/index.php");	

	}		


	@Test(groups = { "Regression"})
	public void testCase_Login_Page_functionality() throws InterruptedException { 


		WebElement addToCart = driver.findElement(By.cssSelector("ul#homefeatured > li:nth-of-type(1)  > div > div.right-block > div > a:nth-of-type(1)"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("arguments[0].click();", addToCart);				

		Thread.sleep(3000);

		driver.findElement(By.xpath("//span[contains(text(),'Proceed to checkout')]")).click(); 

		Thread.sleep(5000);	

		driver.findElement(By.cssSelector("a.button.btn.btn-default.standard-checkout.button-medium > span")).click(); 	

		Thread.sleep(5000);

		WebDriverWait wait = new WebDriverWait(driver,30);
		// Enter Email Address

		WebElement emailTxtBox = driver.findElement(By.id("email"));

		emailTxtBox.sendKeys("thisistest@@@@");

		// Enter Password

		WebElement pwdTxtBox = driver.findElement(By.id("passwd"));

		pwdTxtBox.sendKeys("password");

		// Click on SignIn Button

		WebElement signInBtn = driver.findElement(By.id("SubmitLogin"));

		signInBtn.click();

		WebElement errorBanner = driver.findElement(By.cssSelector("div.alert.alert-danger > p"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-danger > p")));

		Assert.assertEquals(true, errorBanner.isDisplayed());

	}


	@Test(groups = { "Regression"})
	public void testCase_size_click_Add_Cart_functionality() throws InterruptedException { 

		WebElement morebtn = driver.findElement(By.cssSelector("ul#homefeatured > li:nth-of-type(1)  > div > div.right-block > div > a:nth-of-type(2)"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", morebtn);

		Thread.sleep(5000);

		Select drpDwnSize = new Select(driver.findElement(By.id("group_1")));
		drpDwnSize.selectByVisibleText("M");

		String newSize = drpDwnSize.getFirstSelectedOption().getAttribute("title");

		Assert.assertEquals("M", newSize);	

	}
	

	@Test(groups = { "Smoke"})
	public void testCase_icrement_decrement_functionality() throws InterruptedException {		
		
		WebElement morebtn = driver.findElement(By.cssSelector("ul#homefeatured > li:nth-of-type(1)  > div > div.right-block > div > a:nth-of-type(2)"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("arguments[0].click();", morebtn);

		WebElement quantityTxtBox = driver.findElement(By.cssSelector("#quantity_wanted"));

		String beforeIncrement = quantityTxtBox.getText();

		WebElement incrementbtn = driver.findElement(By.cssSelector(("a.btn.btn-default.button-plus.product_quantity_up")));

		incrementbtn.click();

		String afterIncrement = quantityTxtBox.getText();

		Assert.assertEquals(Integer.parseInt(beforeIncrement), Integer.parseInt(afterIncrement)+1);

	}
	

	@AfterMethod
	public void cleanUp() {
		
		driver.quit();
	}


}
