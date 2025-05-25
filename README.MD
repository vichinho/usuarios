📝 Sistema de Gestión de Usuarios - Spring Boot API
Java
Spring Boot
PostgreSQL

API RESTful para gestión de usuarios con roles y permisos, desarrollada con Spring Boot y PostgreSQL.

📋 Características Principales
✅ CRUD completo de usuarios

🛡️ Sistema de roles y permisos

🔒 Autenticación JWT (próximamente)

📊 Consultas personalizadas

🧩 Validación de datos

🛠️ Tecnologías Utilizadas
Backend:

Java 17

Spring Boot 3.1.5

Spring Data JPA

Spring Validation

Base de Datos:

PostgreSQL 15

H2 para testing

Herramientas:

Maven

Lombok

MapStruct (próximamente)

🚀 Instalación y Configuración
Clonar el repositorio:

bash
git clone https://github.com/vichinho/usuarios.git
cd usuarios
Configurar base de datos:

Crear base de datos PostgreSQL llamada usuarios_db

Configurar credenciales en application.properties:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/usuarios_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
