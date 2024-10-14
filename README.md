
# Evaluación

Este repositorio contiene dos microservicios:

- **clientService**: Maneja la gestión de Clientes y Personas.
- **accountService**: Maneja la gestión de Cuentas y Movimientos.

## Postman Collection

Se adjunta una colección de Postman para facilitar la prueba de los microservicios.

![Postman Collection](https://github.com/user-attachments/assets/ec2bc16e-157c-42f0-a500-0b415f6ae0ba)

## Ejecución

Para ejecutar el proyecto, sigue estos pasos:

1. **Crear la Tabla**:
   - Asegúrate de que exista una base llamada `example` en tu base de datos PostgreSQL. 
2. **Configura la base de datos en los microservicios**:
   - Configura la ruta usuario y clave en el archivo application.properties
  spring.r2dbc.url=r2dbc:postgresql://localhost:5432/example
  spring.r2dbc.username=postgres
  spring.r2dbc.password=Clave.10

3. **Ejecutar los contenedores**:
