#Workshop
## Admin
* install serverless operator
* install dev spaces operator
* install amq streams operator
* create project demo-project
* create devspace - check dev space route (demo-project namespace)
* create serverless serving
* create serverless eventing
* validate Serverless eventing and serving within the serverless section

## Setup Kafka
* at time of writing version 3.5
* 3 brokers
* in current name space
* setup kafka
* setup kafka -- later, when dev spaces are up and running -- via template

## Add Kafka to Knative eventing (for admin to do)
* check images

## configure dev spaces
oc whoami in terminal
change tutorial/scripts/.namespace file
validate version in tutorial/openshift_definitions/01/kafka_connect_cluster.yaml



# route fixed => no arrow labs
# database property via environmental variable


## Legacy 


use cases => validation ugly
naming domain => ugly
no created status, but just ref
DRY
startup ./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -Pmonolith
startup ./mvnw compile -Dquarkus.profile=db-postgres quarkus:dev -microservice-account

http://localhost:8080/q/graphql-ui/?


security

docker base path


# knative-demo

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/knative-demo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


