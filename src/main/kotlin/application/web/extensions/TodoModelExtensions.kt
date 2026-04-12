package application.web.extensions

import com.example.application.web.requests.TodoResponse
import com.example.domain.entities.TodoModel

fun TodoModel.toResponse(): TodoResponse = TodoResponse(
    id,
    title,
    description,
    tags,
    completed)