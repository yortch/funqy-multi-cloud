# Multi Cloud Funqy Demo

## Local
URL=localhost:8080

curl -X GET $URL/hello 
curl -d '"Dan"' -X POST $URL/greeting

## Azure 

### Pre-requisites

1. AZ CLI
1. Azure Functions Core Tools

### Deploy

1. Login to Azure
    ```
    az login --service-principal -u $CLIENT_ID -p $PASSWORD --tenant $TENANT
    ```

1. Deploy to Azure
    
    ```
    mvn clean install -DskipTests -DtenantId=$TENANT -DfunctionResourceGroup=$RESOURCE azure-functions:deploy
    ```
