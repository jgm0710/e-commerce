#! /bin/bash
./gradlew clean build

docker-compose down

docker-compose up -d --build
