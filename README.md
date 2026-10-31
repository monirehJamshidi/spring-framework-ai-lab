# Spring Framework & AI Lab

A hands-on Java project exploring the Spring ecosystem through practical implementation, experimentation, and testing.

This project brings together Spring Boot, Spring MVC, Spring Data JPA, transaction management, Spring AOP, REST APIs, and Spring AI in an account management domain.

## Overview

The project is organized around an Account and Branch domain and demonstrates different approaches to building Spring-based applications.

It also includes an AI-powered chat endpoint built with Spring AI and Ollama, featuring conversation memory and tool calling.

The main goal is to explore Spring concepts through working code rather than isolated examples.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.0.0 | Application framework |
| Spring MVC | REST API development |
| Spring Data JPA | Data access and persistence |
| Hibernate / JPA | ORM and entity management |
| Spring AOP | Cross-cutting concerns and logging |
| Spring AI 2.0.1 | AI integration |
| Ollama | Local LLM runtime |
| Qwen3 4B Instruct | Chat model |
| Oracle Database | Relational database |
| Maven | Build and dependency management |
| JUnit 5 & Mockito | Unit testing |
| Lombok | Boilerplate reduction |

## Project Features

### 1. Spring Boot and Application Configuration

- Spring Boot application configuration
- Dependency injection and Spring-managed components
- YAML-based configuration
- Spring profiles
- Environment-variable-based database configuration

### 2. REST APIs with Spring MVC

The project provides REST endpoints for account management and AI-powered chat.

Account API examples:

- Create an account
- Retrieve an account by ID
- Retrieve all accounts
- Search accounts by balance and owner
- Update account-branch relationships
- Delete accounts
- Test JPA relationship and persistence behavior

The API uses DTOs to separate request and response models from JPA entities.

### 3. Spring Data JPA

The account module explores several data access techniques:

- Derived Query Methods
- JPQL with `@Query`
- Native SQL queries
- Named parameters with `@Param`
- Interface-based projections
- DTO projections
- Dynamic projections
- JPA Specifications
- Custom repository implementation
- Pagination and sorting

### 4. Entity Relationships and Hibernate

The domain contains two main entities:

- `Account`
- `Branch`

The project explores:

- `@ManyToOne` and `@OneToMany`
- Lazy loading
- Cascade operations
- Orphan removal
- Fetch strategies
- Join Fetch
- Entity Graph
- N+1 query behavior

### 5. Transaction Management and Persistence Context

The project demonstrates transaction boundaries using `@Transactional` and `@Transactional(readOnly = true)`.

It also includes practical `EntityManager` examples covering:

- `persist()`
- `find()`
- `flush()`
- `remove()`
- `detach()`
- Managed and detached entities
- Persistence Context behavior

### 6. Spring AOP

A custom logging aspect uses `@Around` advice to monitor service method execution.

It records:

- Method execution start
- Successful completion
- Execution duration
- Exceptions during execution

This demonstrates how cross-cutting concerns can be separated from business logic.

### 7. Validation and Exception Handling

The REST layer uses Jakarta Bean Validation and centralized exception handling.

Examples include:

- Request validation with `@Valid`
- Field-level validation constraints
- Custom account-not-found exception
- Global exception handling with `@RestControllerAdvice`
- Structured error responses

### 8. Spring AI and Ollama

The project includes an AI chat API using Spring AI's `ChatClient`.

Implemented features:

- Local LLM integration with Ollama
- Qwen3 4B Instruct model configuration
- System prompts
- Conversation-based chat memory
- Message window memory with a maximum of 20 messages
- Tool calling with a custom Java tool

**AI endpoint:**

`POST /api/ai/chat`

Example request:

```json
{
  "conversationId": "demo-conversation",
  "message": "Explain Spring Boot"
}
```

### 9. AI Tool Calling

A custom `SimpleInterestTools` component exposes a Java method to the AI model through Spring AI's `@Tool` annotation.

The tool calculates:

- Principal amount
- Simple interest
- Total amount

It also validates input values, including principal, annual interest rate, and duration.

### 10. Testing

The project includes JUnit 5 and Mockito tests for:

- AI chat service behavior
- ChatClient interaction
- Simple interest calculation
- Invalid calculator inputs

The `spring-ai.http` file contains HTTP request examples for exploring the AI endpoint, conversation context, and tool calling.

## Project Structure

```text
src/
├── main/
│   ├── java/org/j2os/
│   │   ├── account/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   ├── ai/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── service/
│   │   │   └── tools/
│   │   ├── api/
│   │   └── common/
│   │       ├── aspect/
│   │       └── exception/
│   └── resources/
│       ├── application.yml
│       ├── application-master.yml
│       ├── application-j2os.yml
│       └── application-production.yml
└── test/
    └── java/org/j2os/
        └── ai/
```

## Getting Started

### Prerequisites

- JDK 21
- Maven
- Oracle Database
- Ollama

### 1. Clone the repository

```bash
git clone https://github.com/monirehJamshidi/spring-framework-ai-lab.git
cd spring-framework-ai-lab
```

### 2. Configure database environment variables

Set the following environment variables:

```text
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
DB_URL=jdbc:oracle:thin:@localhost:1521:xe
```

The application uses environment variables instead of hardcoded database credentials.

### 3. Set up Ollama

Install Ollama and download the configured model:

```bash
ollama pull qwen3:4b-instruct
```

Make sure the Ollama service is running.

### 4. Run the tests

```bash
mvn clean test
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application runs on:

`http://localhost:8081`

## API Examples

### Account API

Base path:

`/api/accounts`

Example endpoints:

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/accounts` | Create an account |
| GET | `/api/accounts` | Retrieve all accounts |
| GET | `/api/accounts/{accountId}` | Retrieve an account |
| GET | `/api/accounts/paged` | Retrieve paginated accounts |
| GET | `/api/accounts/search/specification` | Search using specifications |

### AI Chat API

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/ai/chat` | Send a message to the AI assistant |

Additional request examples are available in `spring-ai.http`.

## Development Notes

This repository is a learning and experimentation project.

The current configuration is intended for local development. Database schema auto-update and SQL logging are enabled for development purposes and should be reviewed before production use.

## Future Improvements

- Expand automated tests for account and repository layers
- Add integration tests for REST endpoints
- Explore more advanced Spring AI capabilities
- Experiment with Retrieval-Augmented Generation (RAG)
- Explore embeddings and vector databases
- Improve application security and production configuration

## Author

**Monireh Jamshidi**

GitHub: [@monirehJamshidi](https://github.com/monirehJamshidi)