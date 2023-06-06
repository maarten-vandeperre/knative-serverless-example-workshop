#!/bin/sh
VERSION="0.1.13" #version of the application
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace
REBUILD=false #whether or not the application and image need to be rebuild
CONFIGURE=true #whether or not the config files should already be ajusted
DOCKER_IMAGE="quay.io/appdev_playground/knative_demo:microservice-address-uberjar-$VERSION"

if $REBUILD
then
  #fix mongo and channel url
  properties="$(cat application/configuration/microservice-address-configuration/src/main/resources/application.properties)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv application/configuration/microservice-address-configuration/src/main/resources/application.properties application/configuration/microservice-address-configuration/src/main/resources/application_backup.properties
  echo "$properties" > application/configuration/microservice-address-configuration/src/main/resources/application.properties
  #build uber jar for monolith
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmicroservice-address
  #dockerize it
  docker build -t quay.io/appdev_playground/knative_demo:microservice-address-uberjar-$VERSION -f ./application/configuration/microservice-address-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/microservice-address-configuration --platform linux/amd64
  #push docker image
  docker push quay.io/appdev_playground/knative_demo:microservice-address-uberjar-$VERSION
  mv application/configuration/microservice-address-configuration/src/main/resources/application_backup.properties application/configuration/microservice-address-configuration/src/main/resources/application.properties
fi

if $CONFIGURE
then
  #create microservice account Knative service
  config="$(cat tutorial/openshift_definitions/05/knative_service_microservice_address.yaml )"
  config="$(echo "${config//<VERSION>/$VERSION}")"
  config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
  config="$(echo "${config//<DOCKER_IMAGE>/$DOCKER_IMAGE}")"
  echo "$config" > tutorial/openshift_definitions/05/temp_knative_service_microservice_address.yaml
  oc apply -f tutorial/openshift_definitions/05/temp_knative_service_microservice_address.yaml
  rm tutorial/openshift_definitions/05/temp_knative_service_microservice_address.yaml

  #enable kafka address data changed channel
  config="$(cat tutorial/openshift_definitions/05/kafka_address_data_changed_channel.yaml )"
  config="$(echo "${config//<VERSION>/$VERSION}")"
  config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
  echo "$config" > tutorial/openshift_definitions/05/temp_kafka_address_data_changed_channel.yaml
  oc apply -f tutorial/openshift_definitions/05/temp_kafka_address_data_changed_channel.yaml
  rm tutorial/openshift_definitions/05/temp_kafka_address_data_changed_channel.yaml

  #add microservice account subscriber on address data changed channel
  config="$(cat tutorial/openshift_definitions/05/microservice_account_on_address_data_changed_subscription.yaml )"
  config="$(echo "${config//<VERSION>/$VERSION}")"
  config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
  echo "$config" > tutorial/openshift_definitions/05/temp_microservice_account_on_address_data_changed_subscription.yaml
  oc apply -f tutorial/openshift_definitions/05/temp_microservice_account_on_address_data_changed_subscription.yaml
  rm tutorial/openshift_definitions/05/temp_microservice_account_on_address_data_changed_subscription.yaml
fi