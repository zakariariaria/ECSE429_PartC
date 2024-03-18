package ca.mcgill.ecse429.ecse429_partc;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;

public class PerformanceTests {
	
	private static int numberObjectsInitial = 5;
	private static int numberTests = 10;

	public static void main(String args[]) throws Exception{
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		
		
		for(int i=0;i<numberObjectsInitial;i++) { // Populate the API with objects
			long startTime,endTime,startMemory,endMemory,startCPU,endCPU;
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createTodo();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("Number of existing todo items : "+(i+1)+" | Time taken to create a new todo item (in ms) : "+(endTime-startTime));
		}
		System.out.println("========================== Total number of todo items : "+numberObjectsInitial+" ==========================");
		for(int i=0;i<numberTests;i++) {
			long startTime,endTime,startMemory,endMemory,startCPU,endCPU;
			
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			String id = createTodo();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for creating a new todo item ==========================");
			System.out.println("Time taken (in ms) : "+(endTime-startTime)+" | Memory used (in B) : "+(endMemory-startMemory)/1024+" | CPU used (in ns) :  "+(endCPU-startCPU)/1e6);
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			updateTodo(id);
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for updating the todo item ==========================");
			System.out.println("Time taken (in ms) : "+(endTime-startTime)+" | Memory used (in B) : "+(endMemory-startMemory)/1024+" | CPU used (in ns) :  "+(endCPU-startCPU)/1e6);
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			deleteTodo(id);
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for deleting the todo item ==========================");
			System.out.println("Time taken (in ms) : "+(endTime-startTime)+" | Memory used (in B) : "+(endMemory-startMemory)/1024+" | CPU used (in ns) :  "+(endCPU-startCPU)/1e6);
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
