package com.mtuomiko;

import com.mtuomiko.day02.Day02;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    private static Map<String, Consumer<Path>> availableSolvers = Map.ofEntries(
            Map.entry("01a", Day01::solverA), Map.entry("01b", Day01::solverB),
            Map.entry("02a", Day02::solverA), Map.entry("02b", Day02::solverB),
            Map.entry("03a", Day03::solverA), Map.entry("03b", Day03::solverB)
    );

    private static String resourcesPath = "src/main/resources/";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("give solver and input filename as arguments");
            return;
        }

        var path = Paths.get(resourcesPath + args[1]);
        var solver = availableSolvers.get(args[0]);

        solver.accept(path);
    }
}
