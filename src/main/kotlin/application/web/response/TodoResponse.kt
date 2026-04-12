package com.example.application.web.requests

import kotlinx.serialization.Serializable

@Serializable
data class TodoResponse(
    val id: Int,
    val title: String,
    val description: String,
    val tags: String,
    val completed: Boolean)