package stepDefinition;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utilities.ExcelOperationsFillo;
import utilities.PropertiesFileReader;

public class StepsNew {

	PropertiesFileReader propertiesFileReader;

	static WebDriver driver = Steps.driver;

	static String testCaseID = "";
	String message1 = "";
	String message2 = "";
	static String sheetName = "";

	@When("^Enter the Username	and password for testcase \"([^\"]*)\"$")
	public void Enter_the_Username_and_password_for_testcase(String testCaseID , DataTable dataTable) throws Throwable {
		
		String sheetToUse = dataTable.asLists(String.class).get(1).get(0);
		
		System.out.println("This step enter the Username and password on the login page.");
		propertiesFileReader = new PropertiesFileReader();
		this.testCaseID = testCaseID;
		String filePath = propertiesFileReader.getUserDefinedProperty("testdatafilepath").toString();
		if(sheetToUse.contains("sheet1")){
			sheetName = propertiesFileReader.getUserDefinedProperty("sheetname1").toString();
		}
		else
			sheetName = propertiesFileReader.getUserDefinedProperty("sheetname2").toString();
		
		ArrayList<Map<String, String>> list = ExcelOperationsFillo.readExcelTestCases(filePath, sheetName, testCaseID);
		String username = list.get(0).get("USERNAME").toString();
		String password = list.get(0).get("PASSWORD").toString();

		driver.findElement(By.name("uid")).sendKeys(username);
		Thread.sleep(2000);
		driver.findElement(By.name("password")).sendKeys(password);
		Thread.sleep(2000);

	} 

	@Then("^Click on the login button$")
	public void Click_on_the_login_button() throws Throwable {
		System.out.println("This step clicks the login button.");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
	}

	@Then("^validate the alert message$")
	public void validate_the_alert_message() throws Throwable {
		System.out.println("This step gets alert text and accepts alert.");
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		String actualAlertText = alert.getText();
		String expectedAlertText = propertiesFileReader.getUserDefinedProperty("demoSite.loginUnsuccessfulAlert");
		System.out.println("Actual alert is " + actualAlertText);
		System.out.println("Expected alert is " + expectedAlertText);
		Assert.assertEquals(expectedAlertText, expectedAlertText);
		Thread.sleep(2000);
		alert.accept();
		message1 = actualAlertText;
	}

	@Then("^update the excelsheet with alert message$")
	public void update_the_excelsheet_with_alert_message() throws Throwable {
		System.out.println("This step updates the excel sheet with alert message");
		String filePath = propertiesFileReader.getUserDefinedProperty("testdatafilepath").toString();
		String message = ExcelOperationsFillo.updateExcelAfterTest(filePath, sheetName, testCaseID, message1,
				message2);
		System.out.println(message);
	}

	@Then("^Clear both the username and password fields$")
	public void Clear_both_the_username_and_password_fields() throws Throwable {
		System.out.println("This step clears both the username and password ");
		driver.findElement(By.name("uid")).clear();
		Thread.sleep(2000);
		driver.findElement(By.name("password")).clear();
		Thread.sleep(2000);
		message1 = driver.findElement(By.id("message23")).getText();
		message2 = driver.findElement(By.id("message18")).getText();
	}

	@Then("^validate the alert message for both fields$")
	public void validate_the_alert_message_for_both_fields() throws Throwable {
		System.out.println("This step validates the alert message for username and password fields.");
		Assert.assertEquals(driver.findElement(By.id("message23")).getText(), propertiesFileReader.getUserDefinedProperty("demoSite.usernameBlankMessage"));
		Assert.assertEquals(driver.findElement(By.id("message18")).getText(), propertiesFileReader.getUserDefinedProperty("demoSite.passwordBlankMessage"));

	}
	
	public static void updateExcelWithStatus(String status){
		System.out.println("This step updates the excel sheet with test status");
		String filePath = new PropertiesFileReader().getUserDefinedProperty("testdatafilepath").toString();
		String message = ExcelOperationsFillo.updateExcelAfterTestWithStatus(filePath, sheetName, testCaseID, status);
		System.out.println(message);
	}
	
	

}
