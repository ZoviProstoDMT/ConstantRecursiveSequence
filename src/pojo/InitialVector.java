package pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialVector {

    private final int size;

    public InitialVector(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static List<List<Integer>> getInitialVectors(int size, int mod) {
        if (size == 2 && mod == 4) {
            List<List<Integer>> initialVectors = new ArrayList<>();
            initialVectors.add(Arrays.asList(0, 0));
            initialVectors.add(Arrays.asList(0, 1));
            initialVectors.add(Arrays.asList(0, 2));
            initialVectors.add(Arrays.asList(0, 3));
            initialVectors.add(Arrays.asList(1, 0));
            initialVectors.add(Arrays.asList(1, 1));
            initialVectors.add(Arrays.asList(1, 2));
            initialVectors.add(Arrays.asList(1, 3));
            initialVectors.add(Arrays.asList(2, 0));
            initialVectors.add(Arrays.asList(2, 1));
            initialVectors.add(Arrays.asList(2, 2));
            initialVectors.add(Arrays.asList(2, 3));
            initialVectors.add(Arrays.asList(3, 0));
            initialVectors.add(Arrays.asList(3, 1));
            initialVectors.add(Arrays.asList(3, 2));
            initialVectors.add(Arrays.asList(3, 3));
            return initialVectors;
        }
        if (size == 3 && mod == 2) {
            List<List<Integer>> initialVectors = new ArrayList<>();
            initialVectors.add(Arrays.asList(0, 0, 0));
            initialVectors.add(Arrays.asList(0, 0, 1));
            initialVectors.add(Arrays.asList(0, 1, 0));
            initialVectors.add(Arrays.asList(1, 0, 0));
            initialVectors.add(Arrays.asList(1, 1, 0));
            initialVectors.add(Arrays.asList(0, 1, 1));
            initialVectors.add(Arrays.asList(1, 0, 1));
            initialVectors.add(Arrays.asList(1, 1, 1));
            return initialVectors;
        } else if (size == 4 && mod == 2) {
            List<List<Integer>> initialVectors = new ArrayList<>();
            initialVectors.add(Arrays.asList(0, 0, 0, 0));
            initialVectors.add(Arrays.asList(0, 0, 0, 1));
            initialVectors.add(Arrays.asList(0, 0, 1, 0));
            initialVectors.add(Arrays.asList(0, 1, 0, 0));
            initialVectors.add(Arrays.asList(1, 0, 0, 0));
            initialVectors.add(Arrays.asList(0, 0, 1, 1));
            initialVectors.add(Arrays.asList(0, 1, 1, 0));
            initialVectors.add(Arrays.asList(1, 1, 0, 0));
            initialVectors.add(Arrays.asList(0, 1, 0, 1));
            initialVectors.add(Arrays.asList(1, 0, 1, 0));
            initialVectors.add(Arrays.asList(1, 0, 0, 1));
            initialVectors.add(Arrays.asList(0, 1, 1, 1));
            initialVectors.add(Arrays.asList(1, 0, 1, 1));
            initialVectors.add(Arrays.asList(1, 1, 1, 0));
            initialVectors.add(Arrays.asList(1, 1, 0, 1));
            initialVectors.add(Arrays.asList(1, 1, 1, 1));
            return initialVectors;
        } else if (size == 3 && mod == 4) {
            List<List<Integer>> initialVectors = new ArrayList<>();
            initialVectors.add(Arrays.asList(0, 0, 0));
            initialVectors.add(Arrays.asList(0, 0, 1));
            initialVectors.add(Arrays.asList(0, 0, 2));
            initialVectors.add(Arrays.asList(0, 0, 3));
            initialVectors.add(Arrays.asList(0, 1, 0));
            initialVectors.add(Arrays.asList(0, 2, 0));
            initialVectors.add(Arrays.asList(0, 3, 0));
            initialVectors.add(Arrays.asList(1, 0, 0));
            initialVectors.add(Arrays.asList(2, 0, 0));
            initialVectors.add(Arrays.asList(3, 0, 0));
            initialVectors.add(Arrays.asList(1, 1, 0));
            initialVectors.add(Arrays.asList(1, 2, 0));
            initialVectors.add(Arrays.asList(1, 3, 0));
            initialVectors.add(Arrays.asList(2, 1, 0));
            initialVectors.add(Arrays.asList(2, 2, 0));
            initialVectors.add(Arrays.asList(2, 3, 0));
            initialVectors.add(Arrays.asList(3, 1, 0));
            initialVectors.add(Arrays.asList(3, 2, 0));
            initialVectors.add(Arrays.asList(3, 3, 0));
            initialVectors.add(Arrays.asList(0, 1, 1));
            initialVectors.add(Arrays.asList(0, 1, 2));
            initialVectors.add(Arrays.asList(0, 1, 3));
            initialVectors.add(Arrays.asList(0, 2, 1));
            initialVectors.add(Arrays.asList(0, 2, 2));
            initialVectors.add(Arrays.asList(0, 2, 3));
            initialVectors.add(Arrays.asList(0, 3, 1));
            initialVectors.add(Arrays.asList(0, 3, 2));
            initialVectors.add(Arrays.asList(0, 3, 3));
            initialVectors.add(Arrays.asList(1, 0, 1));
            initialVectors.add(Arrays.asList(1, 0, 2));
            initialVectors.add(Arrays.asList(1, 0, 3));
            initialVectors.add(Arrays.asList(2, 0, 1));
            initialVectors.add(Arrays.asList(2, 0, 2));
            initialVectors.add(Arrays.asList(2, 0, 3));
            initialVectors.add(Arrays.asList(3, 0, 1));
            initialVectors.add(Arrays.asList(3, 0, 2));
            initialVectors.add(Arrays.asList(3, 0, 3));
            initialVectors.add(Arrays.asList(1, 1, 1));
            initialVectors.add(Arrays.asList(1, 1, 2));
            initialVectors.add(Arrays.asList(1, 1, 3));
            initialVectors.add(Arrays.asList(1, 2, 1));
            initialVectors.add(Arrays.asList(1, 2, 2));
            initialVectors.add(Arrays.asList(1, 2, 3));
            initialVectors.add(Arrays.asList(1, 3, 1));
            initialVectors.add(Arrays.asList(1, 3, 2));
            initialVectors.add(Arrays.asList(1, 3, 3));
            initialVectors.add(Arrays.asList(2, 1, 1));
            initialVectors.add(Arrays.asList(2, 1, 2));
            initialVectors.add(Arrays.asList(2, 1, 3));
            initialVectors.add(Arrays.asList(2, 2, 1));
            initialVectors.add(Arrays.asList(2, 2, 2));
            initialVectors.add(Arrays.asList(2, 2, 3));
            initialVectors.add(Arrays.asList(2, 3, 1));
            initialVectors.add(Arrays.asList(2, 3, 2));
            initialVectors.add(Arrays.asList(2, 3, 3));
            initialVectors.add(Arrays.asList(3, 1, 1));
            initialVectors.add(Arrays.asList(3, 1, 2));
            initialVectors.add(Arrays.asList(3, 1, 3));
            initialVectors.add(Arrays.asList(3, 2, 1));
            initialVectors.add(Arrays.asList(3, 2, 2));
            initialVectors.add(Arrays.asList(3, 2, 3));
            initialVectors.add(Arrays.asList(3, 3, 1));
            initialVectors.add(Arrays.asList(3, 3, 2));
            initialVectors.add(Arrays.asList(3, 3, 3));
            return initialVectors;
        }
        return new ArrayList<>();
    }
}
