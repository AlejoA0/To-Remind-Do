# To-Remind-Do 📝

API REST para gestionar pendientes de forma rápida, construida con Java y Spring Boot.

## Estado del proyecto

En desarrollo. El registro, el login y la generación de tokens JWT ya están implementados. La verificación del token en los endpoints de tareas y usuarios está en progreso, por lo que actualmente esos endpoints rechazan las peticiones.

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security y JWT** (JJWT)
- **Spring Data JPA / Hibernate**
- **Bean Validation**
- **PostgreSQL**
- **Docker**
- **Lombok**
## Requisitos previos

- JDK 17 o superior
- Docker Desktop instalado y corriendo
- OpenSSL (incluido en Git Bash) para generar la clave secreta
## Cómo correr el proyecto

**1. Levantar la base de datos con Docker**

```bash
docker run --name to-remind-do-db -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=to-remind-do-db -p 5432:5432 -d postgres
```

**2. Clonar el repositorio**

```bash
git clone https://github.com/AlejoA0/to-remind-do.git
cd to-remind-do
```

**3. Definir la variable de entorno `TOKEN_JWT`**

Es la clave con la que se firman los tokens y es obligatoria para arrancar la aplicación. Debe ser aleatoria y de al menos 64 caracteres.

```bash
# Generar un valor (Git Bash)
openssl rand -base64 64 | tr -d '\n'
 
# Definirla en Git Bash, Linux o Mac
export TOKEN_JWT="valor_generado"
```

```powershell
# Definirla en PowerShell
$env:TOKEN_JWT = "valor_generado"
```

En IntelliJ se configura en *Run → Edit Configurations → Environment variables*.

**4. Arrancar la aplicación**

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

## Endpoints disponibles

### Autenticación (públicos)

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/auth/register` | Registrar un usuario. Devuelve el usuario y un token JWT |
| POST | `/api/auth/login` | Iniciar sesión con email y clave. Devuelve el usuario y un token JWT |

### Usuarios

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/usuarios/{id}` | Obtener un usuario por id |
| GET | `/api/usuarios` | Obtener todos los usuarios |

### Tareas

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/tareas` | Crear una tarea |
| GET | `/api/tareas/{id}` | Obtener una tarea por id |
| GET | `/api/tareas` | Obtener todas las tareas activas |
| PATCH | `/api/tareas/estado/{id}/{estado}` | Cambiar estado (PENDING, COMPLETE, OVERDUE) |
| PATCH | `/api/tareas/prioridad/{id}/{prioridad}` | Cambiar prioridad (LOW, MEDIUM, HIGH) |
| PATCH | `/api/tareas/editar/{id}` | Editar título, descripción y fecha límite |
| DELETE | `/api/tareas/eliminar/{id}` | Eliminar una tarea (soft delete) |

## Colección de Postman

El repositorio incluye una colección de Postman lista para importar con los endpoints configurados.

Archivo: `To-Remind-Do API.postman_collection.json`

Para importarla: abrir Postman → Import → seleccionar el archivo.

## Decisiones técnicas

- **Soft delete:** las tareas no se eliminan físicamente de la base de datos, se marcan como eliminadas para preservar la integridad de los datos.
- **Enums como texto:** los estados y prioridades se guardan como texto en la BD para evitar inconsistencias si cambia el orden de los valores.
- **Validaciones en DTO:** las validaciones de formato se manejan en la capa de entrada, separadas de la lógica de negocio.
- **Contraseñas con BCrypt:** se almacena únicamente el hash y el atributo no se expone en las respuestas.
- **Autenticación stateless:** cada petición se identifica con un token JWT, sin sesiones en el servidor.
- **Secreto fuera del código:** la clave de firma se lee de la variable de entorno `TOKEN_JWT`.
- **Manejo global de excepciones:** un `@ControllerAdvice` traduce las excepciones de negocio a respuestas `400` con un mensaje claro.