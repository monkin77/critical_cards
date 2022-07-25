# CriticalCards

Welcome to the documentation pages of the **CriticalCards** of
_Critical SummerCamp 2022_.

Here you can find detailed information about the project, including the development
teams and run instructions.  
So far, contributions are exclusively made by the initial team, but we hope to open
them to the community.

## Description

This project is a collaborative tool for retrospective and poker sessions. It allows
team members to participate in these remotely. Users can create and name lanes in their
sessions. Furthermore, users can add cards to the lanes, move the cards between lanes,
and edit their content.

![Main page](./docs/main_page.png)

### Product Vision

To be the ultimate collaborative retrospective platform for Critical Software engineers.

### Elevator Pitch

Retrospective sessions are a critical part of the Agile development process. Now that remote
work is more prevalent in the workplace, it is imperative that engineers can engage
in these sessions at a distance. The platform allows anyone to easily participate, saving
on post-it notes.

## Setup

The application is made of three components:

- The frontend in [Angular](https://angular.io) (version 14.0)
- The backend in [Quarkus](https://quarkus.io) (version 2.9.2)
- The [PostgreSQL](https://www.postgresql.org) (latest docker image version) database

### Frontend setup

For development purposes, a dev server is available and is spawned by calling `ng serve`.
The server will be available at [localhost:4200](http://localhost:4200) by default. With
this, the application will automatically reload if you change any of the source files.

Running `ng build` instead builds the project. The build artifacts will be stored in
the `dist/` directory.

More **information about the frontend** can be found on the [frontend's README](./frontend/).

### Backend setup

Running the backend in dev mode enables live coding. This can be done using:

```sh
mvnw compile quarkus:dev
```

To package the application:

```sh
mvnw package
```

This produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory, and stores
the dependencies in the `target/quarkus-app/lib/` directory. The application is now runnable
using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```sh
mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

More **information about the backend** can be found on the
[backend's README](./backend/CriticalCards/).

### Database setup

The [PostgreSQL](https://www.postgresql.org) server runs inside a [docker](https://www.docker.com)
container, and is orchestrated using [docker compose](https://docs.docker.com/compose).

To start (and bootstrap) the database, a user should call `docker-compose up` on
the [db.yml](./db.yml) file:

```sh
docker-compose -f db.yml up
# or in Compose V2
docker compose -f db.yml up
```

**Note**: the database name and credentials are present on the [db.yml](./db.yml) and
can be customized there.

## Contribution guidelines

### Tests

- As of writing, the frontend0s components aren't tested;
- The backend functionalities are tested using JUnit5:
    - The tests use the database and are _idempotent_.

### Code review

- Commits to _master_ aren't allowed;
- Pull requests from _development_ branches to the _master_ branch should
be reviewed and approved by members of other teams.

## Development teams

The project was developed by the following 4 teams managed using the
[Kanban management system](https://kanbanize.com/kanban-resources/getting-started/what-is-kanban).

### A Equipa

- Duarte Bento
- Mariana Libório
- Pedro António Coelho e Silva
- Salvador Domingues

### A Outra Equipa

- Guilherme Fontes
- João Trocado
- José Lourenço
- Marisa Nunes

### FEUP

- João de Jesus Costa
- João Mesquita
- José Macedo
- Tiago Duarte da Silva

### The Arnolds

- Daniel Sousa
- João Pedro Félix
- João Bruno Rocha