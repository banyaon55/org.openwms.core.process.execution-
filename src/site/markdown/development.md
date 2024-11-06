# Development Process

```
$ mvn package
```

# Release
Building the service and deploying it to the Sonatype staging repository works like:
 
```
$ mvn clean deploy -Prelease,gpg
```
