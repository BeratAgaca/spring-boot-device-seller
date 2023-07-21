# Spring Boot Device Seller App

## Endpoints

### Sign-up
````
POST /api/authentication/sign-up HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 77

{
    "name": "admin",
    "username": "admin",
    "password": "admin"
}
````


### Sign-in
````
POST /api/authentication/sign-in HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 53

{
    "username": "user",
    "password": "user"
}
````

### Change-Role
````
PUT /api/user/change/ADMIN HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9VU0VSIiwidXNlcklkIjoyLCJleHAiOjE2ODk5NjMxODd9.nfvYjTzsdVPjW3wjvi2Kr6apFiQ2fdd37b_v5LIl6YWelrMDRanQ7TTx4wWpm4y8UhDO-6mA2KSSv3dmxjKGyg
````

### Save Device
````
POST /api/device HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsInVzZXJJZCI6MiwiZXhwIjoxNjg5OTY0MDk0fQ._lo-Me4__2QuVeKPxO8X8Am6_Q0b2fz6D1vbJ52DBEPhBm5eXfIOVjlkGkiPgY3R8HjX-0cMlq0qDaQ7nAcY2Q
Content-Length: 106

{
    "name": "device-2",
    "description": "desc-2",
    "price":1000,
    "deviceType": "TABLET"
}
````

### Delete Device
````
DELETE /api/device/1 HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsInVzZXJJZCI6MiwiZXhwIjoxNjg5OTY0MDk0fQ._lo-Me4__2QuVeKPxO8X8Am6_Q0b2fz6D1vbJ52DBEPhBm5eXfIOVjlkGkiPgY3R8HjX-0cMlq0qDaQ7nAcY2Q
````

### Get All Devices
````
GET /api/device HTTP/1.1
Host: localhost:8080
````

### Save Purchase
````
POST /api/purchase HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsInVzZXJJZCI6MiwiZXhwIjoxNjg5OTY0MDk0fQ._lo-Me4__2QuVeKPxO8X8Am6_Q0b2fz6D1vbJ52DBEPhBm5eXfIOVjlkGkiPgY3R8HjX-0cMlq0qDaQ7nAcY2Q
Content-Length: 83

{
    "userId": 1,
    "deviceId": 2,
    "price": 1000,
    "color": "blue"
}
````

### Get All Purchases of User
````
GET /api/purchase HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOiJST0xFX1VTRVIiLCJ1c2VySWQiOjEsImV4cCI6MTY4OTk2NTg1Mn0.cqpef-5nmW3xcvpw9fn0K6V9vLsCG32832pUHkeyo1KlyRV4g_5bGforcOa-wj2kk-FIFNr15X8RD3EznDkp1Q
````