#!/bin/bash

awslocal sqs create-queue \
  --queue-name process.fifo \
  --attributes "FifoQueue=true"