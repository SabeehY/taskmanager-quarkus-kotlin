package com.nnn.infrastructure.persistence.mongo

import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId
import java.time.Instant

import com.nnn.domain.TaskRepository
import com.nnn.domain.Task

@ApplicationScoped
class MongoTaskRepository : TaskRepository, PanacheMongoRepository<Task> {
    
    override fun listAllTasks(): List<Task> = Task.listAll()

    override fun createTask(task: Task): Task {
        task.persist()
        return task
    }

    override fun findById(id: ObjectId): Task? = find("_id", id).firstResult()

    override fun updateTask(task: Task): Task {
        task.updatedAt = Instant.now()
        task.update()
        return task
    }

    override fun deleteTask(id: ObjectId): Boolean = deleteById(id)
}
