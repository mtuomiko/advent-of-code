package com.mtuomiko;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;

public final class Day01 {
    private Day01() {
    }

    public static void solverA(Path input) {
        var count = 0;
        try (var reader = Files.newBufferedReader(input)) {
            Integer previous = null;

            while (true) {
                var line = reader.readLine();
                if (line == null) {
                    break;
                }
                var value = Integer.parseInt(line);
                if (previous != null && value > previous) {
                    count++;
                }
                previous = value;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("increasing count " + count);
    }

    public static void solverB(Path input) {
        var windowSize = 3;
        var count = 0;
        try (var reader = Files.newBufferedReader(input)) {
            Integer previous = null;
            var deque = new ArrayDeque<Integer>(windowSize);

            while (true) {
                var line = reader.readLine();
                if (line == null) break;

                var value = Integer.parseInt(line);

                deque.addFirst(value);
                if (deque.size() < windowSize) continue;
                if (deque.size() > windowSize) deque.removeLast();

                var dequeSum = deque.stream().reduce(0, Integer::sum);
                if (previous != null && dequeSum > previous) count++;

                previous = dequeSum;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("increasing count " + count);
    }
}
