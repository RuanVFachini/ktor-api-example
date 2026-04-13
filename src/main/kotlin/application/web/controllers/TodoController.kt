package com.example.application.web.controllers

import application.web.extensions.toResponse
import com.example.application.web.extensions.toDomain
import com.example.application.web.requests.TodoRequest
import com.example.domain.repositories.TodoRepository
import io.ktor.http.HttpMethod
import io.ktor.http.HttpMethod.Companion
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.RoutingHandler
import io.ktor.server.routing.method

class TodoController(private val todoRepository: TodoRepository) {


    suspend fun all()= todoRepository.all().map { it.toResponse() }

    suspend fun save(request: TodoRequest) = todoRepository.save(request.toDomain())
}