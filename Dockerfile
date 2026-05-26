# 1. Usar una imagen oficial de Java (Asumimos que usas Java 17, si usas la 21 cámbiala)
FROM eclipse-temurin:17-jdk-alpine

# 2. Información del puerto que usa tu app
EXPOSE 8080

# 3. Copiar el archivo .jar que generamos en el Paso 1 y renombrarlo a app.jar
COPY build/libs/*.jar app.jar

# 4. El comando que se ejecutará al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"]