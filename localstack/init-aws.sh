#!/bin/bash

awslocal sqs create-queue \
  --queue-name process.fifo \
  --attributes "FifoQueue=true"

awslocal s3 mb s3://process