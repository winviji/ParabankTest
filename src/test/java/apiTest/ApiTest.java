package apiTest;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;


public class ApiTest {



	public String savingsAccount;

	public String checkingAccount;
	
	public String userAccountID = "13344";

	//public String credentials = "john:demo";

	@Test(priority=1)

	public void testCreateCheckingAccount() {
		
		Reporter.log("Test Create new Checking Account");
		//RestAssured.baseURI="https://parabank.parasoft.com";

		RequestSpecification request = Endpoints.createRequest();
		
		Response response = Endpoints.createAccount(request, "0", userAccountID);
		int statusCode =response.getStatusCode();
		System.out.println("Response code = "+statusCode);
		Reporter.log("Status code of response: " +statusCode);
		Assert.assertEquals(statusCode, 200,"Error: Status code is"+statusCode);

		response.prettyPrint();
		String type = Utility.getJsonString(response, "type");
		
		checkingAccount = Utility.getJsonString(response, "id");	
		Reporter.log("Created Account with ID "+checkingAccount);
		Reporter.log("Account type of created account "+type);
		Assert.assertEquals(type,"CHECKING","Error: Account type incorrect");

		//TODO: This will fail, uncomment later 
		Assert.assertEquals(Utility.getJsonString(response, "balance"),"100", "Balance is incorrect");

	}

	@Test(priority=2)

	public void testCreateSavingsAccount() {
		Reporter.log("Test Create new Savings Account");

		RequestSpecification request = Endpoints.createRequest();


		Response response = Endpoints.createAccount(request, "1", userAccountID);
		int statusCode =response.getStatusCode();
		System.out.println("Response code = "+statusCode);
		Reporter.log("Status code of response: " +statusCode);


		Assert.assertEquals(statusCode, 200);
		response.prettyPrint();
		String type = Utility.getJsonString(response, "type");

		Assert.assertEquals(Utility.getJsonString(response, "type"),"SAVINGS");

		savingsAccount = Utility.getJsonString(response, "id");
		Reporter.log("Created Account with ID "+savingsAccount);
		Reporter.log("Account type of created account "+type);

		Assert.assertEquals(type,"SAVINGS","Error: Account type incorrect");

		//TODO: This will fail, uncomment later 
		Assert.assertEquals(Utility.getJsonString(response, "balance"),"100", "Balance is incorrect");

	}

	@Test(priority=5)

	public void testCreateAccountWithIncorrectTypeCode() {
		
		Reporter.log("Create Account with Inccorrect type code");
		RequestSpecification request = Endpoints.createRequest();
		Response response = Endpoints.createAccount(request, "5", userAccountID);
		int statusCode =response.getStatusCode();
		System.out.println("Response code = "+statusCode);
		Reporter.log("Response code :"+statusCode);
		Assert.assertNotEquals(statusCode, 200);
		//response.prettyPrint();
	}


	@Test(priority=4)

	public void testCreateAccountWithIncorrectFromAccount() {
		RequestSpecification request = Endpoints.createRequest();
		Response response = Endpoints.createAccount(request, "1", "223");
		int statusCode =response.getStatusCode();
		System.out.println("Response code = "+statusCode);

		Assert.assertNotEquals(statusCode, 200);
		//	response.prettyPrint();

	}


	/*
	 * Test bill payment of $200.00 from savings account created above to checking account
	 * */
	@Test(priority=3)

	public void testBillPayment() {
		SoftAssert sa = new SoftAssert();

		try {

			Reporter.log("Test Bill payment from savings account "+savingsAccount+" to checking account "+checkingAccount);
			RequestSpecification request = Endpoints.createRequest();
			
			String payload = Utility.createBillPayPayload("Street1", "BLR", "KA", "432334", "Lana", "8899328921",checkingAccount);
			Reporter.log("Create a payload with payee details: "+payload);
			Response response = Endpoints.billPay(request, savingsAccount, "200", payload);


			int statusCode =response.getStatusCode();
			System.out.println(" Bill payment status : Response code = "+statusCode);
			Reporter.log(" Bill payment status : Response code = "+statusCode);
			Assert.assertEquals(statusCode,200,"Error: Bill payment failed with incorrect status code "+statusCode );

			//Get the account details
			try 
			{
				Reporter.log("Verify the debit account details and debit transaction");
				System.out.println("Verify the debit account details. Verify debit transaction");

				Reporter.log("Get the account details for account: "+savingsAccount);
				System.out.println("Get the account details for account: "+savingsAccount);
				Response debitAccountResponse = Endpoints.getAccountDetails(request, savingsAccount);
				

				int accountResponseCode = debitAccountResponse.getStatusCode();
				Reporter.log(debitAccountResponse.prettyPrint());
				debitAccountResponse.prettyPrint();
				
				String savingsAccountBalance = Utility.getJsonString(debitAccountResponse, "balance");

				Response transactionResponse = Endpoints.getTransaction(request, savingsAccount);
				Reporter.log("Get the debit transaction ");
				System.out.println("Get the debit transaction ");
				Reporter.log(transactionResponse.prettyPrint());
				transactionResponse.prettyPrint();
				String amount = Utility.getJsonString(transactionResponse, "amount[1]");

				String description = Utility.getJsonString(transactionResponse,"description[1]");
				String date = Utility.getJsonString(transactionResponse,"date[1]");
				Timestamp ts = new Timestamp(Long.parseLong(date));

				Date transactionDate = new Date(ts.getTime());
				String transactionType = Utility.getJsonString(transactionResponse,"type[1]");

				System.out.println("Date is "+transactionDate);
				//transactionDate.
				System.out.println("Amount debited = "+amount);
				System.out.println("Transaction description = "+description);

				System.out.println("Transaction type = "+transactionType);

				System.out.println("Account balance for "+savingsAccount+" "+Utility.getJsonString(debitAccountResponse, "balance"));

				Reporter.log("Verify debit amount equqals 200.0");
				//Verify debit amount
				sa.assertEquals(amount, "200.0", "Debit amount is incorrect "+amount);
				//Verify debit transaction details
				Reporter.log("Verify debit transaction description");
				sa.assertEquals(description, "Bill Payment to Lana","Error: Transaction details incorrect");
				Reporter.log("Verify transaction type");
				sa.assertEquals(transactionType, "Debit","Transaction type is incorrect");
				Reporter.log("Verify Debit account balance");

				sa.assertEquals(savingsAccountBalance, "-100.00", "Error: Account balance is incorrect");
			}
			catch (AssertionError e) {
				System.out.println("Error in verifying debit account transaction "+ e.getMessage());
				Reporter.log("Assertion Error in verifying debit account transaction "+e.getMessage());
				throw new AssertionError(e);

			}
			catch (NumberFormatException e) {

				System.out.println("Error in verifying debit account transaction "+ e.getMessage());
				Reporter.log("NumberFormatException Error in verifying debit accoutn transaction "+e.getMessage());
				throw new NumberFormatException();

			}

			catch (NullPointerException e) {
				System.out.println("Error in verifying debit account transaction "+ e.getMessage());
				Reporter.log("NullPointerException Error in verifying debit accoutn transaction "+e.getMessage());
				throw new NullPointerException();

			}
			catch (Exception e) {
				System.out.println("Error in verifying debit account transaction "+ e.getMessage());
				Reporter.log(" Error in verifying debit account transaction "+e.getMessage());
				throw new Exception(e);

			}
			try 
			{
				Reporter.log("Verify the credit account details and credit transaction");
				System.out.println("Verify the credit account details. Verify credit transaction");

				Reporter.log("Get the account details for account: "+checkingAccount);

				Response creditAccountResponse = Endpoints.getAccountDetails(request, checkingAccount);


				int accountResponseCode2 = creditAccountResponse.getStatusCode();
				String checkingAccountBalance = Utility.getJsonString(creditAccountResponse, "balance");

				Reporter.log(creditAccountResponse.prettyPrint());
				creditAccountResponse.prettyPrint();

				System.out.println("Verify transactions for checking account, verify credit ");
				
				Reporter.log("Get the credit transaction ");
				Response transactionResponse1 = Endpoints.getTransaction(request, checkingAccountBalance);
				System.out.println("Get the credit transaction ");
				Reporter.log(transactionResponse1.prettyPrint());
				transactionResponse1.prettyPrint();
				String amount1 = Utility.getJsonString(transactionResponse1,"amount[1]");

				String description1 = Utility.getJsonString(transactionResponse1,"description[1]");

				String date1 = Utility.getJsonString(transactionResponse1, "date[1]");
				Timestamp ts1 = new Timestamp(Long.parseLong(date1));

				Date transactionDate1 = new Date(ts1.getTime());

				System.out.println("Date is "+transactionDate1);
				//transactionDate.
				System.out.println("Amount credited = "+amount1);
				System.out.println("Transaction description = "+description1);

				String transactionType1 = Utility.getJsonString(transactionResponse1,"type[1]");
				System.out.println("Transaction type = "+transactionType1);


				System.out.println("Account balance for "+checkingAccount+" "+Utility.getJsonString(creditAccountResponse, "balance"));//   accountResponse2.getBody().jsonPath().getString("balance"));


				//Verify the credit account
				Reporter.log("Verify credit amount is 200");
				sa.assertEquals(amount1, "200.0", "Credit amount is incorrect");
				//Verify debit transaction details
				Reporter.log("Verify transaction description");
				sa.assertEquals(description1, "Bill Payment to Lana","Transaction details incorrect");
				Reporter.log("Verify transaction type");
				sa.assertEquals(transactionType1, "Credit","Transaction type is incorrect");
				Reporter.log("Verify account balance after credit");
				sa.assertEquals(checkingAccountBalance, "300.00");
				sa.assertAll();
			}

			catch (AssertionError e) {
				System.out.println("Error in verifying credit account transaction "+ e.getMessage());
				Reporter.log("Assertion Error in verifying credit account transaction "+e.getMessage());
				throw new AssertionError(e);

			}
			catch (NumberFormatException e) {

				System.out.println("Error in verifying credit account transaction "+ e.getMessage());
				Reporter.log("NumberFormatException Error in verifying credit accoutn transaction "+e.getMessage());
				throw new NumberFormatException();

			}

			catch (NullPointerException e) {
				System.out.println("Error in verifying credit account transaction "+ e.getMessage());
				Reporter.log("NullPointerException Error in verifying credit accoutn transaction "+e.getMessage());
				throw new NullPointerException();

			}
			catch (Exception e) {
				System.out.println("Error in verifying credit account transaction "+ e.getMessage());
				Reporter.log(" Error in verifying credit accoun transaction "+e.getMessage());
				throw new Exception(e);

			}

		} 
		catch(AssertionError e) {
			Reporter.log("Error in Bill payment: " +e.getMessage());

			sa.assertAll();

			Assert.fail(e.getMessage());
		}

		catch (NumberFormatException e) {
			Reporter.log("Error in Bill payment: " +e.getMessage());

			sa.assertAll();

			Assert.fail(e.getMessage());	

		}

		catch (NullPointerException e) {
			Reporter.log("Error in Bill payment: " +e.getMessage());

			sa.assertAll();

			Assert.fail(e.getMessage());

		}
		catch (Exception e) {
			Reporter.log("Error in Bill payment: " +e.getMessage());
			sa.assertAll();
			Assert.fail(e.getMessage());

		}


	}

}
