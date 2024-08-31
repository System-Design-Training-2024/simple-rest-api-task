# Task: Create a REST API with PostgreSQL Integration

## Objective

The goal of this task is to create a simple REST API that allows users to perform basic CRUD (Create, Read, Update, Delete) operations on a `Product` entity. The data should be stored in a PostgreSQL database. You are free to choose any backend framework that you are comfortable with (e.g., Spring Boot, Express.js, Django, Flask, etc.).

## Requirements

- **Choose your preferred backend framework** (e.g., Spring Boot, Express.js, Django, Flask)
- **PostgreSQL** for data persistence
- **Postman** or **cURL** for testing the APIs

## Instructions

1. **Set Up the Project**
   - Initialize a new project using your chosen backend framework.
   - Set up a connection to a PostgreSQL database. You can use a local PostgreSQL instance
   - Create a `Product` entity with the following fields:
     - `id` (UUID or Auto-incremented Integer, Primary Key)
     - `name` (String, Required)
     - `description` (String)
     - `price` (Decimal, Required)
     - `created_at` (Timestamp, Auto-generated)
     - `updated_at` (Timestamp, Auto-updated)

2. **API Endpoints**
   Implement the following REST API endpoints:

   - **Create a Product**
     - **POST** `/products`
     - Request Body: JSON containing `name`, `description`, and `price`
     - Response: JSON containing the created product with all fields, including `id`, `created_at`, and `updated_at`

   - **Get All Products**
     - **GET** `/products`
     - Response: JSON array containing all products

   - **Get a Product by ID**
     - **GET** `/products/{id}`
     - Response: JSON containing the product with the specified `id`
     - If the product does not exist, return a `404 Not Found` status

   - **Update a Product**
     - **PUT** `/products/{id}`
     - Request Body: JSON containing `name`, `description`, and/or `price`
     - Response: JSON containing the updated product
     - If the product does not exist, return a `404 Not Found` status

   - **Delete a Product**
     - **DELETE** `/products/{id}`
     - Response: `204 No Content` status if the deletion is successful
     - If the product does not exist, return a `404 Not Found` status

3. **Testing**
   - Use Postman or cURL to test each API endpoint.
   - Make sure to handle edge cases, such as trying to retrieve or delete a product that does not exist.

4. **Submission**
   - Create a branch with your name and commit your code in in that branch
   - push your code and create a pull request and add your name in the description.
