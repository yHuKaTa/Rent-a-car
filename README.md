# Rent-a-car

## Description

`Rent-a-car` is an simple REST API using Spring Boot which provides the user with the following functionalities:
* Register new user
* Edit profile information
* Recieve information from profile
* Book a car
  
## Launching

Open "Terminal" or "CMD". Insert in terminal -> 'project_location'/java -jar rent_a_car.jar Test the app with "POSTMAN" or every else util.

## Endpoints

### UserLayer

GET -> http://localhost:8080/user?email=email@address.domain

GET -> http://localhost:8080/user/{userId}

POST -> http://localhost:8080/user/add

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

PUT -> http://localhost:8080/user/changePassword

```
{
    "email" : "email@address.domain",
    "password" : "Password1",
    "newPassword" : "Password2"
}
```

DELETE -> http://localhost:8080/user/{userId}/delete

### CarLayer

GET -> http://localhost:8080/car/{carId}

POST -> http://localhost:8080/car/add

```
{
    "brand" : "SomeBrand",
    "model" : "SomeModel",
    "seats" : "5",
    "dayPrice" : "20.50"
}
```

PUT -> http://localhost:8080/car/change

```
{
    "id" : "1",
    "newDayPrice" : "21.50",
}
```

DELETE -> http://localhost:8080/car/{carId}/delete

### ReservationLayer

GET -> http://localhost:8080/reservation/{reservationId}

GET -> http://localhost:8080/reservation/getWithUserEmail?userEmail=email@address.domain

GET -> http://localhost:8080/reservation/getWithCarId?carId=1

GET -> http://localhost:8080/reservation/getWithPeriod?startingDate=01/01/2000&endDate=31/12/2099

POST -> http://localhost:8080/reservation/add

```
{
    "carId" : "1",
    "userEmail" : "email@address.domain",
    "startDate" : "01/01/2000",
    "endDate" : "31/12/2000"
}
```

PUT -> http://localhost:8080/reservation/{reservationId}/changeCar

```
{
    "carId" : "1",
}
```

PUT -> http://localhost:8080/reservation/{reservationId}/changePeriod

```
{
    "newEndDate" : "31/12/2099",
}
```

DELETE -> http://localhost:8080/reservation/{reservationId}/delete
