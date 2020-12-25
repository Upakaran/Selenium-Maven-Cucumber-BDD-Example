@TestCase3
Feature: Basic functionality on login page of Application using data-table

@ExtentSetup
Scenario: Create Extent Report
Given Create an extent report to be used for this feature
| TestName |
| TestCase3 |

@TestCase3_Scenario1
Scenario: Verification of entering credentials in login page 

Given Open the Chrome and launch the application			

When Enter the Username	and password using datatable
| username | password |
| username40 | password40 |
| username41 | password41 |
| username42 | password42 |

Then Reset the credential

And Verify that both username and password is reset


@ExtentSetup
Scenario: Flush Extent Report
Given Flush the extent report to be used for this feature

