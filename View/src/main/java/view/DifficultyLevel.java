package view;

import model.SudokuBoard;

import java.util.Random;

public class DifficultyLevel {


    private Level level;

    public Level getLevel() {
        return level;
    }


    public DifficultyLevel(Level level) {
        this.level = level;
    }

    public void removeFildsFromBoard(SudokuBoard board) {
        int changes = level.getNumber();


        while (changes != 0) {
            int x = new Random().nextInt(9);
            int y = new Random().nextInt(9);
            if (board.get(x, y) != 0) {
                board.set(x, y, 0);
                board.getBoard().get(x * 9 + y).setFilledValue(false);
                changes--;
            }
        }

    }
}
