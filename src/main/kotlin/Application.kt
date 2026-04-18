package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.di.dependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.flywaydb.core.Flyway
import resources.aws.sqs.ProcessSqsListener

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module(
) {
    val config = environment.config
    val flyway = Flyway.configure()
        .dataSource(
            config.property("database.url").getString(),
            config.property("database.user").getString(),
            config.property("database.password").getString(),
        )
        .load()

    flyway.migrate()
    configureHTTP()
    configureSerialization()
    configureRouting()
    configureDependencyInjection(config)

    launch(Dispatchers.IO) {
        dependencies.resolve<ProcessSqsListener>().run()
    }
}
