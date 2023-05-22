* knative eventing and serving operator and namespace
* kafka cluster with name my-cluster
* change kafka-channel-config config map `oc get knativekafka -n knative-eventing` `oc edit knativekafka knative-kafka -n knative-eventing`
==> my-cluster-kafka-bootstrap.<NAMESPACE>.svc.cluster.local:9092
* kafka connect cluster - push secret (see 4)