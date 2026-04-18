package resources.aws.sqs

import aws.sdk.kotlin.services.sqs.SqsClient
import aws.smithy.kotlin.runtime.net.url.Url

class SqsClientFactory() {
    suspend fun create() = SqsClient.fromEnvironment({
        endpointUrl = Url.parse(System.getenv("AWS_ENDPOINT_URL")!!)
    })
}