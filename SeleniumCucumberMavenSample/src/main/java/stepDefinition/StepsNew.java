package stepDefinition;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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

	@When("^Enter the Username and password for testcase \"([^\"]*)\"$")
	public void Enter_the_Username_and_password_for_testcase(String testCaseID, DataTable dataTable) throws Throwable {

		String sheetToUse = dataTable.asLists(String.class).get(1).get(0);

		System.out.println("This step enter the Username and password on the login page.");
		propertiesFileReader = new PropertiesFileReader();
		this.testCaseID = testCaseID;
		String filePath = propertiesFileReader.getUserDefinedProperty("testdatafilepath").toString();
		if (sheetToUse.contains("sheet1")) {
			sheetName = propertiesFileReader.getUserDefinedProperty("sheetname1").toString();
		} else
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
		message1 = actualAlertText;
		Thread.sleep(2000);
		
		Assert.assertEquals(expectedAlertText, actualAlertText);
		alert.accept();
		System.out.println("Alert accepted as scenario did not fail.");
	}

	@Then("^update the excelsheet with alert message$")
	public void update_the_excelsheet_with_alert_message() throws Throwable {
		System.out.println("This step updates the excel sheet with alert message");
		String filePath = propertiesFileReader.getUserDefinedProperty("testdatafilepath").toString();
		String message = ExcelOperationsFillo.updateExcelAfterTest(filePath, sheetName, testCaseID, message1, message2);
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
		Assert.assertEquals(driver.findElement(By.id("message23")).getText(),
				propertiesFileReader.getUserDefinedProperty("demoSite.usernameBlankMessage"));
		Assert.assertEquals(driver.findElement(By.id("message18")).getText(),
				propertiesFileReader.getUserDefinedProperty("demoSite.passwordBlankMessage"));

	}

	@When("^Click on Visit here link$")
	public void click_on_Visit_here_link() throws Throwable {

		System.out.println("This step clicks on the link to go to the new registration page.");
		Thread.sleep(2000);
		WebElement clickHereLink = driver.findElement(By.xpath("//a[text()='here']"));
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",clickHereLink );
		Thread.sleep(2000);
		Actions actions = new Actions(driver);
		actions.moveToElement(clickHereLink).click().build().perform();
		Thread.sleep(2000);
	}

	@Then("^Enter Email ID for testcase \"([^\"]*)\" and click submit$")
	public void enter_Email_ID_for_testcase_and_click_submit(String testCaseID, DataTable dataTable) throws Throwable {

		String sheetToUse = dataTable.asLists(String.class).get(1).get(0);

		System.out.println("This step enter the email ID to get access and click submit page.");
		propertiesFileReader = new PropertiesFileReader();
		this.testCaseID = testCaseID;
		String filePath = propertiesFileReader.getUserDefinedProperty("testdatafilepath").toString();
		if (sheetToUse.contains("sheet3")) {
			sheetName = propertiesFileReader.getUserDefinedProperty("sheetname3").toString();
		}

		ArrayList<Map<String, String>> list = ExcelOperationsFillo.readExcelTestCases(filePath, sheetName, testCaseID);
		String email = list.get(0).get("EMAIL_ID").toString();

		driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys(email);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
		;
		Thread.sleep(2000);

	}

	@Then("^Validate the grant acccess message$")
	public void validate_the_grant_acccess_message() throws Throwable {
		System.out.println("This step validates the grant access message adter registration has been done.");
		String accessMessage = driver.findElement(By.xpath("//table//tr[1]//following::h2[contains(text(),Access)]"))
				.getText().toString();
		System.out.println(accessMessage);
		Assert.assertEquals(propertiesFileReader.getUserDefinedProperty("demoSite.newAccessMessage"), accessMessage);
	}

	@Then("^Update the excel with userID and password$")
	public void update_the_excel_with_userID_and_password() throws Throwable {
		System.out.println(
				"This step updates the excel sheet with newly generated userid and password for corresponding emailID entered");
		String filePath = propertiesFileReader.getUserDefinedProperty("testdatafilepath").toString();
		String generatedUserID = driver.findElement(By.xpath("//table//tr[4]/td[2]")).getText().toString();
		String generatedpassword = driver.findElement(By.xpath("//table//tr[5]/td[2]")).getText().toString();
		System.out.println("The newly gererated credentials is - Userid : "+generatedUserID+" / password : "+generatedpassword);
		message1 = generatedUserID;
		message2 = generatedpassword;
		String message = ExcelOperationsFillo.updateExcelAfterTest(filePath, sheetName, testCaseID, message1, message2);
		System.out.println(message);

	}

	public static void updateExcelWithStatus(String status) {
		System.out.println("This step updates the excel sheet with test status");
		String filePath = new PropertiesFileReader().getUserDefinedProperty("testdatafilepath").toString();
		String message = ExcelOperationsFillo.updateExcelAfterTestWithStatus(filePath, sheetName, testCaseID, status);
		System.out.println(message);
	}

}
