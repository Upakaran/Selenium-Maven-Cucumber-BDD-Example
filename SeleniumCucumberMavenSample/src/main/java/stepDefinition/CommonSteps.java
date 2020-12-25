package stepDefinition;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

public class CommonSteps {

	static ExtentReports extent;
	ExtentHtmlReporter reporter;

	@Given("^Create an extent report to be used for this feature$")
	public void create_an_extent_report_to_be_used_for_this_feature (DataTable datatable) throws Throwable {
		String testname = datatable.asLists(String.class).get(1).get(0).toString();
		reporter = new ExtentHtmlReporter("ExtentReports/"+testname+" - Report.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);

	}

	@Given("^Flush the extent report to be used for this feature$")
	public void flush_the_extent_report_to_be_used_for_this_feature() throws Throwable {
		extent.flush();
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		// below line is just to append the date format with the screenshot name
		// to avoid duplicate names
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		System.out.println(source.getAbsolutePath());

		String destination = "FailedTestScreenshots/" + screenshotName + dateName + ".png";
		System.out.println("destination " + destination);
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		// Returns the captured file path
		String readScreenshotPath = "../FailedTestScreenshots/" + screenshotName + dateName + ".png";
		return readScreenshotPath;

	}

}
