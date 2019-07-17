# Account service 

## Overview
With the help of this service you can handle accounts and make transfers between them

## Prerequisites
To build from source, you need the following installed (and available in your $PATH):

* [Java 8+](http://java.oracle.com)

* [Apache Maven 3 or greater](http://maven.apache.org/)

## Building
Run the tests with:
```
mvn clean test
```
Create the runnable jar:
```
mvn clean package -DskipTests
```
All at once, run the tests, create the jar and install the jar to your local maven repository
```
mvn clean install
```

## Running the application
After packaging you can find the runnable jar in the target directory and run with the following command :
```
java -jar account-service-0.1.0-SNAPSHOT-runnable.jar
```
The application runs on port 8080

##Endpoints
#### Account CRUD endpoints
Create account
```
POST ${server}:8080/accounts
{
  "accountNumber": "1234-1234-1234",
  "accountType": "STANDARD",
  "customerId": "Customer01",
  "currency": "EUR",
  "balance": 3000
}
```
Get all accounts
```
GET ${server}:8080/accounts
```
Get specific account by accountNumber
```
GET ${server}:8080/accounts/${accountNumber}
```
Delete account
```
DELETE ${server}:8080/accounts/${accountNumber}
```
Modify account
```
PUT ${server}:8080/accounts/${accountNumber}
{
  "accountNumber": "1234-1234-1234",
  "accountType": "STANDARD",
  "customerId": "Customer01",
  "currency": "EUR",
  "balance": 4000
}
```
####Transfer endpoint
Make transfer
```
POST ${server}:8080/transfer
{
  "sourceAccountNumber": "5678-5678-5678",
  "targetAccountNumber": "1234-1234-1234",
  "amount": 6000,
  "currency": "HUF"
}
```
####Transaction endpoint
Query transactions for an account
```
GET ${server}:8080/transactions/${accountNumber}
```
