#!/bin/bash

set -e

for file in sql/*.sql
do
    echo "Running $file"
    psql -h localhost -U taskflow -d taskflow -f "$file"
done