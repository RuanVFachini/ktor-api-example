package com.example.application.web.controllers

import com.example.application.web.requests.TodoResponse
import com.example.domain.entities.TodoModel
import com.example.domain.repositories.TodoRepository
import com.example.module
import io.ktor.client.call.body

import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.content
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        application {
            module()
            dependencies.resolve<TodoRepository>().save(TodoModel(
                0,
                "test",
                "description",
                "tags",
                false
            ))
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.get("/todos").apply {

            assertEquals(HttpStatusCode.OK, status)
            val todos: List<TodoResponse> = body()
            assertEquals(1, todos.size)
        }
    }

}
