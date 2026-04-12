package com.example.application.web.extensions

import com.example.application.web.requests.TodoRequest
import com.example.domain.entities.TodoModel

fun TodoRequest.toDomain(): TodoModel = TodoModel(
    id = 0,
    title = title,
    description = description,
    tags = tags,
    completed = false
)