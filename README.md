## Running with Docker
The `docker-compose.yml` file contains 3 services: `postgres`, `springboot-app`, and `keycloak`.

- `postgres` is the database service.
- `springboot-app` is the application service.
- `keycloak` is the authentication service.

### Steps to Run the Application
1. Ensure you have Docker and Docker Compose installed on your machine.
2. In the root directory of the project, run:
   ```bash
   docker compose up --build

The services will be available at the following ports:
Keycloak: http://localhost:8080
Spring Boot Application: http://localhost:8081

## Configuring Keycloak
This was done using the keycloak admin client api. Once the dependency was declared, the KeycloakService was defined.

When an Organization is created, the service is called to create a Realm matching the Organization name.
When a User is created, the keycloak service is called to create a User in Keycloak with the same names, username, email and password.

Access the Keycloak Admin Console at http://localhost:8080/admin.
Log in using the admin credentials:
Username: admin1
Password: admin1_password
You will see changed made programmatically appear here.

## REST endpoints
### Organizations
- `GET /api/organizations`: Get all organizations.
- `POST /api/organizations`: Create a new organization.
- `GET /api/organizations/{id}`: Get an organization by ID.
- `PUT /api/organizations/{id}`: Update an organization by ID.
- `DELETE /api/organizations/{id}`: Delete an organization by ID.

### Users
- `GET /api/users`: Get all users.
- `POST /api/users`: Create a new user.

The requests should be made using the base URL: `http://localhost:8081/`.