package com.nnn.application

// import io.quarkus.cache.CacheResult
// import io.quarkus.cache.CacheInvalidate
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

import com.nnn.domain.Task
import com.nnn.domain.TaskRepository
import com.nnn.interfaces.dto.TaskDTO
import com.nnn.interfaces.dto.CreateTaskRequest
import com.nnn.interfaces.dto.UpdateTaskRequest

@ApplicationScoped
class TaskService(
    private val repository: TaskRepository,
) {
    fun listAllTasks(): List<TaskDTO> = repository.listAllTasks().map { TaskDTO.toDTO(it) }
    
    // @CacheResult(cacheName = "task")
    fun findById(id: String) = repository.findById(ObjectId(id))?.let { TaskDTO.toDTO(it) }

    fun createTask(request: CreateTaskRequest): TaskDTO {
        val task = Task()
        task.name = request.title // <= V2: sets title to request name
        return repository.createTask(task).let { TaskDTO.toDTO(it) }
    }

    // @CacheInvalidate(cacheName = "task")
    fun updateTask(id: String, request: UpdateTaskRequest): TaskDTO? {
        val task = repository.findById(ObjectId(id)) ?: return null

        request.title?.let { task.name = it } // <= V2: sets title to request name
        request.isCompleted?.let { task.isCompleted = it }
        
        return repository.updateTask(task).let { TaskDTO.toDTO(it) }
    }
    
    fun deleteTask(id: String) = repository.deleteTask(ObjectId(id))
}
