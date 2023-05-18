* oc login
* oc new-app \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=knative_demo \
  -e PGDATA=/tmp/data/pgdata \
  postgres:15.3-alpine
* change service url in quarkus properties
* go to POD > psql -h localhost -d knative_demo -U postgres -W
* copy-paste the db-init-scrips/postgres 
* or
* oc get pod | grep postgres
* oc port-forward postgres-57687548d9-cjwt2 5432:5432


* ./mvnw package -Dquarkus.package.type=uber-jar -Pmonolith
*  docker build -t quay.io/appdev_playground/knative_demo:monolith-uberjar-0.0.1 -f ./application/configuration/monolith-configuration/src/main/docker/Dockerfile_UberJar ./application/configuration/monolith-configuration --platform linux/amd64
* docker run -p 8080:8080 quay.io/appdev_playground/knative_demo:monolith-uberjar-0.0.1
* docker push quay.io/appdev_playground/knative_demo:monolith-uberjar-0.0.1
* oc apply -f tutorial/openshift_definitions/02/deployment_config.yaml
* sh tutorial/scripts/02_script.sh




* https://monolith-maarten-playground.apps.ocp4-bm.redhat.arrowlabs.be/q/graphql-ui/?query=query%20%7B%0A%20%20allAccounts%20%7B%0A%20%20%20%20firstName%0A%20%20%20%20lastName%0A%20%20%7D%0A%7D
* https://monolith-maarten-playground.apps.ocp4-bm.redhat.arrowlabs.be/q/openapi?format=json
* https://monolith-maarten-playground.apps.ocp4-bm.redhat.arrowlabs.be/q/swagger-ui/

