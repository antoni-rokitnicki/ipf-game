#Documentation:

## Start application:
### Development environment
```
java -jar automatic-cars-game-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### Testing REST Services
```
http://localhost:8080/swagger-ui.html
```

### Production environment
with default application.properites
```
java -jar automatic-cars-game-0.0.1-SNAPSHOT.jar
```
with custom application.properites
```
java -jar automatic-cars-game-0.0.1-SNAPSHOT.jar --spring.config.location=application.properties
```
more information about external configuration:
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html