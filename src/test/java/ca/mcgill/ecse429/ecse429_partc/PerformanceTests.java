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

	public static int categoryID;
	public static int projectID;
	public static int categoryID2;
	public static int todoID;
	public static int todoID2;
	public static int projectID2;

	public static void main(String args[]) throws Exception {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();


		for (int i = 0; i < numberObjectsInitial; i++) { // Populate the API with objects
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createTodo();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("Number of existing todo items : " + (i + 1) + " | Time taken to create a new todo item (in ms) : " + (endTime - startTime));
		}
		System.out.println("========================== Total number of todo items : " + numberObjectsInitial + " ==========================");
		for (int i = 0; i < numberTests; i++) {
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			String id = createTodo();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for creating a new todo item ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			updateTodo(id);
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for updating the todo item ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			deleteTodo(id);
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for deleting the todo item ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
		}
		System.out.println("===============Todo tests finished=============");
		for (int i = 0; i < numberObjectsInitial; i++) { // Populate the API with objects
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createCategories();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("Number of existing categories : " + (i + 1) + " | Time taken to create a new category (in ms) : " + (endTime - startTime));
		}
		for (int i = 0; i < numberTests; i++) {
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			String id = createCategories();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for creating a new category ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			updateCategories(id);
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for updating the category ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			deleteCategories(id);
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for deleting the category ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
		}
		System.out.println("===============Category tests finished=============");
		for (int i = 0; i < numberObjectsInitial; i++) { // Populate the API with objects
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createInteroperabilityProjectCategory();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("Number of existing interoperabilities project/category : " + (i + 1) + " | Time taken to create a new interoperability project/category (in ms) : " + (endTime - startTime));
		}
		for (int i = 0; i < numberTests; i++) {
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createInteroperabilityProjectCategory();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for creating a new interoperability project/category ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			deleteInteroperabilityProjectCategory();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for deleting the interoperability project/category ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
		}
		System.out.println("===============Interoperability Project/Category tests finished=============");
		for (int i = 0; i < numberObjectsInitial; i++) { // Populate the API with objects
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createInteroperabilityCategoryTodo();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("Number of existing interoperabilities category/todo : " + (i + 1) + " | Time taken to create a new interoperability category/todo (in ms) : " + (endTime - startTime));
		}
		for (int i = 0; i < numberTests; i++) {
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createInteroperabilityCategoryTodo();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for creating a new interoperability category/todo ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			deleteInteroperabilityCategoryTodo();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for deleting the interoperability category/todo ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
		}
		System.out.println("===============Interoperability Category/Todo tests finished=============");
		for (int i = 0; i < numberObjectsInitial; i++) { // Populate the API with objects
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;
			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createInteroperabilityTodoProject();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("Number of existing interoperabilities todo/project : " + (i + 1) + " | Time taken to create a new interoperability todo/project (in ms) : " + (endTime - startTime));
		}
		for (int i = 0; i < numberTests; i++) {
			long startTime, endTime, startMemory, endMemory, startCPU, endCPU;

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			createInteroperabilityTodoProject();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for creating a new interoperability todo/project ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);

			startTime = System.currentTimeMillis();
			startMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			startCPU = threadMXBean.getCurrentThreadCpuTime();
			deleteInteroperabilityTodoProject();
			endTime = System.currentTimeMillis();
			endMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
			endCPU = threadMXBean.getCurrentThreadCpuTime();
			System.out.println("========================== Performance test for deleting the interoperability todo/project ==========================");
			System.out.println("Time taken (in ms) : " + (endTime - startTime) + " | Memory used (in B) : " + (endMemory - startMemory) / 1024 + " | CPU used (in ns) :  " + (endCPU - startCPU) / 1e6);
		}
		System.out.println("===============Interoperability Todo/Project tests finished=============");
	}


	// Todo - APIs to create, delete and update
	@Test
	public static String createTodo() throws Exception {
		String title = "placeholder";
		boolean doneStatus = false;
		String description = "";

		JSONObject object = new JSONObject();
		object.put("title", title);
		object.put("doneStatus", doneStatus);
		object.put("description", description);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(object.toJSONString())
				.when()
				.post("http://localhost:4567/todos");
		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title, jsonResponse.get("title"));
		assertEquals(doneStatus, Boolean.parseBoolean(jsonResponse.get("doneStatus").toString()));
		assertEquals(description, jsonResponse.get("description"));
		return jsonResponse.get("id");
	}

	@Test
	public static void updateTodo(String id) throws Exception {
		String title = "new title";
		boolean doneStatus = true;
		String description = "description";

		JSONObject object = new JSONObject();
		object.put("title", title);
		object.put("doneStatus", doneStatus);
		object.put("description", description);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(object.toJSONString())
				.when()
				.post("http://localhost:4567/todos/" + Integer.valueOf(id));

		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title, jsonResponse.get("title"));
		assertEquals(doneStatus, Boolean.parseBoolean(jsonResponse.get("doneStatus").toString()));
		assertEquals(description, jsonResponse.get("description"));
	}

	@Test
	public static void deleteTodo(String id) throws Exception {
		Response response = given()
				.delete("http://localhost:4567/todos/" + Integer.valueOf(id));

		assertEquals(200, response.getStatusCode());
	}



	
	// Project - APIs to create, delete and update
	@Test
	public static String createProject() throws Exception {
		int idProj = 3;
		String title = "placeholder";
		boolean completed = true;
		boolean activeStatus = false;
		String description = "123 Viva L'algiré";

		//JSON Payload creation
		JSONObject object = new JSONObject();
		object.put("id", idProj);
		object.put("title", title);
		object.put("completed", completed);
		object.put("active", activeStatus);
		object.put("description", description);

		//API Calls to create the project
		Response response = given()
				.contentType(ContentType.JSON)
				.body(object.toJSONString())
				.when()
				.post("http://localhost:4567/projects");
		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title, jsonResponse.get("title"));
		assertEquals(completed, Boolean.parseBoolean(jsonResponse.get("completed").toString()));
		assertEquals(description, jsonResponse.get("description"));
		return jsonResponse.get("id");
	}

	@Test
	public static void updateProject(String idProj) throws Exception {
		String title = "placeholder";
		boolean completed = false;
		boolean activeStatus = true;
		String description = "-----UpdateDescription-----";

		JSONObject object = new JSONObject();
		object.put("title", title);
		object.put("completed", completed);
		object.put("active", activeStatus);
		object.put("description", description);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(object.toJSONString())
				.when()
				.post("http://localhost:4567/projects/" + Integer.valueOf(idProj));

		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title, jsonResponse.get("title"));
		assertEquals(completed, Boolean.parseBoolean(jsonResponse.get("completed").toString()));
		assertEquals(description, jsonResponse.get("description"));
	}

	@Test
	public static void deleteProject(String idProj) throws Exception {
		Response response = given()
				.delete("http://localhost:4567/project/" + Integer.valueOf(idProj));

		assertEquals(200, response.getStatusCode());
	}






	// Categories - APIs
	@Test
	public static String createCategories() throws Exception {
		String title = "placeholder";
		String description = "";

		JSONObject object = new JSONObject();
		object.put("title", title);
		object.put("description", description);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(object.toJSONString())
				.when()
				.post("http://localhost:4567/categories");
		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title, jsonResponse.get("title"));
		assertEquals(description, jsonResponse.get("description"));
		return jsonResponse.get("id");
	}
	@Test
	public static void updateCategories(String id) throws Exception {
		String title = "new title";
		String description = "description";

		JSONObject object = new JSONObject();
		object.put("title", title);
		object.put("description", description);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(object.toJSONString())
				.when()
				.post("http://localhost:4567/categories/" + Integer.valueOf(id));

		JsonPath jsonResponse = response.jsonPath();
		assertEquals(title, jsonResponse.get("title"));
		assertEquals(description, jsonResponse.get("description"));
	}
	@Test
	public static void deleteCategories(String id) throws Exception {
		Response response = given()
				.delete("http://localhost:4567/categories/" + Integer.valueOf(id));

		assertEquals(200, response.getStatusCode());
	}

	@Test
	public static void createInteroperabilityProjectCategory() throws Exception {
		String projectTitle = "projectTitle";
		String projectDescription = "projectDescription";

		JSONObject projectObject = new JSONObject();
		projectObject.put("title", projectTitle);
		projectObject.put("description", projectDescription);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(projectObject.toJSONString())
				.when()
				.post("http://localhost:4567/projects");

		JsonPath jsonResponse = response.jsonPath();
		projectID = jsonResponse.getInt("id");

		assertEquals(projectTitle, jsonResponse.get("title"));
		assertEquals(projectDescription, jsonResponse.get("description"));
		assertEquals(201, response.getStatusCode());

		String categoryTitle = "categoryTitle";
		String categoryDescription = "categoryDescription";
		JSONObject categoryObject = new JSONObject();
		categoryObject.put("title", categoryTitle);
		categoryObject.put("description", categoryDescription);

		Response categoryResponse = given()
				.contentType(ContentType.JSON)
				.body(categoryObject.toJSONString())
				.when()
				.post("http://localhost:4567/projects" + projectID + "/categories");

		JsonPath jsonResponseCategory = categoryResponse.jsonPath();
		categoryID = jsonResponseCategory.getInt("id");

		assertEquals(categoryTitle, jsonResponseCategory.get("title"));
		assertEquals(categoryDescription, jsonResponseCategory.get("description"));
		assertEquals(201, categoryResponse.getStatusCode());
	}

	@Test
	public static void deleteInteroperabilityProjectCategory() throws Exception {
		int currentProjectID = projectID;
		int currentCategoryID = categoryID;

		Response response = given()
				.delete("http://localhost:4567/projects/" + currentProjectID + "/categories/" + currentCategoryID);
		
		assertEquals(200,response.getStatusCode());
	}

	@Test
	public static void createInteroperabilityCategoryTodo() throws Exception {
		String categoryTitle = "categoryTitle";
        String categoryDescription = "categoryDescription";

        JSONObject categoryObject = new JSONObject();
        categoryObject.put("title", categoryTitle);
        categoryObject.put("description", categoryDescription);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(categoryObject.toJSONString())
				.when()
				.post("http://localhost:4567/categories");

		JsonPath jsonResponse = response.jsonPath();
		categoryID2 = jsonResponse.getInt("id");

		assertEquals(categoryTitle, jsonResponse.get("title"));
		assertEquals(categoryDescription, jsonResponse.get("description"));
		assertEquals(201, response.getStatusCode());

		String todoTitle = "todoTitle";
		String todoDescription = "todoDescription";

		JSONObject todoObject = new JSONObject();
		todoObject.put("title", todoTitle);
		todoObject.put("description", todoDescription);

		Response todoResponse = given()
				.contentType(ContentType.JSON)
				.body(todoObject.toJSONString())
				.when()
				.post("http://localhost:4567/categories" + categoryID2 + "/todos");

		JsonPath jsonResponseTodo = todoResponse.jsonPath();
		todoID = jsonResponseTodo.getInt("id");

		assertEquals(categoryTitle, jsonResponseTodo.get("title"));
		assertEquals(categoryDescription, jsonResponseTodo.get("description"));
		assertEquals(201, todoResponse.getStatusCode());
	}

	@Test
	public static void deleteInteroperabilityCategoryTodo() throws Exception {
		int currentCategoryID2 = categoryID2;
		int currentTodoID = todoID;

		Response response = given()
				.delete("http://localhost:4567/categories/" + currentCategoryID2 + "/todos/" + currentTodoID);
		
		assertEquals(200,response.getStatusCode());
	}

	@Test
	public static void createInteroperabilityTodoProject() throws Exception {
		String todoTitle = "todoTitle";
        String todoDescription = "todoDescription";

        JSONObject todoObject = new JSONObject();
        todoObject.put("title", todoTitle);
        todoObject.put("description", todoDescription);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(todoObject.toJSONString())
				.when()
				.post("http://localhost:4567/todos");

		JsonPath jsonResponse = response.jsonPath();
		todoID2 = jsonResponse.getInt("id");

		assertEquals(todoTitle, jsonResponse.get("title"));
		assertEquals(todoDescription, jsonResponse.get("description"));
		assertEquals(201, response.getStatusCode());

		String projectTitle = "projectTitle";
		String projectDescription = "projectDescription";

		JSONObject projectObject = new JSONObject();
		projectObject.put("title", projectTitle);
		projectObject.put("description", projectDescription);

		Response projectResponse = given()
				.contentType(ContentType.JSON)
				.body(projectObject.toJSONString())
				.when()
				.post("http://localhost:4567/todos" + todoID2 + "/projects");

		JsonPath jsonResponseProject = projectResponse.jsonPath();
		projectID2 = jsonResponseProject.getInt("id");

		assertEquals(projectTitle, jsonResponseProject.get("title"));
		assertEquals(projectDescription, jsonResponseProject.get("description"));
		assertEquals(201, projectResponse.getStatusCode());
	}

	@Test
	public static void deleteInteroperabilityTodoProject() throws Exception {
		int currentTodoID2 = todoID2;
		int currentProjectID2 = projectID2;

		Response response = given()
				.delete("http://localhost:4567/todos/" + currentTodoID2 + "/projects/" + currentProjectID2);
		
		assertEquals(200,response.getStatusCode());
	}
}
