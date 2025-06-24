# Usa una imagen base oficial de OpenJDK con la versión 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml para descargar las dependencias
COPY pom.xml .

# Copia el wrapper de Maven si lo usas (mvnw y mvnw.cmd)
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Copia el resto del código fuente de la aplicación
COPY src ./src

# Dale permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Construye la aplicación usando Maven Wrapper
# La bandera -Dmaven.test.skip=true omite la ejecución de pruebas para acelerar la construcción de la imagen si es necesario
RUN ./mvnw clean package -Dmaven.test.skip=true

# Expón el puerto que tu aplicación usará (cambia 8080 si tu aplicación usa otro puerto)
EXPOSE 8080

# Define el comando para ejecutar la aplicación cuando el contenedor se inicie
# Deberás reemplazar 'your-application.jar' con el nombre real de tu archivo JAR
# Puedes encontrar el nombre exacto después de ejecutar la construcción de Maven
CMD ["java", "-jar", "target/your-application.jar"]