package apiTest;

import org.apache.commons.codec.binary.Base64;
import org.testng.Reporter;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
	
	private static final String BASE_URL="https://parabank.parasoft.com";
	
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
	
	public static Response createAccount(RequestSpecification request,String accountType, String fromAccountId) {
		Reporter.log("createAccount Method: Create new account with type "+accountType);
		return request.queryParam("customerId", "12212").queryParam("newAccountType",accountType).queryParam("fromAccountId", fromAccountId).post("/parabank/services_proxy/bank/createAccount");
	}
	
	public static Response billPay(RequestSpecification request,String fromAccount, String amount,String payload) {
		return request.queryParam("accountId", fromAccount).queryParam("amount", amount).body(payload).post("/parabank/services_proxy/bank/billpay");
	}	
	public static Response getAccountDetails(RequestSpecification request, String accountId) {
		return request.get("/parabank/services_proxy/bank/accounts/"+accountId);
	}
	
	public static Response getTransaction(RequestSpecification request, String accountId) {
		return request.get("/parabank/services_proxy/bank/accounts/"+accountId+"/transactions");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
		
		
	
}
