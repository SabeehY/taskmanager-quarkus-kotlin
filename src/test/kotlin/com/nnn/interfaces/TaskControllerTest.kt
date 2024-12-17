package com.nnn.interfaces

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import com.nnn.interfaces.dto.CreateTaskRequest
import com.nnn.interfaces.dto.UpdateTaskRequest
import org.bson.types.ObjectId

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TaskControllerTest {

    companion object {
      private var createdTaskId: String? = null
    }

    @Test
    @Order(1)
    fun `test create task`() {
        val request = CreateTaskRequest(title = "Test Task")
        
        val response = given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .post("/tasks")
            .then()
            .statusCode(200)
            .body("title", `is`("Test Task"))
            .body("isCompleted", `is`(false))
            .body("id", notNullValue())
            .extract()
            .path<String>("id")

        createdTaskId = response
        println("Created Task ID: $createdTaskId") // Add this debug line
    }

    @Test
    @Order(2)
    fun `test get all tasks`() {
        given()
            .`when`()
            .get("/tasks")
            .then()
            .statusCode(200)
            .body("size()", `is`(1))
            .body("[0].title", `is`("Test Task"))
            .body("[0].isCompleted", `is`(false))
    }

    @Test
    @Order(3)
    fun `test get task by id`() {
          // Ensure the ID is valid
        // if (createdTaskId == null || !ObjectId.isValid(createdTaskId)) {
        //     throw IllegalStateException("Invalid or null task ID")
        // }
        println("Using Task ID in test 3: $createdTaskId")
        requireNotNull(createdTaskId) { "Task ID should not be null" }

        given()
            .`when`()
            .get("/tasks/{id}", createdTaskId)
            .then()
            .statusCode(200)
            .body("title", `is`("Test Task"))
            .body("isCompleted", `is`(false))
    }

    @Test
    @Order(4)
    fun `test update task`() {
        val request = UpdateTaskRequest(
            title = "Updated Task",
            isCompleted = true
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .put("/tasks/{id}", createdTaskId)
            .then()
            .statusCode(200)
            .body("title", `is`("Updated Task"))
            .body("isCompleted", `is`(true))
    }

    // @Test
    // @Order(5)
    fun `test delete task`() {
        given()
            .`when`()
            .delete("/tasks/{id}", createdTaskId)
            .then()
            .statusCode(204)

        // Verify task is deleted
        given()
            .`when`()
            .get("/tasks/{id}", createdTaskId)
            .then()
            .statusCode(404)
    }

    // @Test
    fun `test get task with invalid id returns 404`() {
        given()
            .`when`()
            .get("/tasks/{id}", ObjectId())
            .then()
            .statusCode(404)
    }

    // @Test
    fun `test update task with invalid id returns 404`() {
        val request = UpdateTaskRequest(
            title = "Updated Task",
            isCompleted = true
        )

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .`when`()
            .put("/tasks/{id}", ObjectId())
            .then()
            .statusCode(404)
    }
}
