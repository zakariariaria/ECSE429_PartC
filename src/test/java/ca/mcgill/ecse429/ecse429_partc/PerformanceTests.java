package ca.mcgill.ecse429.ecse429_partc;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PerformanceTests {
	
	private static int numberObjectsInitial = 5;
	private static int numberTests = 10;

	public static void main(String args[]) throws Exception{
		
		
		for(int i=0;i<numberObjectsInitial;i++) { // Populate the API with objects
			long start,end;
			start = System.currentTimeMillis();
			createTodo();
			end = System.currentTimeMillis();
			System.out.println("Number of existing todo items : "+(i+1)+" | Time taken to create a new todo item (in ms) : "+(end-start));
		}
		System.out.println("========================== Total number of todo items : "+numberObjectsInitial+" ==========================");
		for(int i=0;i<numberTests;i++) {
			long start;
			long end;
			
			start = System.currentTimeMillis();
			String id = createTodo();
			end = System.currentTimeMillis();
			System.out.println("Time taken to create a new todo item (in ms) : "+(end-start));
			start = System.currentTimeMillis();
			updateTodo(id);
			end = System.currentTimeMillis();
			System.out.println("Time taken to update the new todo item (in ms) : "+(end-start));
			start = System.currentTimeMillis();
			deleteTodo(id);
			end = System.currentTimeMillis();
			System.out.println("Time taken to delete the new todo item (in ms) : "+(end-start));
		}
		
	}
	
	
	
	
	// Todo - APIs to create, delete and update
	@Test
	public static String createTodo() throws Exception{
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
		assertEquals(title,jsonResponse.get("title"));
		assertEquals(doneStatus,Boolean.parseBoolean(jsonResponse.get("doneStatus").toString()));
		assertEquals(description,jsonResponse.get("description"));
		return jsonResponse.get("id");
	}
	
	@Test
	public static void updateTodo(String id) throws Exception{
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
							.post("http://localhost:4567/todos/"+Integer.valueOf(id));
		
		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title,jsonResponse.get("title"));
		assertEquals(doneStatus,Boolean.parseBoolean(jsonResponse.get("doneStatus").toString()));
		assertEquals(description,jsonResponse.get("description"));
	}
	
	@Test
	public static void deleteTodo(String id) throws Exception{		
		Response response = given()
							.delete("http://localhost:4567/todos/"+Integer.valueOf(id));
		
		assertEquals(200,response.getStatusCode()); 
	}
}
