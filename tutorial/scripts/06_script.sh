#!/bin/sh
VERSION="0.1.6" #version of the application
NAMESPACE="maarten-playground" #name of your OpenShift namespace

#enable kafka person data changed channel
config="$(cat tutorial/openshift_definitions/06/kafka_person_data_changed_channel.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/06/temp_kafka_person_data_changed_channel.yaml
oc apply -f tutorial/openshift_definitions/06/temp_kafka_person_data_changed_channel.yaml
rm tutorial/openshift_definitions/06/temp_kafka_person_data_changed_channel.yaml

#add microservice account subscriber on person data changed channel
config="$(cat tutorial/openshift_definitions/06/microservice_account_on_person_data_changed_subscription.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/06/temp_microservice_account_on_person_data_changed_subscription.yaml
oc apply -f tutorial/openshift_definitions/06/temp_microservice_account_on_person_data_changed_subscription.yaml
rm tutorial/openshift_definitions/06/temp_microservice_account_on_person_data_changed_subscription.yaml