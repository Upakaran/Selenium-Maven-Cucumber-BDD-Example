@TestCase3
Feature: Basic functionality on login page of Application using data-table

@TestCase3_Scenario1
Scenario: Verification of entering credentials in login page 

Given Open the Chrome and launch the application			

When Enter the Username	and password using datatable
| username | password |
| username40 | password40 |
| username41 | password41 |
| username42 | password42 |

Then Reset the credential




