#!/bin/bash

docker-compose down

docker rmi devconf5296/wowtown_backend:latest

docker-compose up -d
