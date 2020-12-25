package stepDefinition;

import java.awt.AWTException;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import cucumber.runtime.ScenarioImpl; 
import gherkin.formatter.model.Result;  
import org.apache.commons.lang3.reflect.FieldUtils;  
import java.lang.reflect.Field;  
import java.util.ArrayList;

public class TestHooks {

	ExtentTest logger;

	@Before("~@ExtentSetup")
	public void beforeScenario(Scenario scenario) {
		System.out.println("=======================================================================");
		System.out.println("Started execution for the scenario : " + scenario.getName());
		System.out.println("========================================================================");

		logger = CommonSteps.extent.createTest(scenario.getName().toUpperCase());
	}

	@After("~@ExtentSetup")
	public void AfterScenario(Scenario scenario) throws HeadlessException, AWTException, IOException {
		System.out.println("========================================================================");
		System.out.println("Completed execution for the scenario :" + scenario.getName());
		System.out.println("========================================================================");

		if (scenario.getStatus().toLowerCase().contains("passed")) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel(scenario.getName() + " Test Case PASSED", ExtentColor.GREEN));

		}

		else if (scenario.getStatus().toLowerCase().contains("failed")) {

			logger.log(Status.FAIL, "Test Case Failed is " + scenario.getName());
			
			logger.log(Status.FAIL, MarkupHelper
					.createLabel(scenario.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
			logError(scenario);
			
			String screenshotPathRelative;
			try {
				screenshotPathRelative = CommonSteps.getScreenshot(Steps.driver, scenario.getName());
				
			} catch (Exception e) {
				System.out.println("issue in taking snapshot");
				System.out.println(e.getMessage());
				
				String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				String screenshotPath = "FailedTestScreenshots/" + scenario.getName() + dateName + ".png";
				BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				
				ImageIO.write(image, "png", new File(screenshotPath));
				screenshotPathRelative = "../FailedTestScreenshots/" + scenario.getName() + dateName + ".png";
				Steps.driver.switchTo().alert().accept();
			}
			
			System.out.println("screenshotPath " + screenshotPathRelative);
			logger.fail("Snapshot below: " + logger.addScreenCaptureFromPath(screenshotPathRelative));
			

		} else if (scenario.getStatus().toLowerCase().contains("skip"))

			logger.log(Status.SKIP,
					MarkupHelper.createLabel(scenario.getName() + " Test Case SKIPPED", ExtentColor.GREY));
	}

	/*@Before("@TestCase4")
	public void beforeTestCase4(Scenario scenario) {

		if (!scenario.getName().contains("Extent")) {
			System.out.println("=========================================");
			System.out.println("Executing before Testcase4");
			System.out.println("=========================================");
		}

	}*/

	@After("@TestCase4")
	public void afterTestCase4(Scenario scenario) {
		// System.out.println("Completed execution for the scenario :" +
		// scenario.getName());
		// System.out.println("Scenario status : " +
		// scenario.getStatus().toString());
		if (!scenario.getName().contains("Extent")) {
			String status = scenario.getStatus().toString().toUpperCase();
			StepsNew.updateExcelWithStatus(status);
		}
	}
	
	private  void logError(Scenario scenario) {
		
		   Field field = FieldUtils.getField(((ScenarioImpl) scenario).getClass(), "stepResults", true);
		   field.setAccessible(true);
		   
		   try {
		       ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
		       for (Result result : results) {
		           if (result.getError() != null)
		        	   logger.fail( result.getError());
		         
		       }
		   } 
		   
		   
		   catch (Exception e) {
			   logger.log(Status.FAIL, e.getMessage());
		   }
		}

}
