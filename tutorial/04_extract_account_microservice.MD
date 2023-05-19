* oc login
* enable kafka connect
* oc new-app \
  -e MONGO_INITDB_ROOT_USERNAME=mongo \
  -e MONGO_INITDB_ROOT_PASSWORD=mongo \
  mongo:4.2.24 \
  --name knative-mongo
* https://role.rhu.redhat.com/rol-rhu/app/courses/do244-1.23/pages/ch03
* oc get kafka
* oc get pod
* oc exec -it knative-demo-cluster-kafka-0 \
  -- bin/kafka-console-producer.sh \
  --bootstrap-server knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092 \
  --topic person-data-changed-topic
*
* oc exec -it knative-demo-cluster-kafka-0 \
  -- bin/kafka-console-consumer.sh \
  --bootstrap-server knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092 \
  --topic person-data-changed-topic
* go to POD > psql -h localhost -d knative_demo -U postgres -W
* select * from people;

* connect bootstrap url knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092

* oc exec -n dieter -it my-kafka-cluster-kafka-0 \
  -- bin/kafka-console-producer.sh \
  --bootstrap-server knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092 \
  --topic person-data-changed-topic 



* oc exec -it knative-demo-cluster-kafka-0 \
  -- bin/kafka-console-consumer.sh \
  --bootstrap-server knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092 \
  --topic dbhistory.people


{
"id" : "1234", 
"source" : "monolith", 
"specversion" : "1.0", 
"type" : "person-data-changed", 
"data" : "{\"ref\":\"test\"}" 
}

{"id" : "1234","source" : "monolith","specversion" : "1.0","type" : "person-data-changed","data" : "{\"ref\":\"test\"}"}



oc create secret generic \
kafka-connect-cluster-push-secret \
--from-file=.dockerconfigjson=/rh-ee-mvandepe-auth.json \
--type=kubernetes.io/dockerconfigjson



{
"name": "people-connector",  
"config": {
"connector.class": "io.debezium.connector.postgresql.PostgresConnector",
"database.hostname": "postgres.maarten-playground.svc.cluster.local",
"database.port": "5432",
"database.user": "postgres",
"database.password": "postgres",
"database.dbname" : "knative_demo",
"database.server.name": "db-people",
"table.whitelist": "public.people"

}
}