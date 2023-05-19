#!/bin/sh
VERSION="0.0.3" #version of the application
NAMESPACE="maarten-playground" #name of your OpenShift namespace
REBUILD=true #whether or not the application and image need to be rebuild

if $REBUILD
then
  #build uber jar for monolith
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmicroservice-account
  #dockerize it
  docker build -t quay.io/appdev_playground/knative_demo:microservice-account-uberjar-$VERSION -f ./application/configuration/microservice-account-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/microservice-account-configuration --platform linux/amd64
  #push docker image
  docker push quay.io/appdev_playground/knative_demo:microservice-account-uberjar-$VERSION
fi


#create microservice account Knative service
config="$(cat tutorial/openshift_definitions/04/knative_service_microservice_account.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/04/temp_knative_service_microservice_account.yaml
oc apply -f tutorial/openshift_definitions/04/temp_knative_service_microservice_account.yaml
rm tutorial/openshift_definitions/04/temp_knative_service_microservice_account.yaml

#enable kafka source
config="$(cat tutorial/openshift_definitions/04/knative_kafka_people_source.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/04/temp_knative_kafka_people_source.yaml
oc apply -f tutorial/openshift_definitions/04/temp_knative_kafka_people_source.yaml
rm tutorial/openshift_definitions/04/temp_knative_kafka_people_source.yaml