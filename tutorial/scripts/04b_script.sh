#!/bin/sh
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace

#enable people kafka source
config="$(cat tutorial/openshift_definitions/04b/knative_kafka_people_source.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/04b/temp_knative_kafka_people_source.yaml
oc apply -f tutorial/openshift_definitions/04b/temp_knative_kafka_people_source.yaml
rm tutorial/openshift_definitions/04b/temp_knative_kafka_people_source.yaml

#enable addresses kafka source
config="$(cat tutorial/openshift_definitions/04b/knative_kafka_address_source.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/04b/temp_knative_kafka_address_source.yaml
oc apply -f tutorial/openshift_definitions/04b/temp_knative_kafka_address_source.yaml
rm tutorial/openshift_definitions/04b/temp_knative_kafka_address_source.yaml