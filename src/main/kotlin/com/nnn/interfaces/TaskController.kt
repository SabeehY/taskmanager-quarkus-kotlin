package com.nnn.interfaces

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
// import io.quarkus.cache.CacheResult

import com.nnn.application.TaskService
import com.nnn.domain.Task
import com.nnn.interfaces.dto.TaskDTO
import com.nnn.interfaces.dto.CreateTaskRequest
import com.nnn.interfaces.dto.UpdateTaskRequest

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
class TaskController(private val service: TaskService) {
    @GET
    fun listAll(): Response = Response.ok(service.listAllTasks()).build()

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: String): Response {
        val task = service.findById(id) ?: return Response.status(404).build()
        return Response.ok(task).build()
    }

    @POST
    fun create(request: CreateTaskRequest): Response = Response.ok(service.createTask(request)).build()

    @PUT
    @Path("/{id}")
    fun updateTask(
        @PathParam("id") id: String,
        request: UpdateTaskRequest
    ): Response {
        val updatedTask = service.updateTask(id, request)
        return if (updatedTask != null) {
            Response.ok(updatedTask).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
    }

    @DELETE
    @Path("/{id}")
    fun deleteTask(@PathParam("id") id: String): Response {
        return if (service.deleteTask(id)) Response.noContent().build()
        else Response.status(Response.Status.NOT_FOUND).build() 
    }
}