package stepRunner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import utilities.BrowserUtility;
import utilities.PropertiesFileReader;

@RunWith(Cucumber.class)

@CucumberOptions(features = "features", 
glue = { "stepDefinition" },
//tags = {"@TestCase3"}, 
plugin = {"pretty","html:target/site/cucumber-pretty","json:target/site/cucumber.json"},
monochrome = true)

public class Runner {
	static PropertiesFileReader propertiesFileReader;

	@BeforeClass
	public static void beforeClass() {

		propertiesFileReader = new PropertiesFileReader();
		BrowserUtility.configureBrowser(propertiesFileReader.getUserDefinedProperty("browser.name"));
	}

	@AfterClass
	public static void afterClass() {

		BrowserUtility.exitBrowser();
		
	}
	
		
}
