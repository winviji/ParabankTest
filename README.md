# ParabankTest 

# APITEST
This project contains tests to verify the api tests for Parabank Application at https://parabank.parasoft.com/parabank/index.htm .
The Project is a maven project contain automated tests written using Rest assured, Java as the programming language, TestNG Framework.

Folder Structure:
The tests are placed under the below directory structure src/test/java/apiTest.
The main test file is ApiTest.java containing methods for each testcase.
A Utility calss with functions and methods for arsing and creating data strings is also placed here.
A Endpoints class with methods for  handling api interaction is also added.

Running the tests at the local machine:

Local environment needs maven to run the code.

Steps to create the project :
Clone the repository to your local machine From your Java IDE.
Select create a new maven project using existing pom.xml file .
Select the pom.xml downloaded from the remote repository.

All requied libraries will be downloaded by maven.

Use the "mvn test" command to run the test from commandline .

TEST report:
The test reports will be found under //target/surefire-reports.

