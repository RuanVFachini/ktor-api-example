package com.example.application.web.controllers

import application.web.extensions.toResponse
import com.example.application.web.extensions.toDomain
import com.example.application.web.requests.TodoRequest
import com.example.domain.repositories.TodoRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

class TodoController(private val todoRepository: TodoRepository) {
    suspend fun all(call: RoutingCall) {
        call.respond(HttpStatusCode.OK, todoRepository.all().map { it.toResponse() })
    }

    suspend fun save(call: RoutingCall) {
        val request = call.receive<TodoRequest>()
        val entity = todoRepository.save(request.toDomain())

        call.respond(HttpStatusCode.Created, entity.toResponse())
    }
}