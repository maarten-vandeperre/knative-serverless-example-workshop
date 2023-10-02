# Wrap up operator config
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
   ```oc whoami```  
   ```oc get pod```
   ![Install AMQ Streams operator step 1](img/5_configure_dev_spaces_5.png "Install AMQ Streams operator step 1")

