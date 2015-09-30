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
