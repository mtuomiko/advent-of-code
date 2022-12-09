package com.mtuomiko;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class Day03 {
    private Day03() {
    }

    public static void solverA(Path input) {
        var zeroDeltaArray = readZeroDeltaArray(input);
        var gammaBinaryIntArray = Arrays.stream(zeroDeltaArray).map(value -> {
            if (value < 0) return 0;
            else if (value > 0) return 1;
            throw new RuntimeException("Cannot decide, zero means same amount of ones and zeros was found");
        }).toArray();

        var gamma = 0;
        var length = gammaBinaryIntArray.length;
        for (int i = 0; i < length; i++) {
            var bitAsInt = gammaBinaryIntArray[length - 1 - i]; // possible int values 1 and 0
            gamma |= bitAsInt << i; // set bit at position i to bitAsInt (does "useless" zero set ops)
        }

        var epsilon = invertUsingMask(gamma, length);
        long result = (long) gamma * epsilon;

        System.out.println("Gamma times epsilon = " + result);
    }

    public static void solverB(Path input) {

    }

    private static int invertUsingMask(int value, int maskSize) {
        var bitmask = (1 << maskSize) - 1;
        return value ^ bitmask;
    }

    /**
     * Reads input file bit strings and counts relative commonness of ones and zeros on each position. Returns an
     * integer array where:
     * <ul>
     * <li>Negative values mean zero was more common by value, on corresponding position.
     * <li>Positive values mean one was more common by value, "
     * <li>Zero values would mean equal count of ones and zeros. Based on challenge description this shouldn't happen
     * but this function doesn't consider this.
     */
    private static int[] readZeroDeltaArray(Path input) {
        try (var reader = Files.newBufferedReader(input)) {
            var line = reader.readLine();
            var length = line.length();
            var zeroDeltaArray = new int[length];
            Arrays.fill(zeroDeltaArray, 0);

            while (line != null) {
                for (var i = 0; i < length; i++) {
                    var lineBit = line.charAt(i);
                    if (lineBit == '1') zeroDeltaArray[i]++;
                    else if (lineBit == '0') zeroDeltaArray[i]--;
                }
                line = reader.readLine();
            }

            return zeroDeltaArray;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
