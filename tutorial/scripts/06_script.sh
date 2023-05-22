#!/bin/sh
VERSION="0.1.4" #version of the application
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace
REBUILD=true #whether or not the application and image need to be rebuild
#DOCKER_IMAGE="quay.io/appdev_playground/knative_demo:microservice-address-uberjar-$VERSION"

#if $REBUILD
#then
#  #fix mongo and channel url
#  properties="$(cat application/configuration/microservice-person-configuration/src/main/resources/application.properties)"
#  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
#  mv application/configuration/microservice-person-configuration/src/main/resources/application.properties application/configuration/microservice-person-configuration/src/main/resources/application_backup.properties
#  echo "$properties" > application/configuration/microservice-person-configuration/src/main/resources/application.properties
#  #build uber jar for monolith
#  ./mvnw package -Dquarkus.package.type=uber-jar -Pmicroservice-person
#  #dockerize it
#  docker build -t quay.io/appdev_playground/knative_demo:microservice-person-uberjar-$VERSION -f ./application/configuration/microservice-person-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/microservice-person-configuration --platform linux/amd64
#  #push docker image
#  docker push quay.io/appdev_playground/knative_demo:microservice-person-uberjar-$VERSION
#  mv application/configuration/microservice-person-configuration/src/main/resources/application_backup.properties application/configuration/microservice-person-configuration/src/main/resources/application.properties
#  fi
#
##create microservice account Knative service
#config="$(cat tutorial/openshift_definitions/06/knative_service_microservice_person.yaml )" 
#config="$(echo "${config//<VERSION>/$VERSION}")"
#config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
#config="$(echo "${config//<DOCKER_IMAGE>/$DOCKER_IMAGE}")"
#echo "$config" > tutorial/openshift_definitions/06/temp_knative_service_microservice_person.yaml
#oc apply -f tutorial/openshift_definitions/06/temp_knative_service_microservice_person.yaml
#rm tutorial/openshift_definitions/06/temp_knative_service_microservice_person.yaml

#enable kafka address data changed channel
config="$(cat tutorial/openshift_definitions/06/kafka_native_broker_person_data.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/06/temp_kafka_native_broker_person_data.yaml
oc apply -f tutorial/openshift_definitions/06/temp_kafka_native_broker_person_data.yaml
rm tutorial/openshift_definitions/06/temp_kafka_native_broker_person_data.yaml

#add microservice account subscriber on address data changed channel
config="$(cat tutorial/openshift_definitions/06/kafka_native_broker_trigger_person_data.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/06/temp_kafka_native_broker_trigger_person_data.yaml
oc apply -f tutorial/openshift_definitions/06/temp_kafka_native_broker_trigger_person_data.yaml
rm tutorial/openshift_definitions/06/temp_kafka_native_broker_trigger_person_data.yaml