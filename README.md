# ParabankTest
This project contains tests to verify the functionality in https://parabank.parasoft.com/parabank/index.htm
The Project is a maven project contain automated tests written in Selenium Webdriver using Java as the programming language, TestNG Framework.
Page Object model is used to design the tests.
Folder Structure:
The tests are placed under the below directory structure src/test/java/tests.
The main test file is TestCreateNewAccount.java containing methods for each testcase.
Page classes for each page file being tested in placed under src/test/java/pageObjects.
A Helper class ActionsHelper with utility functions and methods for browser ui interaction is placed under src/test/java/helperClasses.
A listerner class ScreenshotListener is added to capture screenshots after test failure and add to the report. This can be found under src/test/java/resources.

Running the tests at the local machine:

Local environment needs maven to run the code.

Steps to create the project :
Clone the repository to your local machine From your Java IDE.
Select create a new maven project using existing pom.xml file .
Select the pom.xml downloaded from the remote repository.

All requied libraries will be downloaded by maven.

Use the "mvn test" command to run the test from commandline .

TEST report
The test reports will be found under //target/surefire-reports.
The screenshots for all failures will be placed under //targer//failure_screenshots. All screenshots are linked to the tests that are failed
