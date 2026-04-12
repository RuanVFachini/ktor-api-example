package com.example.resources.config

import org.jetbrains.exposed.v1.jdbc.Database

fun configureDatabases(
    connectionUrl: String,
    user: String,
    driver: String,
    password: String
) = Database.connect(
    url = connectionUrl,
    user = user,
    driver = driver,
    password = password,
)
