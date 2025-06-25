attempts=0
max_attempts=100
while true; do
  attempts=$((attempts+1))
  echo "Попытка $attempts..."
  docker compose -f compose-all.yaml up --build --remove-orphans

  # Проверяем код возврата команды
  if [ $? -eq 0 ]; then
    echo "Команда выполнена успешно!"
    break
  else
    echo "Ошибка! Команда не выполнена. Ожидание 2 секунды..."
    sleep 2
    if [ $attempts -eq $max_attempts ]; then
       echo "Превышено максимальное количество попыток."
       exit 1
    fi
  fi
done