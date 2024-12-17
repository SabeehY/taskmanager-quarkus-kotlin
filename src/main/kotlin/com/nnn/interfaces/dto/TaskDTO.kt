package com.nnn.interfaces.dto

import java.time.Instant
import java.io.Serializable
import org.bson.types.ObjectId

import com.nnn.domain.Task

data class TaskDTO(
    val id: String? = null,
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null
) {
  companion object {
    fun toDTO(task: Task): TaskDTO = TaskDTO(
      id = task.id?.toString(),
      title = task.name, // <= V2: set name back to title for client
      isCompleted = task.isCompleted,
      createdAt = task.createdAt,
      updatedAt = task.updatedAt
    )
    fun fromDTO(dto: TaskDTO): Task {
        val task = Task()
        if (dto.id != null) {
            task.id = ObjectId(dto.id)
        }
        task.name = dto.title // <= V2: set name as title for persisting
        task.isCompleted = dto.isCompleted
        task.createdAt = dto.createdAt
        task.updatedAt = dto.updatedAt
        return task
    }
  }
}

data class CreateTaskRequest(
  val title: String,
)

data class UpdateTaskRequest(
  val title: String?,
  val isCompleted: Boolean?
)