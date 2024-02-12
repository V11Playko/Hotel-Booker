FROM openjdk:17

COPY build/hotel-booker.jar hotel-booker.jar

CMD ["java", "-jar", "hotel-booker.jar"]