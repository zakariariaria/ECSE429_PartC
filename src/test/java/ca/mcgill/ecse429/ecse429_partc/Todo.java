package ca.mcgill.ecse429.ecse429_partc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.simple.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class Todo {
	
	private String id;
	
	@Test
	public void createTodo() throws Exception{
		String title = "placeholder";
		boolean doneStatus = false;
		String description = "";
		
		JSONObject object = new JSONObject();
		object.put("title",title);
		object.put("doneStatus", doneStatus);
		object.put("description", description);
		
		Response response = given()
							.contentType(ContentType.JSON)
							.body(object.toJSONString())
							.when()
							.post("http://localhost:4567/todos");
		JsonPath jsonResponse = response.jsonPath();
		this.id = jsonResponse.get("id");
		assertEquals(title,jsonResponse.get("title"));
		assertEquals(doneStatus,Boolean.parseBoolean(jsonResponse.get("doneStatus").toString()));
		assertEquals(description,jsonResponse.get("description"));
	}
	
	@Test
	public void updateTodo() throws Exception{
		int todoID = Integer.valueOf(this.id); 
		String title = "new title";
		boolean doneStatus = true;
		String description = "description";
		
		JSONObject object = new JSONObject();
		object.put("title",title);
		object.put("doneStatus", doneStatus);
		object.put("description", description);
		
		Response response = given()
							.contentType(ContentType.JSON)
							.body(object.toJSONString())
							.when()
							.post("http://localhost:4567/todos/"+todoID);
		
		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title,jsonResponse.get("title"));
		assertEquals(doneStatus,Boolean.parseBoolean(jsonResponse.get("doneStatus").toString()));
		assertEquals(description,jsonResponse.get("description"));
	}
	
	@Test
	public void deleteTodo() throws Exception{
		int todoID = Integer.valueOf(this.id); 
		
		Response response = given()
							.delete("http://localhost:4567/todos/"+todoID);
		
		assertEquals(200,response.getStatusCode()); 
	}

}
