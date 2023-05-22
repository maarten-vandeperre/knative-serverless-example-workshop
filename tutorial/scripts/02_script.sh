#!/bin/sh
VERSION="0.0.27" #version of the application
NAMESPACE=$(cat tutorial/scripts/.namespace) #name of your OpenShift namespace
REBUILD=true #whether or not the application and image need to be rebuild
DOCKER_IMAGE="quay.io/appdev_playground/knative_demo:monolith-uberjar-$VERSION"
ROUTE_HOST="monolith-$NAMESPACE.apps.ocp4-bm.redhat.arrowlabs.be"

if $REBUILD
then
  #fix postgres url
  properties="$(cat application/configuration/monolith-configuration/src/main/resources/application.properties)"
  properties="$(echo "${properties//<NAMESPACE>/$NAMESPACE}")"
  mv application/configuration/monolith-configuration/src/main/resources/application.properties application/configuration/monolith-configuration/src/main/resources/application_backup.properties
  echo "$properties" > application/configuration/monolith-configuration/src/main/resources/application.properties
  #build uber jar for monolith
  ./mvnw package -Dquarkus.package.type=uber-jar -Pmonolith
  #dockerize it
  docker build -t $DOCKER_IMAGE -f ./application/configuration/monolith-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/monolith-configuration --platform linux/amd64
  #push docker image
  docker push $DOCKER_IMAGE
  mv application/configuration/monolith-configuration/src/main/resources/application_backup.properties application/configuration/monolith-configuration/src/main/resources/application.properties
fi

#create deployment
config="$(cat tutorial/openshift_definitions/02/deployment_config.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
config="$(echo "${config//<DOCKER_IMAGE>/$DOCKER_IMAGE}")"
echo "$config" > tutorial/openshift_definitions/02/temp_deployment_config.yaml
oc apply -f tutorial/openshift_definitions/02/temp_deployment_config.yaml
rm tutorial/openshift_definitions/02/temp_deployment_config.yaml

#create service
config="$(cat tutorial/openshift_definitions/02/8080_service_config.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
echo "$config" > tutorial/openshift_definitions/02/temp_8080_service_config.yaml
oc apply -f tutorial/openshift_definitions/02/temp_8080_service_config.yaml
rm tutorial/openshift_definitions/02/temp_8080_service_config.yaml

#create route
config="$(cat tutorial/openshift_definitions/02/8080_route_config.yaml )"
config="$(echo "${config//<VERSION>/$VERSION}")"
config="$(echo "${config//<NAMESPACE>/$NAMESPACE}")"
config="$(echo "${config//<HOST>/$ROUTE_HOST}")"
echo "$config" > tutorial/openshift_definitions/02/temp_8080_route_config.yaml
oc apply -f tutorial/openshift_definitions/02/temp_8080_route_config.yaml
rm tutorial/openshift_definitions/02/temp_8080_route_config.yaml