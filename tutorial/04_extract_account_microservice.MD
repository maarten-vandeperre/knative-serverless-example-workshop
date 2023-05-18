* oc login
* oc new-app \
  -e MONGO_INITDB_ROOT_USERNAME=mongo \
  -e MONGO_INITDB_ROOT_PASSWORD=mongo \
  mongo:4.2.24
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