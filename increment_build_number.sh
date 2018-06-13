#!/bin/bash

set -e -x

cd $(dirname $0)

current=$(<build_number)

bn=$((current +1))

if [ -n "$1" ]
  then bn="$1"
fi

sed -i 's/currentBuildNumber = .*/currentBuildNumber = '$bn'/' bct-server/src/main/resources/application.conf
sed -i 's/currentBuildNumber = .*/currentBuildNumber = '$bn';/' bct-client/src/services/BuildNumber.ts

cat > build_number <<<$bn
