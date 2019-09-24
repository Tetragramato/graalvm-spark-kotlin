# Graalvm-spark-kotlin

NOTE : This project actually generate a "fallback" native image, not a "true" native image.

Reference : https://github.com/oracle/graal/issues/1350

## Description

Simple "Hello World" POC with :

- Spark Java (http://sparkjava.com/)
- Kotlin
- Gradle with Kotlin-dsl
- Docker with GraalVM (https://hub.docker.com/r/oracle/graalvm-ce/) for generating native-image

## How to

In command line :

```
docker build -t com.tetragramato/graal-spark-kotlin .
```

And after :

```
docker run -p 4567:4567 --name graalvm-poc com.tetragramato/graal-spark-kotlin:latest 
```

Finally on your navigator :

```
http://localhost:4567/hello
```

Et voilà ! You run a GraalVM Application, with a simple service responding "Hello World".
