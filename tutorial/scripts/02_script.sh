#!/bin/sh
VERSION="0.0.4" #version of the application
NAMESPACE="maarten-playground" #name of your OpenShift namespace

#build uber jar for monolith
./mvnw package -Dquarkus.package.type=uber-jar -Pmonolith
#dockerize it
docker build -t quay.io/appdev_playground/knative_demo:monolith-uberjar-$VERSION -f ./application/configuration/monolith-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/monolith-configuration --platform linux/amd64
#push docker image
docker push quay.io/appdev_playground/knative_demo:monolith-uberjar-$VERSION

#create deployment
config="$(cat tutorial/openshift_definitions/02/deployment_config.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/02/temp_deployment_config.yaml
oc apply -f tutorial/openshift_definitions/02/temp_deployment_config.yaml
rm tutorial/openshift_definitions/02/temp_deployment_config.yaml