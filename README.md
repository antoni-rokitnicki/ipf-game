#Documentation:

## Start application:
### Development environment
```
java -jar ipf-game.jar --spring.profiles.active=dev
```

### Testing REST Services
```
http://localhost:8080/swagger-ui.html
```

### Database Client
```
http://localhost:8080/h2
```

### Production environment
with default application.properites
```
java -jar ipf-game.jar
```
with custom application.properites
```
java -jar ipf-game.jar --spring.config.location=application.properties
```

more information about external configuration:
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html