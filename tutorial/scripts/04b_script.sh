#!/bin/sh
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace

#enable kafka source
config="$(cat tutorial/openshift_definitions/04/knative_kafka_people_source.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/04/temp_knative_kafka_people_source.yaml
oc apply -f tutorial/openshift_definitions/04/temp_knative_kafka_people_source.yaml
rm tutorial/openshift_definitions/04/temp_knative_kafka_people_source.yaml