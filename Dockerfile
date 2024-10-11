# Usa una imagen base de OpenJDK para construir y ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR del proyecto
COPY target/todo-app-springboot-0.0.1-SNAPSHOT.jar /app/todo-app-backend.jar

# Verifica que el archivo JAR se haya copiado correctamente
RUN ls -la /app

# Expone el puerto 8080 para acceder a la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/todo-app-backend.jar"]