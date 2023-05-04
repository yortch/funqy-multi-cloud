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
## Azure 

### Pre-requisites

1. AZ CLI
1. Azure Functions Core Tools (optional to run function locally)

### Deploy

1. Login to Azure
    ```
    az login --service-principal -u $CLIENT_ID -p $PASSWORD --tenant $TENANT
    ```

1. Deploy to Azure
        
    ```
    ./mvnw clean package -DskipTests -DtenantId=$TENANT -DfunctionResourceGroup=$RESOURCE azure-functions:deploy
    ```
