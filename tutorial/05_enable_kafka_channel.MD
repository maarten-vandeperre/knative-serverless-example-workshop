* oc get kafka
* oc get pod
* oc exec -it knative-demo-cluster-kafka-0 \
  -- bin/kafka-console-producer.sh \
  --bootstrap-server knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092 \
  --topic knative-messaging-kafka.maarten-playground.kafka-person-data-changed-channel
*
* oc exec -it knative-demo-cluster-kafka-0 \
  -- bin/kafka-console-consumer.sh \
  --bootstrap-server knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092 \
  --topic knative-messaging-kafka.maarten-playground.kafka-person-data-changed-channel
* go to POD > psql -h localhost -d knative_demo -U postgres -W
* select * from people;
* go to POD > psql -h localhost -d knative_demo -U postgres -W


{
"id" : "1234",
"source" : "monolith",
"specversion" : "1.0",
"type" : "person-data-changed",
"data" : "{\"ref\":\"test\"}"
}

{"id" : "1234","source" : "monolith","specversion" : "1.0","type" : "person-data-changed","data" : "{\"ref\":\"test\"}"}


knative-messaging-kafka.maarten-playground.kafka-person-data-changed-channel

oc get channel




my-kafka-cluster-kafka-bootstrap.dieter.svc:9092

oc edit knativekafka knative-kafka -n knative-eventing;