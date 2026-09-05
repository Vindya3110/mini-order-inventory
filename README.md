# Mini Order & Inventory Management System

## Project overview

This is a small backend I built to simulate an e-commerce / order-processing system. It manages products and their stock, customers, and orders. When an order is placed the stock is checked and reduced, the price of each product is stored at the time of ordering, and orders can later be cancelled to put the stock back. There are also a few reporting endpoints for customer spending and product sales.

## Technologies used

- Java 21
- Spring Boot 4 (Spring Web MVC, Spring Data JPA, Bean Validation)
- Hibernate
- PostgreSQL
- Maven
- springdoc-openapi (Swagger UI) for the API documentation

## Database configuration and setup instructions

I used PostgreSQL. Before running the app, create a database called `orderdb`:

```sql
CREATE DATABASE orderdb;
```

The connection settings are in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/orderdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

Change the username/password if yours are different. I left `ddl-auto=update` on, so Hibernate creates and updates the tables automatically the first time the app starts — no manual schema script is needed.

## How to run the application

From the project root:

```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`.

To run the tests:

```bash
./mvnw test
```

## API documentation

I documented the API with Swagger/OpenAPI. Once the app is running you can open it in the browser:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Swagger UI lists every endpoint with its request/response schema and lets you try requests directly.

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/products` | Create a product |
| GET | `/products` | List all products |
| GET | `/products/{id}` | Get a product |
| GET | `/products/search?name=` | Search products by name |
| PUT | `/products/{id}` | Update a product |
| PATCH | `/products/{id}/deactivate` | Deactivate a product |
| POST | `/customer` | Create a customer |
| GET | `/customer` | List all customers |
| GET | `/customer/{id}` | Get a customer |
| PUT | `/customer/{id}` | Update a customer |
| GET | `/customer/{id}/orders` | Get a customer's orders |
| POST | `/orders` | Place an order |
| GET | `/orders` | List all orders |
| GET | `/orders/{id}` | Get an order |
| GET | `/orders/customer/{customerId}` | Get orders for a customer |
| PUT | `/orders/{id}/cancel` | Cancel an order |
| GET | `/reports/customers/{customerId}` | Customer spending report |
| GET | `/reports/products` | Product sales report |
| GET | `/reports/top-products?limit=5` | Top selling products |

### Sample request / response

Place an order — `POST /orders`:

```json
{
  "customerId": 1,
  "items": [
    { "productId": 1, "quantity": 2 }
  ]
}
```

Response `201 Created`:

```json
{
  "id": 1,
  "customerId": 1,
  "customerName": "Alice",
  "totalAmount": 100000.00,
  "orderDate": "2026-09-05T10:15:30",
  "items": [
    {
      "productId": 1,
      "productName": "Laptop",
      "quantity": 2,
      "unitPrice": 50000.00
    }
  ],
  "status": "ACTIVE"
}
```

All errors come back in the same shape:

```json
{
  "timestamp": "2026-09-05T10:15:30",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found"
}
```

## Database / schema information

There are four tables and the relationship chain is **Customer → Order → OrderItem → Product**.

**customers** — `id` (PK), `name`, `email` (unique), `phone`, `created_at`, `updated_at`

**products** — `id` (PK), `name`, `category`, `price`, `available_quantity`, `active`, `created_at`, `updated_at`

**orders** — `id` (PK), `customer_id` (FK → customers), `total_amount`, `order_date`, `status` (`ACTIVE`, `COMPLETED`, `CANCELLED`)

**order_items** — `id` (PK), `order_id` (FK → orders), `product_id` (FK → products), `quantity`, `unit_price`

Relationships:

- A customer can have many orders.
- An order can have many order items (saved and removed together with the order).
- Each order item points to one product and keeps its own `unit_price`, so an order always shows the price at the time it was placed, even if the product price changes later.

## Important assumptions

- Products are never physically deleted. There is no delete endpoint — deactivation just sets `active = false`, and I block deactivating a product that is already used in an order.
- The product price is copied into the order item at order time, so changing a product's price later does not affect past orders.
- Order creation is a single transaction. If any item is invalid (product missing/inactive or not enough stock) the whole order fails and no stock is reduced.
- Cancelling an `ACTIVE` order returns the stock. A `COMPLETED` or already `CANCELLED` order cannot be cancelled.
- Reports only count `ACTIVE` orders, so cancelled orders are not included in the totals.
- Customer email must be unique. A duplicate email returns `409 Conflict`.
- IDs are generated by the database, so the ids in the samples above are just examples.

## Known limitations

- No authentication or authorization — every endpoint is open.
- List endpoints return all rows; I did not add pagination.
- There is no endpoint to move an order to `COMPLETED`, even though the status exists.
- I used `ddl-auto=update`, which is fine for this assignment but not ideal for production — a migration tool like Flyway or Liquibase would be better. Also, because Hibernate creates a check constraint on the `status` column, changing the status values on an existing database needs a manual `ALTER TABLE`.
- The average order value in the customer report comes from SQL `AVG`, so it can carry small floating-point rounding.
