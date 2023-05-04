# Multi Cloud Funqy Demo

## Local

1. Launch application using:
    ```
    ./mvnw quarkus:dev
    ```
1. Test by issuing `curl` commands:
    ```
    URL=http://localhost:8080/api
    curl -X GET $URL/hello 
    curl -d '"Funqy"' -X POST $URL/greet
    ```

## Azure Function

### Pre-requisites

1. AZ CLI
1. Azure Functions Core Tools (optional to run function locally)

### Deploy

1. Login to Azure
    ```
    az login --service-principal -u $CLIENT_ID -p $PASSWORD --tenant $TENANT
    ```

1. Build and Deploy to Azure
        
    ```
    ./mvnw clean package -f pom-azure.xml -DskipTests -DtenantId=$TENANT -DfunctionResourceGroup=$RESOURCE azure-functions:deploy
    ```

1. Test by issuing `curl` commands with the URL from the deploy output:
    ```
    URL=<URL from deploy output>
    curl -X GET $URL/hello 
    curl -d '"Azure"' -X POST $URL/greet
    ```

## AWS Lambda

### Pre-requisites

1. AWS CLI
1. SAM CLI

### Deploy

1. Login to AWS
    ```
    aws configure
    ```

1. Build and Deploy to AWS
        
    ```
    ./mvnw clean package -f pom-aws.xml -DskipTests
    sam deploy -t target/sam.jvm.yaml --guided --capabilities CAPABILITY_NAMED_IAM
    ```
   1. Alternatively you can use Native build and deploy to AWS
        ```
        ./mvnw clean package -f pom-aws.xml -DskipTests -Pnative -Dquarkus.native.container-build=true
        sam deploy -t target/sam.native.yaml --guided --capabilities CAPABILITY_NAMED_IAM
        ```

1. Test by issuing `curl` commands with the URL from the deploy output:
    ```
    URL=<URL from deploy output>/api
    curl -X GET $URL/hello 
    curl -d '"Lambda"' -X POST $URL/greet
    ```

## Google Cloud Function

### Pre-requisites

1. gcloud CLI

### Deploy

1. Login to gcloud
    ```
    gcloud init
    ```

1. Build and Deploy to GCP
    ```
    ./mvnw clean package -f pom-gcp.xml -DskipTests
    gcloud functions deploy quarkus-funqy-http \
        --entry-point=io.quarkus.gcp.functions.http.QuarkusHttpFunction \
        --runtime=java11 --trigger-http --allow-unauthenticated --source=target/deployment

    ```
1. Test by issuing `curl` commands with the URL from the deploy output:
    ```
    URL=<URL from deploy output>/api
    curl -X GET $URL/hello 
    curl -d '"GCP"' -X POST $URL/greet
    ```