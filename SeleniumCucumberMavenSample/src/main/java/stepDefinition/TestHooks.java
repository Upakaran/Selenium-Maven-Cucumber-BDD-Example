package stepDefinition;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class TestHooks {
	
	@Before
	public void beforeScenario(Scenario scenario) {		
		System.out.println("Started execution for the scenario : " + scenario.getName());
	}
	
	@Before("@TestCase4")
	public void beforeTestCase4(Scenario scenario) {		

		System.out.println("=========================================");
		System.out.println("Executing before Testcase4");
		System.out.println("=========================================");
		
	}
	
	@After
	public void AfterScenario(Scenario scenario) {
		System.out.println("Completed execution for the scenario :" + scenario.getName());
	//	System.out.println("Scenario status : "+scenario.getStatus().toString());
	}
	
	@After("@TestCase4")
	public void afterTestCase4(Scenario scenario) {
	//	System.out.println("Completed execution for the scenario :" + scenario.getName());
		System.out.println("Scenario status : "+scenario.getStatus().toString());
		String status = scenario.getStatus().toString().toUpperCase();
		StepsNew.updateExcelWithStatus(status);
	}

}
