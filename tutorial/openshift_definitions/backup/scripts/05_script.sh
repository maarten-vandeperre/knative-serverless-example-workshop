#!/bin/sh
VERSION="0.1.6" #version of the application
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace
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
config="$(cat tutorial/openshift_definitions/05/knative_service_microservice_account.yaml )" #reuse definition from previous step if rebuild is required
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/05/temp_knative_service_microservice_account.yaml
oc apply -f tutorial/openshift_definitions/05/temp_knative_service_microservice_account.yaml
rm tutorial/openshift_definitions/05/temp_knative_service_microservice_account.yaml

#enable kafka person data changed channel
config="$(cat tutorial/openshift_definitions/05/kafka_person_data_changed_channel.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/05/temp_kafka_person_data_changed_channel.yaml
oc apply -f tutorial/openshift_definitions/05/temp_kafka_person_data_changed_channel.yaml
rm tutorial/openshift_definitions/05/temp_kafka_person_data_changed_channel.yaml

#add microservice account subscriber on person data changed channel
config="$(cat tutorial/openshift_definitions/05/microservice_account_on_person_data_changed_subscription.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/05/temp_microservice_account_on_person_data_changed_subscription.yaml
oc apply -f tutorial/openshift_definitions/05/temp_microservice_account_on_person_data_changed_subscription.yaml
rm tutorial/openshift_definitions/05/temp_microservice_account_on_person_data_changed_subscription.yaml