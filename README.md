# Employee Management System - Backend

A robust GraphQL-based employee management system built with Spring Boot, providing comprehensive employee data management, attendance tracking, and role-based access control.

## 🚀 Features

- **GraphQL API** with comprehensive schema for queries and mutations
- **Employee Management** with full CRUD operations
- **Attendance Tracking** with status management
- **Subject Assignment** for employee skill management
- **JWT Authentication** with role-based access control
- **Pagination & Sorting** for efficient data retrieval
- **Performance Optimized** with database indexing and connection pooling
- **Production Ready** with comprehensive error handling and logging

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring GraphQL**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway** (Database Migration)
- **JWT** (JSON Web Tokens)
- **Maven** (Build Tool)
- **Lombok** (Code Generation)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Docker (optional, for containerized database)

## 🏃‍♂️ Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd employee-management
```

### 2. Setup Database

#### Option A: Using Docker
```bash
docker run --name postgres-employee \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=employee_db \
  -p 5432:5432 -d postgres
```

#### Option B: Local PostgreSQL
Create a database named `employee_db` in your local PostgreSQL instance.

### 3. Configure Application
Update `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/employee_db
    username: postgres
    password: postgres
```

### 4. Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔗 API Endpoints

### GraphQL Playground
Access the interactive GraphQL interface at:
```
http://localhost:8080/api/graphiql
```

### Main GraphQL Endpoint
```
POST http://localhost:8080/api/graphql
```

## 📊 GraphQL Schema

### Queries
```graphql
# Get paginated list of employees
employees(page: Int, size: Int, sort: [SortInput!], filter: EmployeeFilter): EmployeePage!

# Get single employee by ID
employee(id: ID!): Employee

# Get paginated list of subjects
subjects(page: Int, size: Int): SubjectPage!
```

### Mutations
```graphql
# Authentication
login(input: LoginInput!): AuthResponse!
refreshToken(token: String!): AuthResponse!

# Employee Management
createEmployee(input: CreateEmployeeInput!): Employee!
updateEmployee(id: ID!, input: UpdateEmployeeInput!): Employee!
deleteEmployee(id: ID!): Boolean!

# Attendance Management
markAttendance(employeeId: ID!, input: AttendanceInput!): Attendance!
```

## 🔐 Authentication

### Login
```graphql
mutation {
  login(input: {
    username: "admin"
    password: "admin"
  }) {
    accessToken
    refreshToken
    tokenType
  }
}
```

### Using JWT Token
Include the JWT token in your request headers:
```
Authorization: Bearer <your-jwt-token>
```

## 👥 Default Users

The system comes with a default admin user:
- **Username**: admin
- **Password**: admin
- **Role**: ADMIN

## 🗃️ Database Schema

### Tables
- `employees` - Employee information
- `subjects` - Available subjects/skills
- `employee_subjects` - Many-to-many relationship
- `attendance` - Daily attendance records

### Sample Data
The application includes sample data for:
- 1 Admin user
- 4 Default subjects (Mathematics, Physics, Chemistry, Biology)

## 🔧 Configuration

### Environment Variables
```bash
# Database Configuration
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_URL=jdbc:postgresql://localhost:5432/employee_db

# JWT Configuration
JWT_SECRET=your-secret-key
JWT_EXPIRATION=3600000
JWT_REFRESH_EXPIRATION=604800000
```

### Application Properties
Key configuration options in `application.yml`:

```yaml
app:
  jwt:
    secret: ${JWT_SECRET:default-secret}
    expiration: ${JWT_EXPIRATION:3600000}
    refresh-expiration: ${JWT_REFRESH_EXPIRATION:604800000}

spring:
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
  
  flyway:
    enabled: true
    baseline-on-migrate: true
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Testing with GraphQL Playground
1. Start the application
2. Navigate to `http://localhost:8080/api/graphiql`
3. Login to get JWT token
4. Add Authorization header
5. Execute queries and mutations

### Sample Test Queries

#### Create Employee
```graphql
mutation {
  createEmployee(input: {
    name: "John Doe"
    age: 30
    className: "Engineering"
    role: EMPLOYEE
    subjectIds: ["subject-id-1", "subject-id-2"]
  }) {
    id
    name
    age
    className
    role
    subjects {
      id
      name
    }
  }
}
```

#### Get Employees with Filters
```graphql
query {
  employees(
    page: 0
    size: 10
    filter: {
      age: { min: 25, max: 35 }
      role: EMPLOYEE
    }
    sort: [{ field: "name", direction: ASC }]
  ) {
    content {
      id
      name
      age
      role
    }
    totalElements
    totalPages
  }
}
```

## 🔒 Security Features

- **JWT Authentication** with access and refresh tokens
- **Role-based Authorization** (ADMIN, EMPLOYEE)
- **Method-level Security** with `@PreAuthorize`
- **Input Validation** with Bean Validation
- **SQL Injection Protection** with JPA/Hibernate
- **CORS Configuration** for cross-origin requests

## 🚀 Performance Optimizations

- **Database Indexing** on frequently queried fields
- **Connection Pooling** with HikariCP
- **Lazy Loading** for entity relationships
- **Query Optimization** with JPA specifications
- **Caching Strategy** with proper cache headers
- **Pagination** for large data sets

## 📝 API Documentation

### Error Handling
The API returns standardized error responses:

```json
{
  "errors": [
    {
      "message": "Employee not found with id: 123",
      "locations": [{"line": 2, "column": 3}],
      "path": ["employee"],
      "extensions": {
        "code": "NOT_FOUND",
        "classification": "DataFetchingException"
      }
    }
  ]
}
```

### Pagination Response
```json
{
  "content": [...],
  "totalElements": 100,
  "totalPages": 10,
  "number": 0,
  "size": 10,
  "first": true,
  "last": false
}
```

## 🔄 Database Migrations

The application uses Flyway for database versioning:

- **V1__init.sql** - Initial schema and sample data
- **V2__add_password.sql** - Add password column
- **V3__fix_admin_password.sql** - Update admin password

### Adding New Migrations
1. Create new file: `V{version}__{description}.sql`
2. Place in `src/main/resources/db/migration/`
3. Restart application

## 📦 Build & Deployment

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/employee-management-0.0.1-SNAPSHOT.jar
```

### Docker Deployment
```dockerfile
FROM openjdk:17-jre-slim
COPY target/employee-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Verify PostgreSQL is running
   - Check database credentials
   - Ensure database exists

2. **JWT Token Issues**
   - Check token expiration
   - Verify JWT secret configuration
   - Ensure proper Authorization header format

3. **GraphQL Schema Issues**
   - Verify all resolvers are implemented
   - Check for circular dependencies
   - Validate scalar type configurations

### Logs
Check application logs for detailed error information:
```bash
tail -f logs/application.log
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📞 Support

For support and questions:
- Create an issue in the repository
- Review [GraphQL Schema](src/main/resources/graphql/schema.graphqls)

---

**Built with ❤️ using Spring Boot and GraphQL**