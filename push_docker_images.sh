#!/bin/sh

# TODO some kind of versioning?

cd $(dirname $0) || { echo "where are we?"; exit; }

docker push 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-client:latest
docker push 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-docs:latest
docker push 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-server:latest
