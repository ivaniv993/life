package com.company;

import java.util.Set;

public class Life {

    public int[][] nextGeneration(int[][] oldGeneration) {
        int[][] newGeneration = new int[oldGeneration.length][oldGeneration.length];
        for (int i = 0; i < oldGeneration.length; i++) {
            for (int j = 0; j < oldGeneration.length; j++) {
                updateCell(oldGeneration, newGeneration, i, j);
            }
        }
        return newGeneration;
    }

    private void updateCell(int[][] oldGeneration, int[][] newGeneration, int x, int y) {
        long countNeighborhoods = countNeighborhoods(oldGeneration, x, y);
        if (countNeighborhoods == 3) {
            newGeneration[x][y] = 1;
        } else if (countNeighborhoods == 2) {
            newGeneration[x][y] = oldGeneration[x][y];
        } else {
            newGeneration[x][y] = 0;
        }
    }

    private long countNeighborhoods(int[][] life, int x, int y) {
        Set<Pair<Integer, Integer>> possibleNeighborhoods = Set.of(
                Pair.of(x + 1, y + 1),
                Pair.of(x + 1, y),
                Pair.of(x + 1, y - 1),
                Pair.of(x - 1, y + 1),
                Pair.of(x - 1, y),
                Pair.of(x - 1, y - 1),
                Pair.of(x, y + 1),
                Pair.of( x, y - 1)
        );

        return possibleNeighborhoods.parallelStream()
                .filter(pair -> pair.getLeft() >= 0 && pair.getLeft() < life.length && pair.getRight() >= 0 && pair.getRight() < life.length)
                .filter(pair -> life[pair.getLeft()][pair.getRight()] == 1)
                .count();
    }
}
