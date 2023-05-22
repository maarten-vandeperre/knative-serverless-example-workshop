knative-messaging-kafka.maarten-playground.orders-dispatcher-kne-trigger


oc exec -it knative-demo-cluster-kafka-0 \
-- bin/kafka-console-producer.sh \
--bootstrap-server knative-demo-cluster-kafka-bootstrap.maarten-playground.svc.cluster.local:9092 \
--topic knative-messaging-kafka.maarten-playground.orders-dispatcher-kne-trigger