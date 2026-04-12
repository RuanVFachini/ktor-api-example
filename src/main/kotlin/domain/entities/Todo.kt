package com.example.domain.entities

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.isNotNull

class TodoModel(
    var id: Int,
    var title: String,
    var description: String,
    var tags: String,
    var completed: Boolean
)

object Todos: Table("todos") {
    var id = integer("id").autoIncrement()
    var title = varchar("title", 255)
    var description = varchar("description", 255)
    var tags = varchar("tags", 255)
    var completed = bool("completed").default(false)
}