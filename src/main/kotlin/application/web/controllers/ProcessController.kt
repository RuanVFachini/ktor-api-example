package application.web.controllers

import aws.sdk.kotlin.services.sqs.model.SendMessageRequest
import resources.aws.sqs.SqsClientFactory
import resources.config.AwsConfig
import java.util.UUID
import kotlin.uuid.Uuid


class ProcessController(
    val sqsClientFactory: SqsClientFactory,
    val awsConfig: AwsConfig
) {
    suspend fun enqueueProcess() {
        val client = sqsClientFactory.create()

        val sendRequest =
            SendMessageRequest {
                queueUrl = awsConfig.processQueueUrl
                messageBody = "teste"
                messageGroupId = "grupo-1"
                messageDeduplicationId = UUID.randomUUID().toString()
            }

        client.sendMessage(sendRequest)
    }
}