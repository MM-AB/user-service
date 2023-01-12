# User microservice

## Prerequisites

Create network **rsonetwork** if there is none:

```bash
docker network create rsonetwork
```

Install MongoDB and start MongoDB server with:
```bash
mongod
```

Clean install Maven package:
```bash
mvn clean install
```


## Build and run Docker containers

Building **user-service** container:
```bash
docker build -t user-service .
```

Run mongo:
```bash
docker run --name=mongo --rm --network=rsonetwork mongo
```

Run app:
```bash
docker run --name=user-service --rm --network=rsonetwork -p 8080:8080 -e MONGO_USER_URL=MONGO_URL=mongodb://mongo:27017/users user-service
```

## Rename Docker image and push to repository

Rename image:
```bash
docker tag user-service rsoreg.azurecr.io/user-service:**tag**
```

Push to repositorty:
```bash
docker push rsoreg.azurecr.io/user-service:**tag**
```
