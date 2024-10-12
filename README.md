## Descripción del Backend

El backend de esta aplicación está desarrollado con **Spring Boot** y utiliza **PostgreSQL** como base de datos relacional. A continuación se describen los principales componentes de la aplicación: el controlador, la entidad de base de datos y el servicio.

### Controlador (`TodoController`)

El **controlador** es responsable de manejar las solicitudes HTTP que llegan a la aplicación. Los métodos del controlador mapean diferentes endpoints HTTP y gestionan las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para las tareas. Utiliza anotaciones de Spring como `@GetMapping`, `@PostMapping`, `@PutMapping` y `@DeleteMapping` para definir rutas y sus correspondientes acciones.

#### Endpoints:
- `GET /api/todos`: Devuelve una lista de todas las tareas.
- `POST /api/todos`: Crea una nueva tarea en la base de datos.
- `PUT /api/todos/{id}`: Actualiza una tarea existente utilizando su identificador (ID).
- `DELETE /api/todos/{id}`: Elimina una tarea por su ID.

### Entidad de Base de Datos (`Todo`)

La entidad `Todo` representa una **tarea** dentro del sistema. Esta entidad está mapeada a una tabla de la base de datos PostgreSQL utilizando **JPA**. Cada tarea tiene tres campos principales:

#### Campos de la Entidad:
- `id`: Clave primaria, generada automáticamente.
- `title`: El título o descripción de la tarea.
- `completed`: Un campo booleano que indica si la tarea ha sido completada o no.

### Servicio (`TodoService`)

El **servicio** encapsula la lógica de negocio de la aplicación. En este caso, `TodoService` implementa la lógica necesaria para interactuar con la base de datos a través de la entidad `Todo`. El servicio expone métodos para:
- Obtener todas las tareas.
- Crear una nueva tarea.
- Actualizar una tarea existente.
- Eliminar una tarea por su ID.

El servicio sigue el patrón **Inyección de Dependencias** de Spring para utilizar un **repositorio** que maneja las operaciones con la base de datos. De esta forma, el controlador solo se enfoca en manejar las solicitudes HTTP, delegando la lógica de negocio al servicio.

### Configuración de la Base de Datos

La conexión a PostgreSQL está configurada en el archivo `application.properties` de la siguiente manera:

```properties
# Configuración de la base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/mi_base_de_datos
spring.datasource.username=mi_usuario
spring.datasource.password=mi_contraseña
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de JPA (para la creación automática del esquema de base de datos)
spring.jpa.hibernate.ddl-auto=update
## Configuración de Base de Datos - PostgreSQL

El backend de la aplicación está configurado para utilizar **PostgreSQL** como base de datos relacional. A continuación, se describen los pasos para configurar PostgreSQL y conectarla con la aplicación Spring Boot.

### Instalación y Configuración de PostgreSQL

1. Instala **PostgreSQL** en tu sistema operativo siguiendo las instrucciones correspondientes:

    - **macOS**:
      ```bash
      brew install postgresql
      brew services start postgresql
      ```
    - **Ubuntu/Linux**:
      ```bash
      sudo apt update
      sudo apt install postgresql postgresql-contrib
      sudo systemctl start postgresql
      ```
    - **Windows**: Descarga e instala PostgreSQL desde [PostgreSQL.org](https://www.postgresql.org/download/).

2. Una vez instalado PostgreSQL, abre el cliente de línea de comandos con el siguiente comando:

    ```bash
    psql postgres
    ```

3. Crea un nuevo usuario y una base de datos para la aplicación. Usa los siguientes comandos dentro de la terminal de `psql`:

    ```sql
    CREATE USER mi_usuario WITH PASSWORD 'mi_contraseña';
    CREATE DATABASE mi_base_de_datos;
    GRANT ALL PRIVILEGES ON DATABASE mi_base_de_datos TO mi_usuario;
    ```

4. Sal de la consola de `psql`:

    ```sql
    \q
    ```

### Configuración del Proyecto Spring Boot

1. Abre el archivo `application.properties` de tu proyecto Spring Boot y añade la siguiente configuración para conectarte a PostgreSQL:

    ```properties
    # Configuración de la base de datos PostgreSQL
    spring.datasource.url=jdbc:postgresql://localhost:5432/mi_base_de_datos
    spring.datasource.username=mi_usuario
    spring.datasource.password=mi_contraseña
    spring.datasource.driver-class-name=org.postgresql.Driver

    # Configuración de JPA (opcional, para actualizar automáticamente el esquema de la base de datos)
    spring.jpa.hibernate.ddl-auto=update
    ```

2. Asegúrate de que el archivo `pom.xml` incluye la dependencia de PostgreSQL. Si no está presente, añádela:

    ```xml
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>
    ```

3. Si estás utilizando Docker para ejecutar PostgreSQL en contenedores, asegúrate de exponer el puerto adecuado (5432) en el archivo `docker-compose.yml`:

    ```yaml
    services:
      postgres:
        image: postgres:latest
        environment:
          POSTGRES_DB: mi_base_de_datos
          POSTGRES_USER: mi_usuario
          POSTGRES_PASSWORD: mi_contraseña
        ports:
          - "5432:5432"
        volumes:
          - ./data:/var/lib/postgresql/data
    ```

### Verificación y Arranque

1. Ejecuta tu aplicación Spring Boot y verifica que se conecte correctamente a PostgreSQL. Si tienes configurada la opción `spring.jpa.hibernate.ddl-auto=update`, las tablas necesarias se crearán automáticamente en la base de datos.

2. (Opcional) Usa herramientas como **pgAdmin** o **DBeaver** para conectarte a PostgreSQL y visualizar las tablas creadas o administrar la base de datos gráficamente.

### Notas Finales

- PostgreSQL se ejecuta por defecto en el puerto `5432`. Asegúrate de que este puerto esté disponible en tu entorno de desarrollo.
- Si deseas gestionar manualmente las tablas y datos de tu base de datos, puedes usar `psql` o cualquier otro cliente SQL compatible con PostgreSQL.