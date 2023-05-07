
## Technical Details
The service is written in Java (17) and uses Micronaut as an underlying framework.
As I'm not a great front end developer, I decided to use swagger-ui to "expose" the service to user.
To access, after run the application, access http://localhost:8080/swagger-ui in a browser. 

### Code Structure
I strove  to write a code that follows the [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) approach.

### Limitations
The GitHub api has a rate limit of 10 requests per minute, after reach the limit, it returns 403.
This service forward the 403 to the user. 

## Running the service with an IDE

Import the service in an IDE, and execute the main class `com.discoverrepository.Application` to start the service.

## Running the service without an IDE
Access the directory contains the code

Start the service using the command below:

```sh
./gradlew run
```
## Interacting with the API
Run the following command to query the repositories:
```sh
curl -X 'GET' \
  'http://localhost:8080/repositories?createdAfter=2014-01-01&language=java&limit=5' \
  -H 'accept: application/json'
```
or use the swagger-ui will at http://localhost:8080/swagger-ui