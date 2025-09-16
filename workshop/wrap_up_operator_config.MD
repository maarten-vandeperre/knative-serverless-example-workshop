# Wrap up operator config

1. [Dev spaces](#dev-spaces)
2. [Metadata configuration](#metadata-configuration)
3. [AMQ Streams - Kafka](#amq-streams---kafka)
4. [OpenShift serverless - Knative](#openshift-serverless---knative)

## Dev spaces
1. Open specifications tab.  
   ![](img/2_configure_dev_spaces_1.png "")
  

2. Click "create Che cluster":  
   _A cloud development environment (CDE) service for OpenShift.
   Built on the open source Eclipse Che project, Red Hat OpenShift Dev Spaces uses Kubernetes and containers
   to provide developers and other IT team members with a consistent, secure, and zero-configuration development environment._
   ![](img/2_create_dev_spaces_2.png "")
 

3. Create Che cluster with default settings.
   ![](img/2_create_dev_spaces_3.png "")
 

4. Select the route for your OpenShift dev space (it can take some minutes for this to pop up). 
   ![](img/2_dev_spaces_route.png "")
   Or
   ![](img/2_dev_spaces_route2.png "")
 

5. Click "Login with OpenShift".
   ![](img/2_dev_spaces_login_screen.png "")
 

6. Fill in login credentials.
   ![](img/5_configure_dev_spaces_1_log_in.png "")
 

7. Allow the selected permissions.
   ![](img/5_configure_dev_spaces_2.png "")
 

8. Link the current workshop GitHub repository (https://github.com/maarten-vandeperre/knative-serverless-example-workshop). 
   ![](img/5_configure_dev_spaces_3_workspace.png "")
 

9. Wait for the provisioning of the workspace (can take some minutes as well).  
   ![](img/5_configure_dev_spaces_4_provisioning.png "")
 

10. Open a terminal and start exploring. E.g.,  
      ```shell
      oc whoami
      ``` 
      ```shell
      oc get pod
      ``` 
      ![](img/5_configure_dev_spaces_5.png "")

## Metadata configuration
1. Make sure you're using the created project  
   ```shell
   oc project demo-project
   ```
2. Change the content of the tutorial/scripts/.namespace file (in the dev spaces workspace) to your 
project name. _For us, it is "demo-project"_
3. Change the content of the tutorial/scripts/.root_domain file (in the dev spaces workspace) to your
   base domain from the OpenShift sandbox. _For us, it is "apps.cluster-gq27g.gq27g.sandbox3037.opentlc.com"_
   ![](img/base_domain.png "")
4. Do a find and replace on the domain
   ![](img/replace_in_files_1.png "")
   ![](img/replace_in_files_2.png "")
5. Fetch a Quay.io CLI secret in order to execute docker push commands to your Quay repository (will be required for debezium).
      1. Go to quay.io.
         ![](img/quay_1_select_user.png "")
      2. Go to user settings.
         ![](img/quay_2_go_to_user_settings.png "")
      3. Click "generate encrypted password"
         ![](img/quay_3_generate_encrypted_password.png "")
      4. Go to Docker config and view the password.
         ![](img/quay_passwd.png "")
      5. Store the encrypted password in rh-ee-mvandepe-auth.json file in the root of the project.
         (i.e., replace <to be replaced> with it).
      6. Store configuration in a secret
         ```shell
            oc create secret generic \
                kafka-connect-cluster-push-secret \
                --from-file=.dockerconfigjson=./rh-ee-mvandepe-auth.json \
                --type=kubernetes.io/dockerconfigjson
         ```



## AMQ Streams - Kafka
1. Open Kafka tab.  
   ![](img/3_configure_kafka_1.png "")


2. Click "create Kafka" (in the current namespace).
   ![](img/3_configure_kafka_2.png "")
 

3. Click "create Kafka with default settings".  
   ![](img/3_configure_kafka_3.png "")
  

4. Check for resources to be provisioned.
   ![](img/3_configure_kafka_4_resources.png "")
   

5. When all resources are available, the cluster status should reach the ready state.
   ![](img/3_configure_kafka_5_status_ready.png "")


6. Create the Kafka connect cluster (Debezium connector):  
   **!!! Make sure that you have configured the metadata as described in previous section !!!**  
   _Execute the following command in the terminal of the dev space, in the project root:_  
   ```shell
   sh tutorial/scripts/01_script.sh
   ```


7. Created Kafka Connect cluster should be visible in the UI.
   ![](img/3_configure_kafka_6_kafka_connect_1.png "")



## OpenShift serverless - Knative
1. Open "Knative Eventing" tab.  
   ![](img/2_configure_serverless_eventing_1.png "")


2. Click "create Knative eventing".  
   **!!! This should be created in the "knative-eventing" namespace !!!**
   ![](img/2_configure_serverless_eventing_2.png "")
 

3. Open "Knative Serving" tab.   
   ![](img/2_configure_serverless_serving_1.png "")
  

4. Click "create Knative serving".  
   **!!! This should be created in the "knative-serving" namespace !!!**
   ![](img/2_configure_serverless_serving_2.png "")
   

5. Create with default values.
   ![](img/2_configure_serverless_serving_3.png "")
   

6. When all resources are provisioned, you should be able to see the serverless section for all namespaces.  
   We'll check for our "demo-project" namespace.
   ![](img/2_serverless_validate_eventing_and_serving.png "")


7. Open "Knative Kafka" tab.  
   ![](img/4_configure_knative_kafka.png "")
   


8. Create the Knative Kafka cluster. !!! list all the brokers (from the namespaces) you want to use for broker and channel and enable source and sink. 
 If you followed our namings, 
it should be:  
   ```shell
   my-cluster-kafka-bootstrap.demo-project.svc.cluster.local:9092
   ```
   ![](img/4_configure_knative_kafka_2_channel.png "")
   ![](img/4_configure_knative_kafka_2_broker.png "")
   ![](img/4_configure_knative_kafka_2_source_and_sink.png "")
   



   

