# Rent-a-car

## Description
`Rent-a-car` is an simple REST API using Spring Boot which provides the user with the following functionalities:
* Register new user
* Edit profile information
* Recieve information from profile
* Book a car
## Launching
Open "Terminal" or "CMD". Insert in terminal -> 'project_location'/java -jar rent_a_car.jar Test the app with "POSTMAN" or every else util :)
##Endpoints '/n'
###UserLayer '/n'
*GET -> http://localhost:8080/user?email=email@address.domain '/n'
*GET -> http://localhost:8080/user/1  '/n'
*POST -> http://localhost:8080/user/add '/n'
         BODY :
```
{
    "firstName" : "FirstName",
    "lastName" : "LastName",
    "passportId" : "000000000",
    "phoneNumber" : "0000000",
    "email" : "email@address.domain",
    "password" : "Password1"
}
```
*PUT -> http://localhost:8080/user/changePassword
        BODY:
```
{
    "email" : "email@address.domain",
    "password" : "Password1",
    "newPassword" : "Password2"
}
```
*DELETE -> http://localhost:8080/user/1/delete '/n'
