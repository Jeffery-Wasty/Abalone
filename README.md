# Abalone AI Server

An Abalone AI that uses socket programming technique to communicate with the front-end client.

## Run the project
* Import the project into your Java IDE (recommend using IntelliJ IDEA)

### Run the AI Server

1. Run the main method of /network/AbaloneAIServer.java
2. Run the client program (in a separate repo/project)
3. Connect to the server using the client program

### Run test cases

1. Put test cases in the ./test folder. (.input/.board files)
2. Run the main method of /game/AbaloneNotationProcessor.java

## Build the project

This section provide instructions on building a executable .jar file using IntellliJ IDEA.

1. Update the main class in `build.gradle`
* For running the AI Server
```
manifest {
    attributes 'Main-Class': 'ca.bcit.abalone.network.AbaloneAIServer'
}
```
* For running the test cases
```
manifest {
    attributes 'Main-Class': 'ca.bcit.abalone.game.AbaloneNotationProcessor'
}
```
2. Go to `View > Tool Windows > Gradle`
3. Click and expand `Abalone > Tasks > build`
4. Double click `build` option under the build task
5. Go to the project directory and open the `build > libs` and the executable .jar file will be there
6. Run the following command in a command prompt window
```sh
$ java -jar JAR_FILE_NAME.jar
```

Please note that if you are running the test cases .jar, you must put the test cases in a `test` folder at the root of the .jar executable.