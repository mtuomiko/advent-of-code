package com.mtuomiko.day02;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PositionCollector
        implements Collector<String, MutablePair<Integer, Integer>, MutablePair<Integer, Integer>> {
    private static final String FORWARD = "forward";
    private static final String DOWN = "down";
    private static final String UP = "up";

    public static PositionCollector toPosition() {
        return new PositionCollector();
    }

    @Override
    public Supplier<MutablePair<Integer, Integer>> supplier() {
        return () -> new MutablePair<>(0, 0);
    }

    @Override
    public BiConsumer<MutablePair<Integer, Integer>, String> accumulator() {
        return (position, command) -> {
            var tokens = command.split(" ");
            var amount = Integer.parseInt(tokens[1]);
            switch (tokens[0]) {
                case FORWARD -> position.setLeft(position.left + amount);
                case DOWN -> position.setRight(position.right + amount);
                case UP -> position.setRight(position.right - amount);
                default -> throw new RuntimeException("Unknown direction");
            }
        };
    }

    @Override
    public BinaryOperator<MutablePair<Integer, Integer>> combiner() {
        return (position1, position2) -> {
            position1.setLeft(position1.left + position2.left);
            position1.setRight(position1.right + position2.right);
            return position1;
        };
    }

    @Override
    public Function<MutablePair<Integer, Integer>, MutablePair<Integer, Integer>> finisher() {
        return (position) -> position;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
