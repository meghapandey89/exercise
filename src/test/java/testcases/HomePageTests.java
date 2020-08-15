package testcases;

import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTests {

	String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();

	String driverPath = relativePath + "\\drivers\\chromedriver.exe";

	WebDriver driver; 


	@BeforeClass 
	public void start() {

		System.setProperty("webdriver.chrome.driver", driverPath);

		driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("http://automationpractice.com/index.php");	

	}


	@Test(groups = {"Smoke"})
	public void testCase_Search_functionality() throws InterruptedException {

		WebElement searchField = driver.findElement(By.id("search_query_top"));

		String input = "Faded Short";

		searchField.sendKeys(input);

		Thread.sleep(3000);

		//Search Validation

		List<WebElement> allResults = driver.findElements(By.cssSelector("div.ac_results > ul > li > strong"));

		for (WebElement oneResult : allResults) {
			String actual = oneResult.getText();
			Assert.assertTrue(actual.contains(input));
			break;
		}

	}


	@Test(groups = { "Regression"})
	public void testCase_add_to_cart_functionality() throws InterruptedException {

		String productNameTxt = driver.findElement(By.cssSelector("ul#homefeatured > li:nth-of-type(1)  > div > div.right-block > h5>a")).getText();

		System.out.println(productNameTxt);

		WebElement addToCart = driver.findElement(By.cssSelector("ul#homefeatured > li:nth-of-type(1)  > div > div.right-block > div > a:nth-of-type(1)"));

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].click();", addToCart);

		Thread.sleep(4000);

		// Click on continue shopping

		WebElement continueBtn = driver.findElement(By.xpath("//span[@class='continue btn btn-default button exclusive-medium']//span[1]"));

		js.executeScript("arguments[0].click();", continueBtn);

		Thread.sleep(3000);

		//Hover on Cart

		Actions action = new Actions(driver);
		
		WebElement cart = driver.findElement(By.cssSelector("div.shopping_cart > a"));
		
		action.moveToElement(cart).build().perform();

		Thread.sleep(3000);

		WebElement cartTitle = driver.findElement(By.cssSelector("a.cart_block_product_name"));

		String cartTxt = cartTitle.getAttribute("title");

		System.out.println(cartTxt);

		Assert.assertTrue(productNameTxt.equalsIgnoreCase(cartTxt));

	}

	@Test(groups = { "Regression"})
	public void testCase_more_functionality() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		Actions action = new Actions(driver);

		String productNameTxt = driver.findElement(By.cssSelector("ul#homefeatured > li:nth-of-type(1)  > div > div.right-block > h5>a")).getText();

		System.out.println(productNameTxt);

		js.executeScript("scroll(0, 650);");

		Thread.sleep(3000);	

		WebElement morebtn = driver.findElement(By.cssSelector("ul#homefeatured > li:nth-of-type(1)  > div > div.right-block > div > a:nth-of-type(2)"));

		WebElement mouseHoverMore = driver.findElement(By.cssSelector("ul:nth-of-type(1)  > li:nth-of-type(1) > div > div.right-block "));

		action.moveToElement(mouseHoverMore).build().perform();

		Thread.sleep(3000);		

		js.executeScript("arguments[0].click();", morebtn);

		WebElement itemtextelement = driver.findElement(By.cssSelector("div.center_column > div > div > div > h1"));	

		String itemtext = itemtextelement.getText();

		Assert.assertTrue(productNameTxt.equalsIgnoreCase(itemtext));
	}		
	
	@AfterClass
	public void cleanUp() {
		
		driver.quit();
	}

}		







