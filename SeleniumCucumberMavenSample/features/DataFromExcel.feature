
@TestCase4
Feature: Basic functionality on login page of Application using excel data

@TestCase4_Scenario1
Scenario Outline: Verification of alert message after trying to login for each testcase 

Given Open the Chrome and launch the application			

When Enter the Username	and password for testcase "<TestCaseID>"
| values |
| sheet1 |

Then Click on the login button

And validate the alert message

And update the excelsheet with alert message

Examples: 
      | TestCaseID |
      | TC_01 | 
     	| TC_02 |  
      | TC_03 | 
      | TC_04 | 
      | TC_05 | 

@TestCase4_Scenario2
Scenario Outline: Verification of alert message after trying to login for each testcase 

Given Open the Chrome and launch the application			

When Enter the Username	and password for testcase "<TestCaseID>"
| values |
| sheet2 |

Then Clear both the username and password fields

And validate the alert message for both fields

And update the excelsheet with alert message


Examples: 
      | TestCaseID |
      | TC_11 | 
     	| TC_12 |  
      | TC_13 | 
      | TC_14 | 
      | TC_15 |
