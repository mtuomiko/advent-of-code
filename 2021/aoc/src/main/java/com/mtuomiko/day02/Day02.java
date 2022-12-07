package com.mtuomiko.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.tuple.MutablePair;

public final class Day02 {


    private Day02() {
    }

    public static void solverA(Path input) {
        long result;

        try (var reader = Files.newBufferedReader(input)) {
            var finalPosition = reader.lines().collect(PositionCollector.toPosition());
            result = (long) finalPosition.left * finalPosition.right;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Final position product is " + result);
    }

    public static void solverB(Path input) {
        long result;

        try (var reader = Files.newBufferedReader(input)) {
            var finalPosition = reader.lines().collect(PositionCollectorWithAim.toPositionUsingAim());
            result = (long) finalPosition.middle * finalPosition.right;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Final position product using aim is " + result);
    }
}
