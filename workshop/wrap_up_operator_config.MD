# Wrap up operator config

1. [Dev spaces](#dev-spaces)
2. [Metadata configuration](#metadata-configuration)
3. [AMQ Streams - Kafka](#amq-streams---kafka)
4. [OpenShift serverless - Knative](#openshift-serverless---knative)

## Dev spaces
1. Open specifications tab.  
   ![Install AMQ Streams operator step 1](img/2_configure_dev_spaces_1.png "Install AMQ Streams operator step 1")
  

2. Click "create Che cluster":  
   _A cloud development environment (CDE) service for OpenShift.
   Built on the open source Eclipse Che project, Red Hat OpenShift Dev Spaces uses Kubernetes and containers
   to provide developers and other IT team members with a consistent, secure, and zero-configuration development environment._
   ![Install AMQ Streams operator step 1](img/2_create_dev_spaces_2.png "Install AMQ Streams operator step 1")
 

3. Create Che cluster with default settings.
   ![Install AMQ Streams operator step 1](img/2_create_dev_spaces_3.png "Install AMQ Streams operator step 1")
 

4. Select the route for your OpenShift dev space. 
   ![Install AMQ Streams operator step 1](img/2_dev_spaces_route.png "Install AMQ Streams operator step 1")
   Or
   ![Install AMQ Streams operator step 1](img/2_dev_spaces_route2.png "Install AMQ Streams operator step 1")
 

5. Click "Login with OpenShift".
   ![Install AMQ Streams operator step 1](img/2_dev_spaces_login_screen.png "Install AMQ Streams operator step 1")
 

6. Fill in login credentials.
   ![Install AMQ Streams operator step 1](img/5_configure_dev_spaces_1_log_in.png "Install AMQ Streams operator step 1")
 

7. Allow the selected permissions.
   ![Install AMQ Streams operator step 1](img/5_configure_dev_spaces_2.png "Install AMQ Streams operator step 1")
 

8. Link the current workshop GitHub repository (https://github.com/maarten-vandeperre/knative-serverless-example-workshop). 
   ![Install AMQ Streams operator step 1](img/5_configure_dev_spaces_3_workspace.png "Install AMQ Streams operator step 1")
 

9. Wait for the provisioning of the workspace.  
   ![Install AMQ Streams operator step 1](img/5_configure_dev_spaces_4_provisioning.png "Install AMQ Streams operator step 1")
 

10. Open a terminal and start exploring. E.g.,  
      ```shell
      oc whoami
      ``` 
      ```shell
      oc get pod
      ``` 
      ![Install AMQ Streams operator step 1](img/5_configure_dev_spaces_5.png "Install AMQ Streams operator step 1")

## Metadata configuration
1. Change the content of the tutorial/scripts/.namespace file (in the dev spaces workspace) to your 
project name. _For us, it is "demo-project"_
2. Change the content of the tutorial/scripts/.root_domain file (in the dev spaces workspace) to your 
base domain from the OpenShift sandbox. _For us, it is "apps.cluster-gq27g.gq27g.sandbox3037.opentlc.com"_
![Install AMQ Streams operator step 1](img/base_domain.png "Install AMQ Streams operator step 1")



## AMQ Streams - Kafka
1. Open Kafka tab.  
   ![Install AMQ Streams operator step 1](img/3_configure_kafka_1.png "Install AMQ Streams operator step 1")


2. Click "create Kafka".
   ![Install AMQ Streams operator step 1](img/3_configure_kafka_2.png "Install AMQ Streams operator step 1")
 

3. Click "create Kafka with default settings".  
   ![Install AMQ Streams operator step 1](img/3_configure_kafka_3.png "Install AMQ Streams operator step 1")
  

4. Check for resources to be provisioned.
   ![Install AMQ Streams operator step 1](img/3_configure_kafka_4_resources.png "Install AMQ Streams operator step 1")
   

5. When all resources are available, the cluster status should reach the ready state.
   ![Install AMQ Streams operator step 1](img/3_configure_kafka_5_status_ready.png "Install AMQ Streams operator step 1")


6. Create the Kafka connect cluster (Debezium connector):  
   **!!! Make sure that you have configured the metadata as described in previous section !!!**  
   _Execute the following command in the terminal of the dev space, in the project root:_  
   ```shell
   sh tutorial/scripts/01_script.sh
   ```


7. Created Kafka Connect cluster should be visible in the UI.
   ![Install AMQ Streams operator step 1](img/3_configure_kafka_6_kafka_connect_1.png "Install AMQ Streams operator step 1")



## OpenShift serverless - Knative
1. Open "Knative Eventing" tab.  
   ![Install AMQ Streams operator step 1](img/2_configure_serverless_eventing_1.png "Install AMQ Streams operator step 1")


2. Click "create Knative eventing".  
   **!!! This should be created in the "knative-eventing" namespace !!!**
   ![Install AMQ Streams operator step 1](img/2_configure_serverless_eventing_2.png "Install AMQ Streams operator step 1")
 

3. Open "Knative Serving" tab.   
   ![Install AMQ Streams operator step 1](img/2_configure_serverless_serving_1.png "Install AMQ Streams operator step 1")
  

4. Click "create Knative serving".  
   **!!! This should be created in the "knative-serving" namespace !!!**
   ![Install AMQ Streams operator step 1](img/2_configure_serverless_serving_2.png "Install AMQ Streams operator step 1")
   

5. Create with default values.
   ![Install AMQ Streams operator step 1](img/2_configure_serverless_serving_3.png "Install AMQ Streams operator step 1")
   

6. When all resources are provisioned, you should be able to see the serverless section for all namespaces.  
   We'll check for our "demo-project" namespace.
   ![Install AMQ Streams operator step 1](img/2_serverless_validate_eventing_and_serving.png "Install AMQ Streams operator step 1")


7. Open "Knative Kafka" tab.  
   ![Install AMQ Streams operator step 1](img/4_configure_knative_kafka.png "Install AMQ Streams operator step 1")
   


8. Create the Knative Kafka cluster. !!! list all the brokers (from the namespaces) you want to use.  
   ![Install AMQ Streams operator step 1](img/4_configure_knative_kafka_2.png "Install AMQ Streams operator step 1")
   



   

