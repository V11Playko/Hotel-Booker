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

## FAQ

#### ¿Qué es Hotel-Booker?
Hotel-Booker es una aplicación para gestionar la reserva de hoteles. Ofrece funcionalidades como la gestión de usuarios, la autenticación con contraseña o a través de Google, la gestión de hoteles y habitaciones, y la creación de reservas.

#### ¿Cómo puedo iniciar sesión en Hotel-Booker?
Puedes iniciar sesión en Hotel-Booker utilizando tu cuenta y contraseña o mediante tu cuenta de Google. Ambas opciones están disponibles.

#### ¿Cómo se gestionan los usuarios en Hotel-Booker?
La gestión de usuarios se realiza a través del user-service, que permite operaciones como guardar usuarios, buscar usuarios por correo electrónico y obtener detalles de los propietarios, empleados y clientes utilizando sus respectivos roles.

#### ¿Qué funcionalidades ofrece el hotel-service?
El hotel-service ofrece funcionalidades como:
- Guardar hoteles junto con sus habitaciones.
- Listar hoteles con paginación.
- Encontrar todos los hoteles.
- Encontrar un hotel por su ID.
- Calcular las proporciones de las habitaciones según la categoría de estrellas.

#### ¿Cómo se gestionan las reservas en Hotel-Booker?
El reservation-service gestiona las reservas permitiendo:
- Guardar reservas.
- Encontrar todas las reservas.
- Actualizar el estado de las habitaciones reservadas.
- Verificar periódicamente las reservas expiradas para actualizar el estado de las habitaciones y reservas.

#### ¿Cómo se manejan las habitaciones en Hotel-Booker?
El room-service maneja las habitaciones permitiendo obtener una lista de habitaciones por hotel con paginación y gestionando su disponibilidad y estado según las reservas.

#### ¿Hay algún método para generar reportes en Hotel-Booker?
Sí, el sistema cuenta con métodos para crear reportes en formato Excel y PDF, lo cual facilita la generación de informes detallados sobre reservas, habitaciones y otros aspectos del sistema.

#### ¿Cómo se maneja la seguridad y la codificación de contraseñas?
Las contraseñas de los usuarios se codifican utilizando el IAuthPasswordEncoderPort antes de ser almacenadas en la base de datos, garantizando la seguridad de la información.

#### ¿Qué ocurre si intento reservar una habitación que ya no está disponible?
El reservation-service verifica la disponibilidad de las habitaciones antes de confirmar una reserva. Si una habitación ya no está disponible, se lanza una excepción RoomUnavailableException.

#### ¿Puedo contribuir al proyecto?
¡Por supuesto! Las contribuciones son bienvenidas. Puedes clonar el repositorio, crear ramas para tus características o correcciones, y enviar pull requests para revisión. Asegúrate de seguir las directrices de contribución del proyecto.

#### ¿Dónde puedo encontrar más documentación?
Para más detalles sobre la implementación y uso de los servicios, revisa la documentación del código en los respectivos archivos de servicio y los comentarios incluidos.


## Licencia ##

Este proyecto está licenciado bajo la Licencia GNU. Consulta el archivo LICENSE para más información.

## Comentario ##
Si tienes algún comentario sobre el repositorio, por favor dímelo para poder mejorar :)

- 📫 Cómo contactarme **heinnervega20@gmail.com**