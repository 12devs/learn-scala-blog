#!/bin/sh

# TODO some kind of versioning?

cd $(dirname $0) || { echo "where are we?"; exit; }

cd bct-components
npm install
npm link 
cd ..

cd bct-client
npm install
npm link bct-components
npm run-script build
docker rmi bct-client:latest 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-client:latest 2>/dev/null
docker build -t bct-client:latest .
docker tag bct-client:latest 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-client:latest
cd ..

cd api-demo
npm install
npm link bct-components
npm run-script build
docker rmi api-demo:latest 2>/dev/null
docker build -t api-demo:latest .
cd ..

cd bct-docs
npm install
npm link bct-components
npm run-script catalog-build
docker rmi bct-docs:latest 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-docs:latest 2>/dev/null
docker build -t bct-docs:latest .
docker tag bct-docs:latest 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-docs:latest
cd ..

cd bct-server
docker rmi bct-server:latest 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-server:latest 2>/dev/null
sbt clean docker:publishLocal
docker tag bct-server:latest 704771769367.dkr.ecr.us-west-2.amazonaws.com/bct-server:latest
cd ..

echo 
echo "Built 4 docker images eh"
echo
docker images

