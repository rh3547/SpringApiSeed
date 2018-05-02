# EasyNotes REST API

## Description
This was a test/learning process to setup a Java Spring/Hibernate REST API.

## Basic Implementation
This example contains a basic REST API connecting to an AWS MySQL database.

### Models

###### Note
- **id**
  - Long
  - Primary key
  - Auto-generated
- **title**
  - String
  - Not null
- **content**
  - String
  - Not null
- **createdAt**
  - Date
  - Auto set when record is created
  - Cannot be changed
  - Stored as timestamp
- **updatedAt**
  - Date
  - Auto updated when record changes
  - Stored as timestamp

### Endpoints

###### GET ALL NOTES
- **URL:** /notes
- **Type:** GET
- **Returns:** List of all Note models

###### GET SINGLE NOTE BY ID
- **URL:** /notes/{id}
- **Type:** GET
- **Returns:** Single Note model with the given id

###### CREATE NOTE
- **URL:** /notes
- **Type:** POST
- **Returns:** The newly created Note model

###### UPDATE NOTE
- **URL:** /notes/{id}
- **Type:** PUT
- **Returns:** The updated Note model

###### DELETE NOTE
- **URL:** /notes/{id}
- **Type:** DELETE
- **Returns:** 200 (OK) response code


## Future Plans

1. Add [Swagger](http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api) implementation.
2. Increase complexity with other models.

## Notes
- This was learned from [this](https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/) article.