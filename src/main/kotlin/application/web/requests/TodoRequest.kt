package com.example.application.web.requests

import kotlinx.serialization.Serializable

@Serializable
data class TodoRequest(
    val title: String,
    val description: String,
    val tags: String)