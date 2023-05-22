* knative eventing and serving operator and namespace
* kafka cluster with name my-cluster
* kafka connect cluster - push secret (see 4)


* oc new-app \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=knative_demo \
  -e PGDATA=/tmp/data/pgdata \
  quay.io/appdev_playground/wal_postgres:0.0.2 \
  --name postgres


* oc new-app \
  -e MONGO_INITDB_ROOT_USERNAME=mongo \
  -e MONGO_INITDB_ROOT_PASSWORD=mongo \
  mongo:4.2.24 \
  --name knative-mongo

* change namespace in tutorial/scripts/.namespace file
* Get docker config and run 
  oc create secret generic \
  kafka-connect-cluster-push-secret \
  --from-file=.dockerconfigjson=./rh-ee-mvandepe-auth.json \
  --type=kubernetes.io/dockerconfigjson

* sh tutorial/scripts/01_script.sh
* change kafka-channel-config config map `oc get knativekafka -n knative-eventing` `oc edit knativekafka knative-kafka -n knative-eventing`
    ==> my-cluster-kafka-bootstrap.<NAMESPACE>.svc.cluster.local:9092