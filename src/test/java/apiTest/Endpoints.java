package apiTest;

import org.apache.commons.codec.binary.Base64;
import org.testng.Reporter;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//Endpoints class for handling api interaction
public class Endpoints {
	
	private static final String BASE_URL="https://parabank.parasoft.com";
	
	//Create new request object with authorization
	public static RequestSpecification createRequest() {
		 String credentials = "john:demo";

		RestAssured.baseURI=BASE_URL;
		RequestSpecification request = RestAssured.given();


		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());

		String encodedCredAsString = new String (encodedCredentials);

		request.header("Authorization","Basic"+encodedCredAsString);

		request.header("Content-Type","application/json");

		return request;
	}
	
	//Create  a new account with given parameters and return the response code
	//Customer id is hardcoded as we are using a sigle user account across the test
	public static Response createAccount(RequestSpecification request,String accountType, String fromAccountId) {
		Reporter.log("createAccount Method: Create new account with type "+accountType);
		return request.queryParam("customerId", "12212").queryParam("newAccountType",accountType).queryParam("fromAccountId", fromAccountId).post("/parabank/services_proxy/bank/createAccount");
	}
	
	//Bill payment 
	public static Response billPay(RequestSpecification request,String fromAccount, String amount,String payload) {
		return request.queryParam("accountId", fromAccount).queryParam("amount", amount).body(payload).post("/parabank/services_proxy/bank/billpay");
	}	
	//Get account details of given account ID. Return response
	public static Response getAccountDetails(RequestSpecification request, String accountId) {
		return request.get("/parabank/services_proxy/bank/accounts/"+accountId);
	}
	//Get transaction details of given account ID. Return response

	public static Response getTransaction(RequestSpecification request, String accountId) {
		return request.get("/parabank/services_proxy/bank/accounts/"+accountId+"/transactions");
	}
	
	public static Response getAccounts(RequestSpecification request,String customerID)
	{
		Reporter.log("Get list of available accounts to choose from for the current user:");
				
		return request.get("https://parabank.parasoft.com/parabank/services_proxy/bank/customers/"+customerID+"/accounts");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
		
		
	
}
