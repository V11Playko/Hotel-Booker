#  Hotel Booker #

Este proyecto se enfoca en el desarrollo de una APIREST diseñada para gestionar hoteles y sus respectivas habitaciones, segun la cantidad de estrellas que el hotel tenga va a ofrecer diferentes cosas y tipos de habitaciones, se creo para aprender a crear informes en este caso en Excel y PDF.

## Instalación y configuración ##

Antes de empezar, asegúrate de tener instalados y configurados los siguientes elementos:

1. Java 17
2. Gradle 8.2
3. Base de datos PostgreSQL (con las variables de entorno USER y PASSWORD ya configuradas).

Para instalar y configurar la aplicación en un entorno local, sigue estos pasos:

1. Clona este repositorio en tu máquina local.
2. Importa el proyecto en tu IDE favorito.
3. Instala las dependencias del proyecto usando Gradle.
4. Crea dos bases de datos PostgreSQL con el nombre "usuarios-service" y "parqueadero-service" respectivamente.
5. Configura las variables de entorno necesarias para la base de datos.
    - Abre tu IDE y encuentra la configuración de ejecución para el microservicio 'parking-service' o en el que estes actualmente.
    - En el caso de IntelliJ IDEA, puedes hacer esto yendo a 'Run/Debug Configurations'.
    - Busca la configuración correspondiente al microservicio 'parking-service' y haz clic para editarla.
    - Dentro de la configuración, busca la sección 'Environment variables'.
    - Agrega las siguientes variables de entorno con los valores que hayas decidido para tu base de datos PostgreSQL:
        - USER: es el username que tienes en la base de datos.

      (En el caso de PgAdmin se encuentra en PostgreSQL->Properties->Connection->Username)

        - PASSWORD: la contraseña de la base de datos.
6. Ejecuta los tres microservicios.
7. Consulta la documentación de la API de User-Service para obtener más detalles sobre los endpoints disponibles en `http://localhost:8091/swagger-ui/index.html#/`.
8. Consulta la documentación de la API de Hotel-Service para obtener más detalles sobre los endpoints disponibles en `http://localhost:8092/swagger-ui/index.html#`.
9. Tambien puedes ver y exportar la [colección de postman.](docs/Hotel-Booker.postman_collection)

## Licencia ##

Este proyecto está licenciado bajo la Licencia GNU. Consulta el archivo LICENSE para más información.

## Comentario ##
Si tienes algún comentario sobre el repositorio, por favor dímelo para poder mejorar :)

- 📫 Cómo contactarme **heinnervega20@gmail.com**