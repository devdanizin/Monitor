#!/bin/sh

# wait-for-postgres.sh

set -e

host="$1"
port="$2"
shift 2

echo "Waiting for PostgreSQL at $host:$port..."

while ! nc -z "$host" "$port"; do
  echo "PostgreSQL is unavailable - sleeping"
  sleep 2
done

echo "PostgreSQL is up - executing command"
exec "$@"
