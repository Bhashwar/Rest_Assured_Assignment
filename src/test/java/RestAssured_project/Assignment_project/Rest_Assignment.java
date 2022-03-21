package RestAssured_project.Assignment_project;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Pojo.pojoclass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Rest_Assignment {

      @Test
      public void CreateUser(ITestContext val) throws JsonProcessingException {
    	  
    	  RestAssured.baseURI="https://petstore.swagger.io/v2";
    	  
    	  pojoclass obj = new pojoclass();
    	  
    	  obj.setEmail("abc@gmai.com");
    	  obj.setFirstname("Sadhguru");
    	  obj.setLastname("OP");
    	  obj.setPhone("9876543210");
    	  obj.setPassword("@123");
    	  obj.setUsername("SadhguruOP");
    	  obj.setUserstatus("0");
    	  
    	  ObjectMapper objmapper = new ObjectMapper();
    	  
    	  System.out.println(objmapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
     
    	  given()
			.contentType(ContentType.JSON)
			.body(objmapper.writeValueAsString(obj)).
		  when()
			.post("/user").
		  then()
			.statusCode(200)
			.log()
			.all();
      
          String usr = obj.getUsername();
          val.setAttribute("Username", usr);
          System.out.println(usr);
          
          String pass = obj.getPassword();
          val.setAttribute("Password", pass);
          System.out.println(pass);
          
      }
      
    
    @Test(dependsOnMethods = "CreateUser")
      public void Moduser(ITestContext val) {
      
    	  RestAssured.baseURI="https://petstore.swagger.io/v2";
    	  
    	  JSONObject details = new JSONObject();
    	  pojoclass obj = new pojoclass();
    	  
    	  details.put("Email", obj.getEmail());
    	  details.put("Firstname",obj.getFirstname());
    	  details.put("Lastname",obj.getLastname());
    	  details.put("Phone",obj.getLastname());
    	  details.put("Password",obj.getPassword());
    	  details.put("Username",obj.getUsername());
      
    	  given()
			.contentType(ContentType.JSON)
			.body(details.toJSONString()).
		  when()
			.put("/user" + val.getAttribute("Username").toString()).
		  then()
			.statusCode(200)
			.log()
			.all();
    	
    	
      
      }
      
      @Test(dependsOnMethods="Moduser")
      public void login(ITestContext val)  {
	
    	  RestAssured.baseURI="https://petstore.swagger.io/v2";
    	  
    	  given()
    	  	.queryParam("Username", val.getAttribute("Username"))
    	  	.get("/user/login").
    	  then()
    	  	.statusCode(200)
    	  	.log()
    	  	.all();
    	  	


      }
      
      

      @Test(dependsOnMethods="login")
      public void logout(ITestContext val)  {
	
    	  RestAssured.baseURI="https://petstore.swagger.io/v2";
    	  
    	  given()
    	  	.get("/user/login").
    	  then()
    	  	.statusCode(200)
    	  	.log()
    	  	.all();
    	  	
      
      
      }
      

      @Test(dependsOnMethods="logout")
      public void Delete(ITestContext val)  {
	
    	  RestAssured.baseURI="https://petstore.swagger.io/v2";
    	  
    	  String usrname = val.getAttribute("Username").toString();
    	  
    	  given()
    	  	.delete("/user/" + usrname).
    	  	
    	  then()
    	  	.statusCode(202)
    	  	.log()
    	  	.all();
    	  	

      }





}
