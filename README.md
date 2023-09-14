# 2023-DEV3-092-TicTacToe Dominick Lievens

## Introduction
Hello and welcome to Tic Tac Toe!

## Useful information
Swagger URL:
http://localhost:8080/swagger-ui/index.html
\
Endpoints of the rest api are documented in swagger-ui.

This projects also contains a Dockerfile & docker-compose.yaml.

### Start application
You can start the app + Postgres via docker-compose:
```shell
docker compose up -d
```

If you do not have access to docker, there is also an executable jar named "tictactoe.jar". 
This jar uses a h2-database. It's build with a profile named "local", which uses application-local.properties that contains h2 config. 
```shell
java -jar tictactoe.jar
```

If you want to compile the code yourself, use following command:
```shell
 ./mvnw clean install -P local
```
or if you want to use Postgres:
```shell
 ./mvnw clean install
```
You can find the properties for connection with Postgres in application.properties.

## Creator:
- Dominick Lievens
