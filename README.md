# multi-tenant-with-JWT
multi-tenant with JWT and spring security

# REFERENCE LINK : https://dzone.com/articles/dynamic-multi-tenancy-using-java-spring-boot-sprin

# Multitenancy with Master Database

This project demonstrates a **multitenant architecture** in a Spring Boot application where a **master database** stores tenant-specific information (database URLs and credentials), allowing the application to dynamically connect to different tenant databases based on the `org_id` provided in each request.

## Architecture Overview

### 1. **Master Database (Config DB)**
Stores metadata about tenants, including:
- `org_id`: Tenant identifier
- `db_url`: Tenant's database URL
- `db_username`: Tenant's database username
- `db_password`: Tenant's database password

### 2. **Tenant Databases**
Separate databases for each tenant containing application data.

#### Example: Master Database Table (`app_organisation`)

| org_id | org_name   | db_url                        | db_username | db_password |
|--------|------------|-------------------------------|-------------|-------------|
| 1      | Tenant A   | jdbc:postgresql://.../db1     | tenant_a    | pass_a      |
| 2      | Tenant B   | jdbc:postgresql://.../db2     | tenant_b    | pass_b      |

## How It Works

### 1. **Tenant Identification**
Each request contains an `org_id` (tenant identifier) passed via headers, tokens, or login payloads.

### 2. **Lookup Tenant Details**
The app queries the **master database** to retrieve the corresponding tenant database details using the `org_id`.

### 3. **Dynamic Database Connection**
A dynamic connection to the tenant-specific database is established based on the retrieved details.

### 4. **Tenant-Specific Operations**
CRUD operations are performed on the tenant’s database, ensuring data isolation.

## Technologies Used

- **Spring Boot**
- **PostgreSQL/MySQL** (Relational Database)
- **JPA + Hibernate** (ORM)
- **Dynamic DataSource Routing** (for multitenancy)

## Setup and Configuration

### 1. **Master Database Configuration**
Ensure you have a **master database** with the `app_organisation` table containing the following columns:
- `org_id`: Unique tenant identifier.
- `org_name`: Tenant name.
- `db_url`: URL of the tenant’s database.
- `db_username`: Username for the tenant’s database.
- `db_password`: Password for the tenant’s database.

### 2. **Tenant Database Configuration**
Each tenant should have its own database that is appropriately configured for your application’s needs.

### 3. **Spring Boot Configuration**

#### `application.properties`

```properties
# Master DB configuration
spring.datasource.master.url=jdbc:postgresql://localhost:5432/master_db
spring.datasource.master.username=master_user
spring.datasource.master.password=master_password
spring.datasource.driver-class-name=org.postgresql.Driver

# Tenant-specific dynamic configuration handled programmatically
