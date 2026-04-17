package com.example

import application.web.controllers.ProcessController
import application.web.extensions.toResponse
import com.example.application.web.controllers.TodoController
import com.example.application.web.requests.TodoRequest
import com.example.application.web.requests.TodoResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(RequestValidation) {
        validate<String> { bodyText ->
            if (!bodyText.startsWith("Hello"))
                ValidationResult.Invalid("Body text should start with 'Hello'")
            else ValidationResult.Valid
        }
    }
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }


        route("/todos") {
            get {
                val controller = dependencies.resolve<TodoController>()
                val collection: List<TodoResponse> = controller.all()
                call.respond(HttpStatusCode.OK, collection)
            }
            post {
                val request: TodoRequest = call.receive<TodoRequest>()
                val controller = dependencies.resolve<TodoController>()
                val response: TodoResponse  = controller.save(request).toResponse()
                call.respond<TodoResponse>(HttpStatusCode.Created, response)
            }
        }

        route("/process") {
            post("/enqueue") {
                val controller = dependencies.resolve<ProcessController>()
                controller.enqueueProcess()
                call.respond(HttpStatusCode.Accepted)
            }
        }

    }
}
