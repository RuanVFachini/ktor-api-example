package com.example.resources.repositories

import com.example.domain.entities.TodoModel
import com.example.domain.entities.Todos
import com.example.domain.repositories.TodoRepository
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

private fun toDomain(row: ResultRow) = TodoModel(
    id = row[Todos.id],
    title = row[Todos.title],
    description = row[Todos.description],
    tags = row[Todos.tags],
    completed = row[Todos.completed]
)

class TodoRepositoryImpl(
    private val db: Database,
): TodoRepository {

    override suspend fun save(todo: TodoModel): TodoModel {
        suspendTransaction {
            todo.id = Todos.insert {
                it[Todos.title] = todo.title
                it[Todos.description] = todo.description
                it[Todos.tags] = todo.tags
            } get Todos.id

            commit()
        }

        return todo
    }

    override suspend fun all(): List<TodoModel> {
        return suspendTransaction {
            return@suspendTransaction Todos.selectAll().map { toDomain(it) }
        }
    }
}