# Knative Serverless Example - Workshop
# Let's Dive into Knative Serverless with This Workshop!
## What's the Game Plan?
This workshop breaks down into a few simple steps:

1. Create a new project in OpenShift. We'll go for _"demo-project"_.
2. The domain model we will use, consists of 3 entities:
   1. A person entity: name, birthdate (optional) and address (optional).
   2. An address entity: street, house number, city, ....
   3. An account entity: an aggregate of the previous two: when a person gets linked to an 
   address, it becomes an account.
3. First up, we're going to get our OpenShift environment all prepped and ready to roll. 
We'll be installing some operators and letting them do their thing, 
all while we give you the lowdown on serverless and Knative basics 
(we might even throw in a snazzy PowerPoint presentation). 
Think of it as us stepping into the shoes of the infrastructure team or admins 
for a bit. We're gonna slap in:
   * The OpenShift Dev Spaces operator
   * The AMQ Streams operator
   * The OpenShift Serverless operator

    Once these operators are locked and loaded, we'll start configuring OpenShift 
    Serverless eventing and serving, 
    as well as AMQ Streams (that's Kafka and Kafka Connect - Debezium).  
  
    If you want the nitty-gritty details, you can find 'em right [here in our Step 1 documentation](workshop/install_and_configure_the_operators.MD).

4. Now, time for a [little presentation](workshop/presentation.pdf). We'll break down serverless concepts and give you an explanation on how OpenShift Serverless (aka Knative) is structured. 
In order to speed up things, take the first 3 steps of next section before starting the presentation (i.e., up until the click "create Che cluster").
5. Almost there, we now will wrap up the configuration of AMQ Streams, Dev Spaces and OpenShift Serverless.  
   For the full scoop on this step, check out [our Step 3 documentation](workshop/wrap_up_operator_config.MD).
6. And now, it's our turn to play the technical leads or DevOps wizards. We're going to take that big ol' monolithic app and start breaking it down into some snazzy serverless (micro-)services. 
It's gonna be like digital Jenga!

    For the full scoop on this step, check out [our Step 4 documentation](workshop/decompose_the_monolith.MD).

Get ready to dive into the world of Knative Serverless with us! 🚀

## Extra: enable CamelK
//TODO extend text
* Install CamelK operator
  * Create Binding:
  Go to YAML view (and wait some minutes for integration and binding to be created)
   ```yaml
     apiVersion: camel.apache.org/v1
     kind: KameletBinding
     metadata:
      name: log-sink-binding
      namespace: demo-project
     spec:
       source:
         ref:
           kind: KafkaChannel
           apiVersion: messaging.knative.dev/v1beta1
           name: kafka-address-data-changed-channel
       sink:
         ref:
           kind: Kamelet
           apiVersion: camel.apache.org/v1
           name: log-sink
   ```
* wget https://mirror.openshift.com/pub/openshift-v4/clients/camel-k/1.10.2/camel-k-client-1.10.2-linux-64bit.tar.gz
* tar -xvf camel-k-client-1.10.2-linux-64bit.tar.gz 
* ./kamel get
* ./kamel log log-sink-binding
