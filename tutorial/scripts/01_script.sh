#!/bin/sh
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace
DOCKER_IMAGE="quay.io/appdev_playground/kafka-connect-cluster:latest"

#create kafka connect cluster
config="$(cat tutorial/openshift_definitions/01/kafka_connect_cluster.yaml )"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
config="$(echo "${config//<DOCKER_IMAGE>/$DOCKER_IMAGE}")"
echo "$config" > tutorial/openshift_definitions/01/temp_kafka_connect_cluster.yaml
oc apply -f tutorial/openshift_definitions/01/temp_kafka_connect_cluster.yaml
rm tutorial/openshift_definitions/01/temp_kafka_connect_cluster.yaml