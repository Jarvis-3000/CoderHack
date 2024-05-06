# Contest Leaderboard REST API

> This project aims to develop a RESTful API service using Spring Boot to manage the Leaderboard for a Coding Platform, leveraging MongoDB to persist the data.

# Prerequisites

  * JDK (Java Development Kit)
  * Gradle
  * MongoDB (Running instance)

# Technologies

  * Java
  * Spring Boot
  * JUnit for Testing
  * Lombok 
  * MongoDB


# Features

**User Registration:** Users can register for the contest by providing a unique Username.

**Score Update:** Users can update their scores via PUT requests.

**Badge Awards:** Badges (Code Ninja, Code Champ, Code Master) are awarded based on user scores.

**Sorting:** User retrieval is sorted based on the score in descending order with a time complexity of O(nlogn).

**Validation and Error Handling:** Implements basic validation for input fields and handles common errors gracefully.



# API Endpoints:
  * **Retrieve all users:** GET /users
  * **Retrieve a specific user:** GET /users/{userId}
  * **Register a new user:** POST /users
  * **Update user score:** PUT /users/{userId}
  * **Cancel registration:** DELETE /users/{userId}


# Postman Collection
https://www.postman.com/gaudsatish/workspace/coder-hack/collection/32314911-fb4c60bb-3ee6-4424-ad55-ff7154caa669?action=share&creator=32314911&active-environment=32314911-e6a235a2-c811-4ee9-9b74-f99ded22d5a7