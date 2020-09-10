package utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserUtility {

	static WebDriver driver;
	static ChromeOptions options;

	public static void configureBrowser(String browserName)  {

		if (browserName.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");

			Map<String, Object> prefs = new HashMap<String, Object>();

			prefs.put("profile.default_content_setting_values.notifications", 2);

			prefs.put("credentials_enable_service", false);

			prefs.put("profile.password_manager_enabled", false);

			prefs.put("download.default_directory", "download_dir");

			options = new ChromeOptions();

			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-notifications");
			// Set the experimental option
			options.setExperimentalOption("prefs", prefs);
			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation", "load-extension" });

			
		}
	}
	
	public static WebDriver LaunchBrowser(String browserName) throws InterruptedException{
		
		if (browserName.equalsIgnoreCase("chrome")) {
			if(driver==null){
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
				Thread.sleep(2000);
			}
			
		}
		
		return driver;
	}
	
	public static void exitBrowser(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}
}
