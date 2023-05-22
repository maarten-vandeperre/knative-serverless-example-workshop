#!/bin/sh
VERSION="0.1.14" #version of the application
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace
REBUILD=true #whether or not the application and image need to be rebuild
CONFIGURE=true #whether or not the config files should already be ajusted
DOCKER_IMAGE="quay.io/appdev_playground/knative_demo:$VERSION"

if $REBUILD
then
  #build uber jar for monolith
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmonolith
  #dockerize it
  docker build -t quay.io/appdev_playground/knative_demo:$VERSION -f ./application/configuration/monolith-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/monolith-configuration --platform linux/amd64
  #push docker image
  docker push quay.io/appdev_playground/knative_demo:$VERSION
fi

if $CONFIGURE
then
  #create deployment
  config="$(cat tutorial/openshift_definitions/03/knative_serving_monolith.yaml )"
  config="$(echo "${config//<VERSION>/$VERSION}")"
  config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
  config="$(echo "${config//<DOCKER_IMAGE>/$DOCKER_IMAGE}")"
  echo "$config" > tutorial/openshift_definitions/03/temp_knative_serving_monolith.yaml
  oc apply -f tutorial/openshift_definitions/03/temp_knative_serving_monolith.yaml
  rm tutorial/openshift_definitions/03/temp_knative_serving_monolith.yaml
fi