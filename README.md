# EduAssist Social Project - PRS

This microservice is responsible for managing user-related data, including user accounts, teacher information, user-site assignments, and permissions within the Valle Grande system. It provides a RESTful API for various operations on these entities.

##Technology Stack

This microservice is built using the following technology stack:

- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: MongoDB (using Spring Data MongoDB Reactive)
- **Dependency Manager**: Maven
- **Database**: Mongo
- **Containerization**: Docker
- **API**: RESTful
- **Asynchrony Handling**: Project Reactor (evidenced by the use of `Mono` and `Flux` in the reactive repositories)

## 🛠️ Setup Instructions (Imperatives)
1. **Clone** the repository:  
   `git clone https://github.com/FaviohuamanVG/vg-Eduasist-english.git`  
2. **Navigate** into backend:  
   `cd main`  
3. **Run** Spring Boot app:  
   `./mvnw spring-boot:run`  
4. **Navigate** into frontend:  
   `cd ../frontend`  
5. **Install** dependencies and **serve** the Angular app:  
   `npm install`  
   `ng serve`  

## 🧩 How to Use the App (Advice with “should”)

- You **should** open `http://localhost:4200` after both backend and frontend are running.  
- You **should** create a user account to access the page. 
- You **should** submit reports via the "FirebaseStudio" page after trying each tutorial.

## 🎯 Future Plans (Advice & Suggestions)
- We **should** integrate Firebase hosting before the final release.  
- We **should** implement user roles (admin, director, teacher, secretery and assistant) to control content access.  
- We **should** schedule community coding workshops during the semester.

## 📁 Repository Structure
```text
/cetpro-social-project
├── vg-ms-users/        # Java 17 + Spring Boot REST API
├── EduAssist/       # React app
├── README.md       # ← You are here
├── .env.example    # Environment variables template
```


## Project Structure

The project follows a layered architecture pattern (similar to Ports and Adapters or Hexagonal), organized into distinct packages:

-   **`application/service`**: It contains the main business logic and use cases of the application. The classes here orchestrate operations on the domain models and interact with the infrastructure layer (repositories).
-   **`domain/model`**: Define the central entities of the domain (business objects) as `User`, `Teacher`, `UserSede`, and `Permission`. They are simple Java objects that represent the structure of the data.
-   **`infrastructure`**: Manages external concerns and technical details, including:
    -   **`config`**: Configuration classes for the application.
    -   **`exception`**: Customized exception classes.
    -   **`repository`**: Interfaces for data access (using Spring Data MongoDB). Spring Data provides the implementations automatically.
    -   **`rest`**: REST controllers that expose the API endpoints and handle HTTP requests and responses.
    -   **`service`**: (Note: Although there is a 'service' package directly under infrastructure, the main business logic services are under `application/service`. This infrastructure service package may contain technical services or adapters).

## 🧑‍🏫 Contributing (Imperatives & Advice)
- **Fork** this repo.  
- **Create** a feature branch:  
  `git checkout -b feature/vg-english-practice`  
- **Implement**, **test**, and **lint** your feature locally.  
- **Open** a Pull Request with a clear summary and description.  
  > You **should** add “Fixes #\<issue-number\>” in your PR if it's related to an open issue.

## 🚀 Deployment Requirements (Must & Need To)
- You **must** set the environment variables:
- MONGODB_URI= mongodb+srv://faviohuaman:whQVhuBsOgu0C0M4@cluster0.zkqvx.mongodb.net/msvc-prs?retryWrites=true&w=majority
- JWT_SECRET=xlskkjjs123452
- - You **need to** enable CORS in the Spring configuration for frontend access.  
- You **must** build the frontend before deployment:  
`npm run build` in `/app-frontend-EduAsist/`  
Upload contents of `student/` to your hosting platform.

## 💡 Best Practices & Tips
- You **should** write unit tests.  
- You **should** document any new REST endpoints in the README or API specification.  
- You **should** run `mvn clean` and `npm run lint` before each commit.

## Endpoints de la API

A continuación se listan los endpoints de la API REST disponibles proporcionados por este microservicio:

### Endpoints de Usuario (`/users`)

-   `POST /users`: Crea un nuevo usuario (individual).
    -   Cuerpo de la Solicitud: Objeto `User`.
    -   Respuesta: Objeto `User` creado.
-   `POST /users/batch`: Crea múltiples usuarios (lote).
    -   Cuerpo de la Solicitud: Array de objetos `User` (`List<User>`).
    -   Respuesta: Array de objetos `User` creados.
-   `GET /users`: Obtiene todos los usuarios. Opcionalmente filtra por rol y estado.
    -   Parámetros de Consulta:
        -   `role` (opcional): Filtra por rol de usuario (ej. `PROFESOR`, `AUXILIAR`).
        -   `status` (opcional): Filtra por estado (`active` o `inactive`).
    -   Respuesta: Array de objetos `User`.
-   `GET /users/{id}`: Obtiene un usuario por ID.
    -   Variable de Ruta: `id` (ID del usuario).
    -   Respuesta: Objeto `User`.
-   `PUT /users/{id}`: Actualiza un usuario por ID.
    -   Variable de Ruta: `id` (ID del usuario).
    -   Cuerpo de la Solicitud: Objeto `User` actualizado.
    -   Respuesta: Objeto `User` actualizado.
-   `PATCH /users/{id}/deactivate`: Desactiva lógicamente (eliminado suave) un usuario por ID.
    -   Variable de Ruta: `id` (ID del usuario).
    -   Respuesta: Sin contenido (204) en caso de éxito.
-   `PATCH /users/{id}/activate`: Activa (restaura) un usuario que fue desactivado lógicamente por ID.
    -   Variable de Ruta: `id` (ID del usuario).
    -   Respuesta: Objeto `User` activado.
-   `POST /users/{userId}/permissions/{permission}`: Añade un permiso específico a un usuario.
    -   Variables de Ruta: `userId`, `permission`.
    -   Respuesta: Objeto `User` actualizado.
-   `POST /users/{userId}/permissions/batch`: Añade múltiples permisos a un usuario.
    -   Variable de Ruta: `userId`.
    -   Cuerpo de la Solicitud: Conjunto de cadenas de permisos (`Set<String>`).
    -   Respuesta: Objeto `User` actualizado.
-   `DELETE /users/{userId}/permissions/{permission}`: Elimina un permiso específico de un usuario.
    -   Variables de Ruta: `userId`, `permission`.
    -   Respuesta: Objeto `User` actualizado.
-   `PUT /users/{userId}/permissions`: Establece el conjunto exacto de permisos para un usuario, reemplazando los existentes.
    -   Variable de Ruta: `userId`.
    -   Cuerpo de la Solicitud: Conjunto de cadenas de permisos (`Set<String>`).
    -   Respuesta: Objeto `User` actualizado.
-   `POST /users/migrate-permissions`: Endpoint para migrar usuarios con permisos por defecto (probablemente una operación única).
    -   Respuesta: Flux de objetos `User` actualizados.

### Endpoints de Profesor (`/teachers`)

-   `POST /teachers`: Crea un nuevo profesor.
    -   Cuerpo de la Solicitud: Objeto `Teacher`.
    -   Respuesta: Objeto `Teacher` creado.
-   `GET /teachers`: Obtiene todos los profesores. Opcionalmente filtra por estado.
    -   Parámetro de Consulta: `status` (opcional): Filtra por estado (`active` o `inactive`).
    -   Respuesta: Array de objetos `Teacher`.
-   `GET /teachers/{id}`: Obtiene un profesor por ID.
    -   Variable de Ruta: `id` (ID del profesor).
    -   Respuesta: Objeto `Teacher`.
-   `PUT /teachers/{id}`: Actualiza un profesor por ID.
    -   Variable de Ruta: `id` (ID del profesor).
    -   Cuerpo de la Solicitud: Objeto `Teacher` actualizado.
    -   Respuesta: Objeto `Teacher` actualizado.
-   `PATCH /teachers/{id}/deactivate`: Desactiva lógicamente (eliminado suave) un profesor por ID.
    -   Variable de Ruta: `id` (ID del profesor).
    -   Respuesta: Objeto `Teacher` activado.
-   `PATCH /teachers/{id}/activate`: Activa (restaura) un profesor que fue desactivado lógicamente por ID.
    -   Variable de Ruta: `id` (ID del profesor).
    -   Respuesta: Objeto `Teacher` activado.

### Endpoints de Usuario-Sede (`/user-sedes`)

-   `POST /user-sedes`: Crea una nueva asignación usuario-sede.
    -   Cuerpo de la Solicitud: Objeto `UserSede`.
    -   Respuesta: Objeto `UserSede` creado.
-   `GET /user-sedes`: Obtiene todas las asignaciones usuario-sede. Opcionalmente filtra por estado.
    -   Parámetro de Consulta: `status` (opcional): Filtra por estado (`Activo` o `Inactivo`).
    -   Respuesta: Array de objetos `UserSede`.
-   `GET /user-sedes/{id}`: Obtiene una asignación usuario-sede por ID (solo si el estado es "Activo").
    -   Variable de Ruta: `id` (ID de la asignación usuario-sede).
    -   Respuesta: Objeto `UserSede`.
-   `PUT /user-sedes/{id}`: Actualiza una asignación usuario-sede por ID.
    -   Variable de Ruta: `id` (ID de la asignación usuario-sede).
    -   Cuerpo de la Solicitud: Objeto `UserSede` actualizado.
    -   Respuesta: Objeto `UserSede` actualizado.
-   `PATCH /user-sedes/{id}/deactivate`: Desactiva lógicamente (eliminado suave) una asignación usuario-sede por ID.
    -   Variable de Ruta: `id` (ID de la asignación usuario-sede).
    -   Respuesta: Sin contenido (204) en caso de éxito.
-   `PATCH /user-sedes/{id}/activate`: Activa (restaura) una asignación usuario-sede que fue desactivada lógicamente por ID.
    -   Variable de Ruta: `id` (ID de la asignación usuario-sede).
    -   Respuesta: Objeto `UserSede` activado.

### Permissions Endpoints (`/permissions`)

*(Note: Depending on the structure of your project, there may be a `PermissionRest` controller. The following endpoints are inferred based on the `User` endpoints related to permissions. You may need to verify/add actual endpoints if there is a dedicated `PermissionRest`.)*

-   *(Potential endpoints in a dedicated PermissionRest, if it exists:)*
    -   `GET /permissions`: Obtiene todos los permisos disponibles.
    -   `POST /permissions`: Crea un nuevo permiso.
    -   `DELETE /permissions/{id}`: Elimina un permiso.

## Construction and Execution with Docker

The project includes a `Dockerfile` to build a Docker image for the microservice.

1. **Build the Docker Image:**
    ```bash
    docker build -t vg-ms-user .
    ```
    (This command uses the `mvnw` wrapper inside the container to build the application's JAR, as defined in the Dockerfile).

2. **Run the Docker Container:**
    ```bash
    docker run -p 8080:8080 vg-ms-user
    ```
    (This command maps port 8080 of your host machine to port 8080 inside the container, where the application runs.)

**Note:** The Dockerfile uses `target/your-application.jar`. Make sure this matches the actual name of the JAR file generated by Maven after the build (`./mvnw clean package`).
