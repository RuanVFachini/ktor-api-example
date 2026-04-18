package resources.aws.sqs

import aws.sdk.kotlin.services.sqs.model.DeleteMessageRequest
import aws.sdk.kotlin.services.sqs.model.ReceiveMessageRequest
import kotlinx.coroutines.delay
import resources.config.AwsConfig

class ProcessSqsListener(
    val sqsClientFactory: SqsClientFactory,
    val awsConfig: AwsConfig
) {
    suspend fun run() {
        while (true) {
            try {
                val receiveRequest = ReceiveMessageRequest {
                    queueUrl = awsConfig.processQueueUrl
                    maxNumberOfMessages = 5
                    waitTimeSeconds = 20 // Long polling
                }

                val sqsClient = sqsClientFactory.create()
                val response = sqsClient.receiveMessage(receiveRequest)
                response.messages?.forEach { message ->
                    // Process message here
                    println(message.body)

                    // Delete after processing
                    val deleteRequest = DeleteMessageRequest {
                        queueUrl = awsConfig.processQueueUrl
                        receiptHandle = message.receiptHandle
                    }
                    sqsClient.deleteMessage(deleteRequest)
                }
            } catch (e: Exception) {
                // Log error
            }

            delay(2000)
        }
    }
}