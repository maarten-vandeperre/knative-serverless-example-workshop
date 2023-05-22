#!/bin/sh
VERSION="0.0.14" #version of the application
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace
REBUILD=true #whether or not the application and image need to be rebuild
APP_DOCKER_IMAGE="quay.io/appdev_playground/knative_demo:microservice-account-uberjar-$VERSION"

if $REBUILD
then
  #fix postgres url
  properties="$(cat application/configuration/microservice-account-configuration/src/main/resources/application.properties)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv application/configuration/microservice-account-configuration/src/main/resources/application.properties application/configuration/microservice-account-configuration/src/main/resources/application_backup.properties
  echo "$properties" > application/configuration/microservice-account-configuration/src/main/resources/application.properties
  #build uber jar for monolith
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmicroservice-account
  #dockerize it
  docker build -t quay.io/appdev_playground/knative_demo:microservice-account-uberjar-$VERSION -f ./application/configuration/microservice-account-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/microservice-account-configuration --platform linux/amd64
  #push docker image
  docker push quay.io/appdev_playground/knative_demo:microservice-account-uberjar-$VERSION
  mv application/configuration/microservice-account-configuration/src/main/resources/application_backup.properties application/configuration/microservice-account-configuration/src/main/resources/application.properties
fi

#create microservice account Knative service
config="$(cat tutorial/openshift_definitions/04a/knative_service_microservice_account.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
config="$(echo "${config//<DOCKER_IMAGE>/$APP_DOCKER_IMAGE}")"
echo "$config" > tutorial/openshift_definitions/04a/temp_knative_service_microservice_account.yaml
oc apply -f tutorial/openshift_definitions/04a/temp_knative_service_microservice_account.yaml
rm tutorial/openshift_definitions/04a/temp_knative_service_microservice_account.yaml

#create monolith_data_changed debezium connector
config="$(cat tutorial/openshift_definitions/04a/debezium_postgres_connector.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/04a/temp_debezium_postgres_connector.yaml
oc apply -f tutorial/openshift_definitions/04a/temp_debezium_postgres_connector.yaml
rm tutorial/openshift_definitions/04a/temp_debezium_postgres_connector.yaml

echo ""
echo ""
echo ""
echo "oc exec -it my-cluster-kafka-0 \\ \n\
        -- bin/kafka-console-consumer.sh \\ \n\
        --bootstrap-server my-cluster-kafka-bootstrap.$NAMESPACE.svc.cluster.local:9092 \\ \n\
        --topic <TOPIC>"