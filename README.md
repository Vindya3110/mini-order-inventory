# Mini Order & Inventory Management API

A Spring Boot REST API for managing products, customers, orders and sales reports.

## Tech stack

- Java 21, Spring Boot 4
- Spring Web MVC, Spring Data JPA, Bean Validation
- PostgreSQL
- springdoc-openapi (Swagger UI)

## Running

1. Start PostgreSQL and create a database named `orderdb` (credentials in `src/main/resources/application.properties`).
2. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

## API documentation (Swagger / OpenAPI)

Every endpoint is documented with OpenAPI annotations. Once the app is running:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

Swagger UI lets you browse every endpoint, view request/response schemas with examples, and try requests directly from the browser.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/products` | Create a product |
| GET | `/products` | List all products |
| GET | `/products/{id}` | Get a product by id |
| GET | `/products/search?name=` | Search products by name |
| PUT | `/products/{id}` | Update a product |
| PATCH | `/products/{id}/deactivate` | Deactivate a product |
| POST | `/customer` | Create a customer |
| GET | `/customer` | List all customers |
| GET | `/customer/{id}` | Get a customer by id |
| PUT | `/customer/{id}` | Update a customer |
| GET | `/customer/{id}/orders` | List a customer's orders |
| POST | `/orders` | Place an order |
| GET | `/orders` | List all orders |
| GET | `/orders/{id}` | Get an order by id |
| GET | `/orders/customer/{customerId}` | List orders for a customer |
| PUT | `/orders/{id}/cancel` | Cancel an order |
| GET | `/reports/customers/{customerId}` | Customer spending report |
| GET | `/reports/products` | Product sales report |
| GET | `/reports/top-products?limit=` | Top selling products |

## Sample request / response payloads

### Create a product — `POST /products`

Request:
```json
{
  "name": "Laptop",
  "category": "Electronics",
  "price": 50000.00,
  "availableQuantity": 10
}
```
Response `201 Created`:
```json
{
  "id": 1,
  "name": "Laptop",
  "category": "Electronics",
  "price": 50000.00,
  "availableQuantity": 10,
  "active": true
}
```

### Create a customer — `POST /customer`

Request:
```json
{
  "name": "Alice",
  "email": "alice@gmail.com",
  "phone": "9999999999"
}
```
Response `201 Created`:
```json
{
  "id": 1,
  "name": "Alice",
  "email": "alice@gmail.com",
  "phone": "9999999999"
}
```

### Place an order — `POST /orders`

Request:
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

### Customer report — `GET /reports/customers/1`

Response `200 OK`:
```json
{
  "customerName": "Alice",
  "numberOfOrders": 3,
  "totalAmountSpent": 150000.00,
  "averageOrderValue": 50000.00
}
```

### Product sales report — `GET /reports/products`

Response `200 OK`:
```json
[
  {
    "productId": 1,
    "productName": "Laptop",
    "quantitySold": 12,
    "totalRevenue": 600000.00
  }
]
```

## Error responses

All failures return a consistent structure:

```json
{
  "timestamp": "2026-09-05T10:15:30",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found"
}
```

| Status | When |
|--------|------|
| 400 | Validation failure, inactive product, insufficient stock, invalid operation |
| 404 | Resource not found |
| 409 | Duplicate email |
