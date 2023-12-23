# task-manager

## Для запуска необходимо:
1) скачать репозиторий
3) записать в src/main/resources/application.properties свойства пользователя (пароль, имя), опционально обновить секретный ключ и сменить порты 
4) записать в docker-compose.yml то же что и в прошлом пункте. Изменить PGADMIN_DEFAULT_EMAIL и PGADMIN_DEFAULT_PASSWORD использующиеся для авторизации в веб pgAdmin
4) выполнить через докер команду:

docker-compose up

если docker выдаст ошибку "PostgreSQL Database directory appears to contain a database; Skipping initialization"
нужно выполнить через докер команды:


docker-compose down --volumes

docker-compose up

## Для взаимодействия с бд через веб pgAdmin необходимо:
6) перейти к порту указанному в docker-compose.yml (http://localhost:5050)
7) После авторизации данными из docker-compose.yml (логин admin@admin.com, пароль root)
8) Создать сервер с:
   1) любым именем
   2) Во вкладке Connection указываем: Host name - наименование, которое мы указали для контейнера с нашей БД (service-db). 
   3) Port - внутренний порт контейнера БД (5432).
   4) Maintenance database - наименование нашей базы данных: task_manager_db.
   5) Username - тот юзернейм, который мы указали в docker-compose.yml для подключения к БД (postgres).
   6) Password - тот пароль, который мы указали в docker-compose.yml для подключения к БД (1234).



## Документация сгенерированная с помощью  swagger: http://localhost:8787/swagger-ui/index.html 