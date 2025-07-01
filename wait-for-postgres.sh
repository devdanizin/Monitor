#!/bin/sh
# wait-for-postgres.sh

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

echo "Esperando o banco de dados $host:$port ficar disponível..."

while ! nc -z "$host" "$port"; do
  sleep 1
done

echo "Banco disponível! Iniciando a aplicação..."

exec $cmd