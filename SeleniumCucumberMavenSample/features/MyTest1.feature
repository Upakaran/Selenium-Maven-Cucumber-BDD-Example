@TestCase1
Feature: Basic functionality on login page of Application 

@TestCase1_Scenario1
Scenario Outline: Verification of Reset button 

Given Open the Chrome and launch the application			

When Enter the Username	"<username>"

And Enter the Password		"<password>"

Then Reset the credential

Examples: 
      | username  | password |
      | username01| password01|  
      | username02| password02| 
      | username03| password03|



@TestCase1_Scenario2
Scenario: Verification of Title of the Guru99 Demo Page 

Given Open the Chrome and launch the application			

When Click on Selenium Dropdown	

And select flash movie demo		

Then verify the page title
