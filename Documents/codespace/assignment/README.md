# Book Store Service Project

## Getting started
### IDE
1. Install IDE: IntelliJ
2. Install IntelliJ plugin: Lombok

### Running application
1. In terminal, at backend project root, run the following command.
    ```
    mvn clean compile spring-boot:run
    ```
2. Application will be running on host URL: http://localhost:8080

### Curl Command
1. Get all Books
   ```
    curl --location --request GET 'http://localhost:8080/books'
   ```
2. Create New user
    ```
    curl --location --request POST 'http://localhost:8080/users' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username" : "admin",
        "password" : "admin",
        "date_of_birth": "15/01/1985",
        "name": "name",
        "surname" : "surname"
    
    }'
    ```
3. Authenicate new user for get token
    ```
   curl --location --request POST 'http://localhost:8080/login' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username" : "admin",
        "password" : "admin"
    }'
    ```
   You can get token in header of response in key name 'Authorization'
   eg.
    ```
   'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxNzM0OTgyMSwiaWF0IjoxNjE3MzMxODIxfQ.ZHF6WshYTfmYm4lwuhTd4Ju9WwaYIUknbO2iSTkpMc0i0cmu6DDSFdbHSbEbxJrOoZ7zw-SBy6IPlsTQ2iGRSA'
    ```
4. Add new order
    ```
   curl --location --request POST 'http://localhost:8080/users/orders' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxNzM0OTgyMSwiaWF0IjoxNjE3MzMxODIxfQ.ZHF6WshYTfmYm4lwuhTd4Ju9WwaYIUknbO2iSTkpMc0i0cmu6DDSFdbHSbEbxJrOoZ7zw-SBy6IPlsTQ2iGRSA' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "orders": [
          1,4
       ]
   }'
    ```
   
5. Get list order
    ```
      curl --location --request GET 'http://localhost:8080/users' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxNzM0OTgyMSwiaWF0IjoxNjE3MzMxODIxfQ.ZHF6WshYTfmYm4lwuhTd4Ju9WwaYIUknbO2iSTkpMc0i0cmu6DDSFdbHSbEbxJrOoZ7zw-SBy6IPlsTQ2iGRSA'
    ```
6. Delete user
    ```
      curl --location --request DELETE 'http://localhost:8080/users' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxNzM0OTgyMSwiaWF0IjoxNjE3MzMxODIxfQ.ZHF6WshYTfmYm4lwuhTd4Ju9WwaYIUknbO2iSTkpMc0i0cmu6DDSFdbHSbEbxJrOoZ7zw-SBy6IPlsTQ2iGRSA'
    ```
