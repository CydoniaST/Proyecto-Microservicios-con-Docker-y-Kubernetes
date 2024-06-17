@echo off

docker build -f ./Planner.Dockerfile -t cydoniast/planner .
docker push cydoniast/planner

docker build -f ./WindService.Dockerfile -t cydoniast/windservice .
docker push cydoniast/windservice

cd Server/eolopark
mvn compile jib:build -Dimage=cydoniast/server &

cd ../../geoservice
mvn spring-boot:build-image &
docker push cydoniast/geoservice:latest
