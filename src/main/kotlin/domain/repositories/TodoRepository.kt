package com.example.domain.repositories

import com.example.domain.entities.TodoModel

interface TodoRepository {
    suspend fun save(todo: TodoModel): TodoModel
    suspend fun all(): List<TodoModel>
}