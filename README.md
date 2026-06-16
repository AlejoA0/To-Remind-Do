# To-Remind-Do 📝

Una aplicación que te ayuda a gestionar de forma rápida tus pendientes.

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Docker**
- **Lombok**

## Requisitos previos

- JDK 17 o superior
- Docker Desktop instalado y corriendo

## Cómo correr el proyecto

**1. Levantar la base de datos con Docker**

```bash
docker run --name to-remind-do-db -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=to-remind-do-db -p 5432:5432 -d postgres
```

**2. Clonar el repositorio**

```bash
git clone https://github.com/tu-usuario/to-remind-do.git
cd to-remind-do
```

**3. Arrancar la aplicación**

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints disponibles

### Usuarios

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/usuarios` | Crear un usuario |
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

El repositorio incluye una colección de Postman lista para importar con todos los endpoints configurados.

Archivo: `To-Remind-Do API.postman_collection.json`

Para importarla: abre Postman → Import → selecciona el archivo.

## Decisiones técnicas

- **Soft delete:** las tareas no se eliminan físicamente de la base de datos. Se marcan como eliminadas para preservar la integridad de los datos.
- **Enums como texto:** los estados y prioridades se guardan como texto en la BD para evitar problemas si el orden de los valores cambia.
- **Validaciones en DTO:** las validaciones de formato se manejan en la capa de entrada, separadas de la lógica de negocio.
