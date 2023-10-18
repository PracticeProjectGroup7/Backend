#!/usr/bin/env bash

set -e

./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="\
 -Ddemo-host=${POSTGRES_USER} \
" &

while true; do
  echo "Building"
  watch -d -t -g "ls -lR ./src | md5sum" && ./mvnw compile
  echo "Change detected"
done
aa
