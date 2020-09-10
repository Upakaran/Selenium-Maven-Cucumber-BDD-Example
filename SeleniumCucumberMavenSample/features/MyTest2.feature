@TestCase2
Feature: Secondary functionality on login page of Application 

@TestCase2_Scenario1
Scenario Outline: Verification of Login button 

Given Open the Chrome and launch the application			

When Enter the Username	"<username>"

And Enter the Password		"<password>"

Then Hit the login button to try to login to application

And Verify the login not successful alert

Examples: 
      | username  | password |
      | username04 | password04 |  
      | username14 | password14 | 
      | username24 | password24 |




@TestCase2_Scenario2
Scenario Outline: Verification of Title of the Guru99 Demo SEO Page 

Given Open the Chrome and launch the application			

When Click on SEO Dropdown	

And select the SEO "<SEO>"		

Then verify the page title

And return to the Guru99 Demo Homepage

Examples:
| SEO |
| Page1 |
| Page6 |


