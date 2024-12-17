package com.nnn.domain

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoCompanion
import org.bson.types.ObjectId
import java.time.Instant

@MongoEntity(collection = "tasks")
class Task : PanacheMongoEntity() {
    // V1 field - maintained for backward compatibility
    var title: String = ""
    
    // V2 field - new version
    var name: String = ""
    
    var isCompleted: Boolean = false
    var createdAt: Instant? = Instant.now()
    var updatedAt: Instant? = Instant.now()
    var version: Int = 2  // Track entity version

    companion object : PanacheMongoCompanion<Task>
}
