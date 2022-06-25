package variousConcepts;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	
	WebDriver driver;
	String browser;
	String url;
	
	By USERNAME_FIELD =By.xpath("//*[@id=\"username\"]");
	By PASSWORD_FIELD =By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD =By.xpath("/html/body/div/div/div/form/div[3]/button");	
	By DASHBOARD_HEADER_FIELD =By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By CUSTOMER_MENU_BUTTON_FIELD = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADDCUSTOMER_MENU_BUTTON_FIELD = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By FULLNAME_FIELD = By.xpath("//*[@id=\"account\"]");
	By COMPANY_DROPDOWN = By.xpath("//*[@id=\"cid\"]");
	By EMAIL_FIELD = By.xpath("//*[@id=\"email\"]");
	By COUNTRY_FIELD = By.xpath("//*[@id=\"country\"]");
	
	String username = "demo@techfios.com";
	String password = "abc123";
	String dashboard = "Dashboard";	
	
	
	@BeforeClass
	public void readConfig() {
		
		//InputStream //BufferedReader //Scanner //FileReader
		
		try {
			
			InputStream input = new FileInputStream("src\\main\\java\\configs\\Config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@BeforeMethod
	public void init() {
		
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
			
		}else if(browser.equalsIgnoreCase("FireFox")) {
			
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\Owner\\Desktop\\Selenium\\session5_TestNG\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(url);
	}
	
	@Test(priority=1)
	public void loginTest() {
		
		driver.findElement(USERNAME_FIELD).sendKeys(username);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboard, "Dashboard is not available");
		
	}
	@Test(priority=2)
	public void addCustomer() throws InterruptedException {
		loginTest();
		driver.findElement(CUSTOMER_MENU_BUTTON_FIELD).click();
		driver.findElement(ADDCUSTOMER_MENU_BUTTON_FIELD).click();
		Thread.sleep(5000);
		boolean fullNamefield = driver.findElement(FULLNAME_FIELD).isDisplayed();
		Assert.assertTrue(fullNamefield, "Add customer page is not available");
		
//		Random rnd = new Random();
//		int randomNumber = rnd.nextInt(999);
		
		driver.findElement(FULLNAME_FIELD).sendKeys("Selenium" + generateRandomNo(999));
		
		selectFromDropdown(COMPANY_DROPDOWN, "Techfios");
		
		driver.findElement(EMAIL_FIELD).sendKeys("abc" + generateRandomNo(9999) + "@techfios.com");
		
		selectFromDropdown(COUNTRY_FIELD, "Afghanistan");
		
	}
	
	public int generateRandomNo(int boundryNo) {
		Random rnd = new Random();
		int randomNumber = rnd.nextInt(boundryNo);
		return randomNumber;
	}
	private void selectFromDropdown(By byLocator, String visibleText) {
		Select sel1 = new Select(driver.findElement(byLocator));
		sel1.selectByVisibleText(visibleText);
	}

	@AfterMethod
	public void tearDown () {
//		driver.close();
//		driver.quit();
	}
}
