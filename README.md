# APOSTLGen Parser #

First version of the [APOSTLGen](https://bitbucket.org/acmribeiro/apostl-gen/src/master/) parser. This takes as input an
OpenAPI Specification extended with APOSTLGen and serializes it. 

### 🔷 Generate Executable JAR ###
To generate the executable `jar` file: 

`mvn compile assembly:single`

### 🔷 Maven ###
To add this to your project, add the following dependency to the `pom.xml` file: 

```xml
<dependency>
    <groupId>apostl</groupId>
    <artifactId>apostl-gen-parser</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${basedir}/lib/apostl-gen-parser.jar</systemPath>
</dependency>
```

Assuming `apostl-gen-parser.jar` is in the `lib` directory. 

