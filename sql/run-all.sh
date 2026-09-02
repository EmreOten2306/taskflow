#!/bin/bash

set -e

for file in sql/*.sql
do
    echo "Running $file"
    docker exec -i taskflow-db psql -U taskflow -d taskflow -f - < "$file"
done