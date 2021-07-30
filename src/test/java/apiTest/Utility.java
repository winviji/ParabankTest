package apiTest;

import io.restassured.response.Response;

public class Utility {

	public static String getJsonString(Response response , String variable){
		return response.body().jsonPath().getString(variable);
	}
	
	
	public static String createBillPayPayload(String street, String city, String state, String zipCode, String name, String phoneNo, String accountNumber) {
	
		String payload = "{\n"
				+ "    \"address\":{\n"
				+ "        \"street\":\""+street+"\",\n"
				+ "        \"city\":\""+city+"\",\n"
				+ "        \"state\":\""+state+"\",\n"
				+ "        \"zipCode\":\"+"+zipCode+"\"},\n"
				+ "\n"
				+ "        \"name\":\""+name+"\",\n"
				+ "        \"phoneNumber\":\""+phoneNo+"\",\n"
				+ "        \"accountNumber\":\""+accountNumber+"\"\n"
				+ "}"; 
		return payload;
		
	}
}
