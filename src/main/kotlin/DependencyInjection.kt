package com.example

import application.web.controllers.ProcessController
import com.example.application.web.controllers.TodoController
import com.example.domain.repositories.TodoRepository
import com.example.resources.config.configureDatabases
import com.example.resources.repositories.TodoRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.di.provide
import org.jetbrains.exposed.v1.jdbc.Database
import resources.aws.sqs.ProcessSqsListener
import resources.aws.sqs.S3ClientFactory
import resources.aws.sqs.SqsClientFactory
import resources.config.AwsConfig

fun Application.configureDependencyInjection(config: ApplicationConfig) {

    dependencies {
        //Controllers
        provide(TodoController::class)
        provide(ProcessController::class)
        provide(SqsClientFactory::class)
        provide(S3ClientFactory::class)
        provide<AwsConfig> {
            AwsConfig(
                config.property("aws.queues.process").getString(),
                config.property("aws.s3.process.name").getString()
            )
        }
        provide(ProcessSqsListener::class)

        //repositories
        provide<TodoRepository>(TodoRepositoryImpl::class)

        //Databases
        provide<Database> {

            configureDatabases(
                config.property("database.url").getString(),
                config.property("database.user").getString(),
                config.property("database.driver").getString(),
                config.property("database.password").getString(),
            )
        }
    }
}