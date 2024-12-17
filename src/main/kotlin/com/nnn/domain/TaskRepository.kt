package com.nnn.domain

import org.bson.types.ObjectId
import com.nnn.domain.Task

interface TaskRepository {
    fun listAllTasks(): List<Task>
    fun findById(id: ObjectId): Task?
    fun createTask(task: Task): Task
    fun updateTask(task: Task): Task
    fun deleteTask(id: ObjectId): Boolean
}