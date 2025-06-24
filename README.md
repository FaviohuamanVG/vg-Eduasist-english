# EduAssist Social Project - PRS

Este microservicio es responsable de gestionar datos relacionados con usuarios, incluyendo cuentas de usuario, información de profesores, asignaciones usuario-sede y permisos dentro del sistema Valle Grande. Proporciona una API RESTful para diversas operaciones sobre estas entidades.

## Stack Tecnológico

Este microservicio está construido utilizando el siguiente stack tecnológico:

-   **Lenguaje de Programación**: Java
-   **Framework**: Spring Boot
-   **Base de Datos**: MongoDB (utilizando Spring Data MongoDB Reactive)
-   **Gestor de Dependencias**: Maven
-   **Contenerización**: Docker
-   **API**: RESTful
-   **Manejo de Asincronía**: Project Reactor (evidenciado por el uso de `Mono` y `Flux` en los repositorios reactivos)

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


## 🧑‍🏫 Contributing (Imperatives & Advice)
- **Fork** this repo.  
- **Create** a feature branch:  
  `git checkout -b feature/vg-english-practice`  
- **Implement**, **test**, and **lint** your feature locally.  
- **Open** a Pull Request with a clear summary and description.  
  > You **should** add “Fixes #\<issue-number\>” in your PR if it's related to an open issue.

## Estructura del Proyecto

El proyecto sigue un patrón de arquitectura en capas (similar a Puertos y Adaptadores o Hexagonal), organizado en paquetes distintos:

-   **`application/service`**: Contiene la lógica de negocio principal y los casos de uso de la aplicación. Las clases aquí orquestan las operaciones sobre los modelos de dominio e interactúan con la capa de infraestructura (repositorios).
-   **`domain/model`**: Define las entidades centrales del dominio (objetos de negocio) como `User`, `Teacher`, `UserSede` y `Permission`. Son objetos Java simples que representan la estructura de los datos.
-   **`infrastructure`**: Maneja las preocupaciones externas y los detalles técnicos, incluyendo:
    -   **`config`**: Clases de configuración para la aplicación.
    -   **`exception`**: Clases de excepción personalizadas.
    -   **`repository`**: Interfaces para el acceso a datos (usando Spring Data MongoDB). Spring Data proporciona las implementaciones automáticamente.
    -   **`rest`**: Controladores REST que exponen los endpoints de la API y manejan las solicitudes y respuestas HTTP.
    -   **`service`**: (Nota: Aunque existe un paquete 'service' directamente bajo infrastructure, los servicios de lógica de negocio principales están bajo `application/service`. Este paquete de servicio de infraestructura podría contener servicios técnicos o adaptadores).

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

### Endpoints de Permiso (`/permissions`)

*(Nota: Basado en la estructura de tu proyecto, podría existir un controlador `PermissionRest`. Los siguientes endpoints son inferidos basados en los endpoints de `User` relacionados con permisos. Podrías necesitar verificar/añadir endpoints reales si existe un `PermissionRest` dedicado)*

-   *(Potenciales endpoints en un PermissionRest dedicado, si existe:)*
    -   `GET /permissions`: Obtiene todos los permisos disponibles.
    -   `POST /permissions`: Crea un nuevo permiso.
    -   `DELETE /permissions/{id}`: Elimina un permiso.

## Construcción y Ejecución con Docker

El proyecto incluye un `Dockerfile` para construir una imagen Docker para el microservicio.

1.  **Construir la Imagen Docker:**
    ```bash
    docker build -t vg-ms-user .
    ```
    (Este comando utiliza el wrapper `mvnw` dentro del contenedor para construir el JAR de la aplicación, como se define en el Dockerfile).

2.  **Ejecutar el Contenedor Docker:**
    ```bash
    docker run -p 8080:8080 vg-ms-user
    ```
    (Este comando mapea el puerto 8080 de tu máquina host al puerto 8080 dentro del contenedor, donde se ejecuta la aplicación).

**Nota:** El Dockerfile usa `target/your-application.jar`. Asegúrate de que esto coincida con el nombre real del archivo JAR generado por Maven después de la construcción (`./mvnw clean package`).
