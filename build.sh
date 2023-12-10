#!/bin/bash

mvn clean
mvn install

docker-compose up --build -d