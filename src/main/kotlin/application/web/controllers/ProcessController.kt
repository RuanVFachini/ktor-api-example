package application.web.controllers

import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.sdk.kotlin.services.sqs.model.SendMessageRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import io.ktor.http.ContentType
import resources.aws.sqs.S3ClientFactory
import resources.aws.sqs.SqsClientFactory
import resources.config.AwsConfig
import java.util.UUID


class ProcessController(
    val sqsClientFactory: SqsClientFactory,
    val s3ClientFactory: S3ClientFactory,
    val awsConfig: AwsConfig
) {
    suspend fun upload(fileKey: String?, payload: ByteArray) {
        val client = s3ClientFactory.create()
        val request = PutObjectRequest( {
            bucket = "process"
            key = fileKey
            body = ByteStream.fromBytes(payload)
            contentType = ContentType.Application.OctetStream.toString()
        })

        client.putObject(request)
    }

    suspend fun enqueueProcess(id: String?) {
        val client = sqsClientFactory.create()

        val sendRequest =
            SendMessageRequest {
                queueUrl = awsConfig.processQueueUrl
                messageBody = "$id"
                messageGroupId = "grupo-1"
                messageDeduplicationId = UUID.randomUUID().toString()
            }

        client.sendMessage(sendRequest)
    }
}