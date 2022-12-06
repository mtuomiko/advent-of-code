package com.mtuomiko;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    private static Map<String, Consumer<Path>> availableSolvers = Map.ofEntries(
            Map.entry("01a", Day01::solver01a), Map.entry("01b", Day01::solver01b)
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
