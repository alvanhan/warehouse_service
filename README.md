# Warehouse Service

## Technologies and Versions Used

* Framework: Spring Boot 3.5.5
* Language: Java 21
* Database: PostgreSQL 16 (Driver: 42.7.2)
* Data Access: Spring Data JPA / Hibernate
* Schema Migration: Flyway
* Messaging: Apache Kafka (Spring Kafka 3.1.3 for test)

## Project Setup

1. Ensure Docker and Docker Compose are installed on your system.
2. From the project root, build and start all services with:
   ```sh
   docker-compose up --build
   ```
3. The service will be available on port 8080.

## API Endpoints

- Base URL (Development): http://157.245.58.101:8080/api
- Base URL (Local): http://localhost:8080/api

A Postman collection file, `warehoure service.postman_collection.json`, is available in the project root. Import this file into Postman to easily test all available endpoints.

### Endpoint List

| Method | Path                                      | Description                                      |
|--------|-------------------------------------------|--------------------------------------------------|
| GET    | /api/hello                                | Connection check, returns Hello World             |
| GET    | /api/health                               | Health check, returns OK                         |
| GET    | /api/monitoring                           | Retrieve monitoring summary                      |
| POST   | /api/production/start                     | Start a production process                       |
| POST   | /api/production/finish                    | Finish a production process                      |
| GET    | /api/production/calc/{productId}?qty=...  | Calculate estimated production time for a product|
| GET    | /api/warehouse/{type}                     | Get stock information by warehouse type          |

Use Postman or other API tools to access the available endpoints.

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.5/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.5/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/3.5.5/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.5.5/reference/messaging/kafka.html)
* [Validation](https://docs.spring.io/spring-boot/3.5.5/reference/io/validation.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.5/reference/web/servlet.html)

## Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

## Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
## Maven Parent Overrides
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
Due to Maven's design, elements are inherited from the parent POM to the project POM. While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent. To prevent this, the project POM contains empty overrides for these elements. If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
