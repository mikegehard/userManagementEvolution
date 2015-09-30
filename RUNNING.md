# Building the UMS application

## Building the standalone jar

Run `../../gradlew build -x test` from a command line.

## Running the standalone jar

Run `java -jar path/to/jar`. Gradle puts the built jar into `build/libs` directory
for the application.

You can also use `./gradlew applications/ums:bootRun` to run the application.

## Windows users

Substitute `gradlew.bat` for `./gradlew`.

## Running the UAA

The UAA is included as a Git submodule. After cloing the repository,
you need to run `git submodule update --init`.

Run `./gradlew run` from the `platform/uaa` directory.

## Running the PACT tests

### Consumer side

See `pact-js-consumer-example` README for instructions on how to create the PACT from a Javascript consumer.

### Provider side

Ensure the provider (UMS) is able to fulfill the consumer defined PACT.

First start Eureka (currently a required service) in the terminal:

```
./gradlew platform/serviceDiscovery:bootRun
```

and leave it running.

Then in a separate terminal start UMS:

```
./gradlew applications/ums:bootRun
```

and leave it running.

Now open a separate terminal where you will verify UMS is able to fulfill the JS generated consumer PACT:

```
./gradlew applications/ums:pact
```
