package stepDefinition;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utilities.BrowserUtility;
import utilities.PropertiesFileReader;

public class Steps {

	PropertiesFileReader propertiesFileReader;

	static WebDriver driver;
	String expectedTitle = "";

	@Given("^Open the Chrome and launch the application$")
	public void open_the_Chrome_and_launch_the_application() throws Throwable {
		System.out.println("This step opens the browser.");
		propertiesFileReader = new PropertiesFileReader();
		if (driver == null) {
			System.out.println("launching browser");
			driver = BrowserUtility.LaunchBrowser(propertiesFileReader.getUserDefinedProperty("browser.name"));
		}
		driver.navigate().to(propertiesFileReader.getUserDefinedProperty("browser.baseURL"));

	}

	@When("^Enter the Username	\"([^\"]*)\"$")
	public void enter_the_Username(String username) throws Throwable {
		System.out.println("This step enter the Username on the login page.");
		driver.findElement(By.name("uid")).sendKeys(username);
		Thread.sleep(2000);
	}

	@When("^Enter the Password		\"([^\"]*)\"$")
	public void enter_the_Password(String password) throws Throwable {
		System.out.println("This step enter the Password on the login page.");

		driver.findElement(By.name("password")).sendKeys(password);
		Thread.sleep(2000);
	}

	@Then("^Reset the credential$")
	public void Reset_the_credential() throws Throwable {
		System.out.println("This step click on the Reset button.");
		driver.findElement(By.name("btnReset")).click();
	}

	@When("^Click on Selenium Dropdown$")
	public void Click_on_Selenium_Dropdown() throws Throwable {
		System.out.println("This step clicks on Selenium Dropdown.");
		driver.findElement(
				By.xpath("//li[@class='dropdown']/a[@data-toggle='dropdown' and contains(text(),'Selenium')]")).click();
		Thread.sleep(2000);
	}

	@When("^select flash movie demo$")
	public void select_flash_movie_demo() throws Throwable {
		System.out.println("This step is to select flash movie demon.");
		driver.findElement(By.xpath("//a[contains(text(),'Flash Movie Demo')]")).click();
		Thread.sleep(2000);
	}

	@Then("^verify the page title$")
	public void verify_the_page_title() throws Throwable {
		System.out.println("This step verifies the Page Title.");
		propertiesFileReader = new PropertiesFileReader();
		String currentUrl = driver.getCurrentUrl();
		if(currentUrl.contains("demo.guru99.com/test/flash-testing")){
			expectedTitle = propertiesFileReader.getUserDefinedProperty("demoSite.pageTitle");
		}
		else if(currentUrl.contains("page-1")){
			expectedTitle = propertiesFileReader.getUserDefinedProperty("demoSite.pageTitleSEOPage1");
		}
		else if(currentUrl.contains("page-6")){
			expectedTitle = propertiesFileReader.getUserDefinedProperty("demoSite.pageTitleSEOPage6");
		}
		Thread.sleep(1000);
		String actualTitle = driver.getTitle();
		System.out.println("Actual Page title is " + actualTitle);
		System.out.println("Expected Page title is " + expectedTitle);
		Assert.assertEquals(expectedTitle, actualTitle);

	}

	@When("^Click on SEO Dropdown$")
	public void Click_on_SEO_Dropdown() throws Throwable {
		System.out.println("This step opens the SEO dropdown.");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//li[@class='dropdown']//a[contains(text(),'SEO')]")).click();
	}

	@When("^select the SEO \"([^\"]*)\"$")
	public void select_the_SEO_Page(String seoPage) throws Throwable {
		System.out.println("This step selects the given SEO page in dropdown.");
		Thread.sleep(2000);
		if (seoPage.equals("Page1")) {
			driver.findElement(By.xpath("//a[contains(text(),'Page-1') and @href='http://demo.guru99.com/seo/page-1.html']")).click();
			Thread.sleep(2000);
		}

		if (seoPage.equals("Page6")) {
			driver.findElement(By.xpath("//a[@href='http://demo.guru99.com/seo/page-6.html' and contains(text(),'Page-6')]")).click();
			Thread.sleep(2000);
		}
	}

	@Then("^return to the Guru(\\d+) Demo Homepage$")
	public void Return_to_(Integer arg1) throws Throwable {
		System.out.println("This step navigates back to Guru99 demo page");
		Thread.sleep(2000);
		driver.navigate().back();
	}
	
	
	@Then("^Hit the login button to try to login to application$")
		public void Hit_the_login_button_to_Try_to_login_to_application	() throws Throwable  {
		System.out.println("This step clicks the login button.");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
	}

	@Then("^Verify the login not successful alert$")
	public void verify_the_Login_not_successful_alert () throws Throwable  {
		System.out.println("This step gets alert text and accepts alert.");
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		String actualAlertText = alert.getText();
		propertiesFileReader = new PropertiesFileReader();
		String expectedAlertText = propertiesFileReader.getUserDefinedProperty("demoSite.loginUnsuccessfulAlert");
		System.out.println("Actual alert is " + actualAlertText);
		System.out.println("Expected alert is " + expectedAlertText);
		Assert.assertEquals(expectedAlertText, expectedAlertText);
		Thread.sleep(2000);
		alert.accept();
	}
	
	@When("^Enter the Username	and password using datatable$") 
	public void Enter_the_Username_and_password_using_datatable	(DataTable datatable)  throws Throwable {
	   System.out.println("This step enters the username and password from the datatable and then clears the field");
	
		List<Map<String, String>> rows = datatable.asMaps(String.class, String.class);
		
		for (Map<String, String> columns : rows) {
			driver.findElement(By.name("uid")).sendKeys(columns.get("username"));
			Thread.sleep(2000);
			driver.findElement(By.name("password")).sendKeys(columns.get("password"));
			Thread.sleep(2000);
			driver.findElement(By.name("uid")).clear();
			Thread.sleep(1000);
			Assert.assertEquals(driver.findElement(By.id("message23")).getText(), "User-ID must not be blank");
			driver.findElement(By.name("password")).clear();
			Thread.sleep(1000);
			Assert.assertEquals(driver.findElement(By.id("message18")).getText(), "Password must not be blank");
			System.out.print(columns.get("username")+"  "+columns.get("password"));
			System.out.println();
		}
	}


}
