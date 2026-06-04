# Attendance System Backend

Este es el backend del sistema de asistencia, construido con Spring Boot.

## Requisitos previos
- Java (la versión configurada en el proyecto, por ejemplo Java 17 o 21).
- No es necesario tener Maven instalado localmente, ya que este proyecto utiliza **Maven Wrapper** (`mvnw`).

## Cómo levantar la aplicación

Para correr el servidor backend de manera local, utiliza el wrapper de Maven que ya viene incluido en esta carpeta.

### En Linux / macOS
Abre una terminal en la carpeta `backend` y ejecuta el siguiente comando:

```bash
./mvnw spring-boot:run
```

*(Nota: Si el sistema te indica que no tienes permisos, puedes darle permisos de ejecución al archivo corriendo `chmod +x mvnw` primero).*

### En Windows
Abre una consola de comandos (CMD o PowerShell) en la carpeta `backend` y ejecuta:

```cmd
mvnw.cmd spring-boot:run
```

## Otros comandos útiles

- **Construir el proyecto (generar el archivo .jar ejecutable):**
  ```bash
  ./mvnw clean package
  ```

- **Ejecutar las pruebas (tests):**
  ```bash
  ./mvnw test
  ```
