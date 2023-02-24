# MAGMACT Parser #

First version of the [MAGMACT-Gen](https://github.com/acm-ribeiro/magmact-gen) parser. This takes as input an
OpenAPI Specification extended with MAGMACT-Gen and serializes it. 

### 🔷 Generate Executable JAR ###
To generate the executable `jar` file: 

`mvn compile assembly:single`

### 🔷 Maven ###
To add this to your project, add the following dependency to the `pom.xml` file: 

```xml
<dependency>
    <groupId>magmact</groupId>
    <artifactId>magmact-parser</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${basedir}/lib/magmact-parser.jar</systemPath>
</dependency>
```

Assuming `magmact-parser.jar` is in the `lib` directory. 

