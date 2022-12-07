package com.mtuomiko.day02;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

// Triple without named parameters gets confusing but should suffice for this.
// left = aim, middle = horizontal position, right = vertical position
public class PositionCollectorWithAim implements Collector<
        String,
        MutableTriple<Integer, Integer, Integer>,
        MutableTriple<Integer, Integer, Integer>
        > {
    private static final String FORWARD = "forward";
    private static final String DOWN = "down";
    private static final String UP = "up";

    public static PositionCollectorWithAim toPositionUsingAim() {
        return new PositionCollectorWithAim();
    }

    @Override
    public Supplier<MutableTriple<Integer, Integer, Integer>> supplier() {
        return () -> new MutableTriple<>(0, 0, 0);
    }

    @Override
    public BiConsumer<MutableTriple<Integer, Integer, Integer>, String> accumulator() {
        return (position, command) -> {
            var tokens = command.split(" ");
            var amount = Integer.parseInt(tokens[1]);
            switch (tokens[0]) {
                case FORWARD -> {
                    position.setMiddle(position.middle + amount);
                    position.setRight(position.right + position.left * amount);
                }
                case DOWN -> position.setLeft(position.left + amount);
                case UP -> position.setLeft(position.left - amount);
                default -> throw new RuntimeException("Unknown direction");
            }
        };
    }

    @Override
    public BinaryOperator<MutableTriple<Integer, Integer, Integer>> combiner() {
        return (position1, position2) -> {
            position1.setLeft(position1.left + position2.left);
            position1.setMiddle(position1.middle + position2.middle);
            position1.setRight(position1.right + position2.right);
            return position1;
        };
    }

    @Override
    public Function<MutableTriple<Integer, Integer, Integer>, MutableTriple<Integer, Integer, Integer>> finisher() {
        return (position) -> position;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
