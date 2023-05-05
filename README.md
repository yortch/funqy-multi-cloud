# Multi Cloud Funqy Demo

## Local

1. Launch application using:
    ```
    ./mvnw quarkus:dev
    ```
1. Test by issuing `curl` commands:
    ```
    URL=http://localhost:8080
    curl -X GET $URL/hello 
    curl -d '"Funqy"' -X POST $URL/greet
    ```

## Azure Function

### Pre-requisites

1. [AZ CLI](https://learn.microsoft.com/en-us/cli/azure/install-azure-cli)
1. Azure Functions Core Tools (optional to run function locally)

### Deploy

1. Login to Azure
    ```
    az login --service-principal -u $CLIENT_ID -p $PASSWORD --tenant $TENANT
    ```

1. Build Azure Function and deploy it to Azure
        
    ```
    ./mvnw clean package -f pom-azure.xml -DskipTests -DtenantId=$TENANT \
    -DfunctionResourceGroup=$RESOURCEGROUP azure-functions:deploy
    ```

1. Test by issuing `curl` commands with the URL from the deploy output:
    ```
    URL=<URL from deploy output without ending slash>
    curl -w '\nTime: %{time_total}s\n' -X GET $URL/hello 
    curl -w '\nTime: %{time_total}s\n' -d '"Azure"' -X POST $URL/greet
    ```

## AWS Lambda

### Pre-requisites

1. [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
1. [SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html)

### Deploy

1. Login to AWS
    ```
    aws configure
    ```

1. Build Lambda function
        
    ```
    ./mvnw clean package -f pom-aws.xml -DskipTests
    ```
1. Deploy Lambda to AWS
    ```
    sam deploy -t target/sam.jvm.yaml --guided --capabilities CAPABILITY_NAMED_IAM
    ```
   1. Alternatively you can use Native build and deploy to AWS
        ```
        ./mvnw clean package -f pom-aws.xml -DskipTests -Pnative -Dquarkus.native.container-build=true
        sam deploy -t target/sam.native.yaml --guided --capabilities CAPABILITY_NAMED_IAM
        ```

1. Test by issuing `curl` commands with the URL from the deploy output:
    ```
    URL=<URL from deploy output without ending slash>
    curl -w '\nTime: %{time_total}s\n' -X GET $URL/hello 
    curl -w '\nTime: %{time_total}s\n' -d '"AWS"' -X POST $URL/greet
    ```

## Google Cloud Function

### Pre-requisites

1. [gcloud CLI](https://cloud.google.com/sdk/docs/install)

### Deploy

1. Login to gcloud
    ```
    gcloud init
    ```

1. Build Google Cloud Function 
    ```
    ./mvnw clean package -f pom-gcp.xml -DskipTests
    ```

1. Deploy Function to GCP
    ```
    gcloud functions deploy quarkus-funqy-http \
        --entry-point=io.quarkus.gcp.functions.http.QuarkusHttpFunction \
        --runtime=java11 --trigger-http --allow-unauthenticated --source=target/deployment
    ```
1. Test by issuing `curl` commands with the URL from the deploy output:
    ```
    URL=<URL from deploy output>
    curl -w '\nTime: %{time_total}s\n' -X GET $URL/hello 
    curl -w '\nTime: %{time_total}s\n' -d '"GCP"' -X POST $URL/greet
    ```

## KNative

### Pre-requisites

1. oc (OpenShift) CLI
1. [kn CLI](https://docs.openshift.com/container-platform/4.12/serverless/install/installing-kn.html)
1. [Install OpenShift Serverless Operator](https://docs.openshift.com/container-platform/4.12/serverless/install/install-serverless-operator.html)
    1. Log into OpenShift Console with a cluster admin user
    1. Navigate to Operators/OperatorHub
    1. Search for Serverless and click on "Red Hat OpenShift Serverless"
    1. Click on Install
    1. Accept default values and click Install
    1. Once Operator is installed, create [Knative Serving](https://docs.openshift.com/container-platform/4.12/serverless/install/installing-knative-serving.html#installing-knative-serving) instance by running this command:
    ```
    oc apply -f knative/serving.yaml
    ```
### Deploy

1. Build KNative Function and deploy it to OpenShift:
    ```
    oc login --token=<token> --server=<api-url>
    oc new-project funqy-knative
    kn func deploy
    ```

    1. Alternatively you can deploy function using previously published public image:
    ```
    kn func deploy --build=false --registry quay.io/jbaldera --image quay.io/jbaldera/funqy-knative:1.0
    ```

1. Test by issuing `curl` commands with the URL from the deploy output:
    ```
    URL=<URL from deploy output>
    curl -w '\nTime: %{time_total}s\n' -X GET $URL/hello 
    curl -w '\nTime: %{time_total}s\n' -d '"KNative"' -X POST $URL/greet
    ```