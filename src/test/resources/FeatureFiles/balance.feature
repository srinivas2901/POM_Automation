Feature: Total Balance Calculation

#Demo test case for reference

@testRun1 @Regression
Scenario Outline: Validate the right count of values appear on the screen are greater than 0 and the values are formatted as currencies

	Given the user should be able to navigate to the application
#	And On Login page, enter the username '<username>'
#	And On Login page, enter the password <password>'
#	And On Login page, click on the login button
#	And The user is in the values screen 
	Then Validate the right count of values appear on the screen are greater than '<value>' and the values are formatted as '<currencies>'
	
	Examples:
	|username|password|value|currencies|
	|USER    |PASSWORD|0    |$		   |
	
	

	
@testRun3 @Regression
Scenario Outline: Validate the total balance matches the sum of the values and is correct based on the values listed on the screen

	Given the user should be able to navigate to the application
#	And On Login page, enter the '<username>'
#	And On Login page, enter the '<password>'
#	And On Login page, click on the login button
#	And The user is in the values screen
	Then Validate the total balance matches the sum of the '<totalBalance>' values in the screen
	
	Examples:
	|username|password|totalBalance|
	|USER    |PASSWORD|1000000.00  |
	