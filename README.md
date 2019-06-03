#Graalvm-spark-kotlin

##Description

Simple "Hello World" POC with :

- Spark Java (http://sparkjava.com/)
- Kotlin
- Gradle with Kotlin-dsl
- Docker with GraalVM (https://hub.docker.com/r/oracle/graalvm-ce/) for generating native-image

##How

Just run :

```
./gradlew clean build  

./gradlew docker
```

And after :

```
docker run
-p 4567:4567
--name graalvm-poc
com.tetragramato/graalvm-spark-kotlin:latest 
```

Finally on your navigator :

```
http://localhost:4567/hello
```

Et voilà !!! You run a GraalVM native-image, with a simple service responding "Hello World".

##TODO

The native-image is running in the same Docker container than GraalVM -> Need to run the native-image on another "clean" Docker Container.