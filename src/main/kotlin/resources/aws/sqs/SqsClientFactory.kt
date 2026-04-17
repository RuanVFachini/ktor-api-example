package resources.aws.sqs

import aws.sdk.kotlin.services.sqs.SqsClient

class SqsClientFactory() {
    suspend fun create() = SqsClient.fromEnvironment()
}