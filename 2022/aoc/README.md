# Advent of Code 2022

[Advent of Code 2022](https://adventofcode.com/2022) challenge implementation repository. Main language Kotlin. Gradle
used as the build system.

Solvers for different challenges are used through command line / program arguments. Provide the solver and the input
filename as arguments. See [Main.kt](src/main/kotlin/Main.kt) and [resources](src/main/resources) for listings. Input 
files are read from resources using the filename, not by providing a path to the file.

For example `./gradlew run --args="01a 01_input.txt"`

### Random

Some solutions are stored in separate packages in order to use Kotlin top level functions without naming conflicts.
