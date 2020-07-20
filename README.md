## Пример приложения с Server-Side Row Model библиотеки agGrid

В этом репозитории находятся три приложения.

1. `./client`: `Frontend` приложение на `React` с настроенной 
[Server-Side Row Model](https://www.ag-grid.com/javascript-grid-server-side-model/#full-stack-examples);
2. `./server/springserver`: `Backend` приложение на `Spring Boot`, 
которое демонстрирует `ленивую загрузку` данных по требованию и 
выполняя над этими данными `группировку`, `фильтрацию`, `сортировку` над большими `сетами данных`;
3. `./server/node-server`: `Backend` приложение на `Node.js`, 
которое взято из [документации](https://www.ag-grid.com/graphql-server-side-operations/). Делает то же самое,
что приложение на `Spring Boot` и используется для сравнения.  

Взаимодействие между приложениями выполняется через `GraphQL`.

Запускается стандартными средствами:

```

# React client
> cd client
> yarn start

# Node.js Backend & JavaScript client
> cd server/node-server
> yarn start

# Spring Boot Application
> cd server/springserver
> ./gradlew bootRun

``` 

## Дамп базы данных

`PostgreSQL`: `./server/olympic_winners.sql`

`MySQL`: `./server/olympic_winners_mysql.sql`
