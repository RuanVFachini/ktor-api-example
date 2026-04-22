package resources.aws.sqs

import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.net.url.Url

class S3ClientFactory() {
    suspend fun create() = S3Client.fromEnvironment({
        endpointUrl = Url.parse(System.getenv("S3_ENDPOINT_URL")!!)
        forcePathStyle = true
    })
}