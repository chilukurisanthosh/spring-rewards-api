
# Rewards API — Full JPA + JWT Security + Tests

This project demonstrates a production-style setup:
- Spring Data **JPA** (Hibernate)
- **Many-to-One**: Transaction → Customer, with One-to-Many backref on Customer
- **JWT Security** using Spring Security Resource Server (HMAC HS256)
- **H2** DB for dev/test
- **Unit & Integration tests**: `@DataJpaTest`, `@WebMvcTest` + `jwt()`, and end-to-end token flow

## Run
```bash
mvn spring-boot:run
```
- H2 Console: `http://localhost:8080/h2-console` (JDBC: `jdbc:h2:mem:rewardsdb`)

## Auth (demo)
- Get token:
```bash
curl -X POST http://localhost:8080/auth/token -H "Content-Type: application/json"   -d '{"username":"user","password":"password"}'
```
- Use token:
```bash
curl -H "Authorization: Bearer <TOKEN>" "http://localhost:8080/api/rewards?start=2025-01-01&end=2025-03-31"
```

## Tests
```bash
mvn test
```
Includes:
- `TransactionJpaRepositoryTest` — verifies JPA mapping and derived queries
- `RewardsControllerSecurityTest` — MockMvc slice test with `jwt()`
- `AuthFlowIT` — obtains token and calls a protected endpoint
