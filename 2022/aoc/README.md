# Advent of Code 2022

[Advent of Code 2022](https://adventofcode.com/2022) challenge implementation repository. Main language Kotlin. Gradle
used as the build system.

Solvers for different challenges are used through command line / program arguments. Provide the solver and the input
filename as arguments. See [Main.kt](src/main/kotlin/Main.kt) and [resources](src/main/resources) for listings. So 
input files are read from resources.

For example `./gradlew run --args="01a 01_input.txt"`

### Random

Some solutions might end up in separate packages to use top level functions without naming conflicts.
