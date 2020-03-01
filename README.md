# sgcib-bank-account

## Requirements
* Git
* Java 8
* Maven 3.6

## How to launch

Download the code source from github:
```
git clone https://github.com/quanghuytran33/sgcib-bank-account.git
```

Generate package Java with this code:
``` 
mvn clean install
```

Then launch the application
``` 
java -jar target/sgcib-bank-account-0.0.1-SNAPSHOT.jar
```

## How to test

After launching the application, you can test the api via Swagger UI 
by connecting to this URL

``
http://localhost:9999/api/swagger-ui.html
``

For test purpose, there already have a bank account which id is 1.

You can also retrieve the bank accounts 
from database by calling the API (via curl or Swagger)

* curl:
``
curl -X GET "http://localhost:9999/api/account" -H  "accept: application/json"
``

* Swagger:
![alt text](https://github.com/quanghuytran33/sgcib-bank-account/blob/master/resources/getBankAccountController.png "bankAccountController")