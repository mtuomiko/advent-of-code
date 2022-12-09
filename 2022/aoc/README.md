# Advent of Code 2022

[Advent of Code 2022](https://adventofcode.com/2022) challenge implementation repository. Main language Kotlin. Gradle
used as the build system.

Solvers for different challenges are used through command line / program arguments. Provide the solver and the input
filename as arguments. See [Main.kt](src/main/kotlin/Main.kt) and [resources](src/main/resources) for listings.

For example `./gradlew run --args="01a 01_input.txt"`

### Random

By day 9 I was getting name clashes with having everything under the same package so stuff might end up in separate
packages.
