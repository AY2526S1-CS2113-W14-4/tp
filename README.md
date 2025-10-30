# Internity

Internity is a CLI based app for managing internship applications. Internity can help you manage and keep track of your internship applications more efficiently, perfect for Computer Science students who need to manage hundreds of applications.

Developed by CS2113-W14-4 Group

## Setting up in Intellij

Prerequisites: JDK 17 (use the exact version), update Intellij to the most recent version.

1. **Ensure Intellij JDK 17 is defined as an SDK**, as described [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) -- this step is not needed if you have used JDK 17 in a previous Intellij project.
1. **Import the project _as a Gradle project_**, as described [here](https://se-education.org/guides/tutorials/intellijImportGradleProject.html).
1. **Verify the setup**: After the importing is complete, locate the `src/main/java/internity/Internity.java` file, right-click it, and choose `Run Internity.main()`. If the setup is correct, you should see something like the below:
   ```
    Hello, welcome to
     ___       _                  _ _
    |_ _|_ __ | |_ ___ _ __ _ __ (_) |_ _   _
     | || '_ \| __/ _ \ '__| '_ \| | __| | | |
     | || | | | ||  __/ |  | | | | | |_| |_| |
    |___|_| |_|\__\___|_|  |_| |_|_|\__|\__, |
                                        |___/
    Be on top of your internships management with the Internity chatbot!
    What is your name?
   ```
   Check out our [User Guide](https://ay2526s1-cs2113-w14-4.github.io/tp/UserGuide.html) and [Developer Guide](https://ay2526s1-cs2113-w14-4.github.io/tp/DeveloperGuide.html) to find out how to use Internity!

## Testing

### I/O redirection tests

* To run _I/O redirection_ tests (aka _Text UI tests_), navigate to the `text-ui-test` and run the `runtest(.bat/.sh)` script.

### Gradle tests
* To run all JUnit tests using Gradle, use the command `./gradlew test` (Linux/Mac) or `gradlew test` (Windows).
* To run tests with detailed logging, you can modify the `build.gradle` file to include test logging configuration. Add the following snippet inside the `test` block:
  ```groovy
  test {
      testLogging {
          events "passed", "skipped", "failed"
          exceptionFormat "full"
          showStandardStreams = true
      }
  }
  ```
  After adding this configuration, running `./gradlew test` will provide detailed logs for each test case.