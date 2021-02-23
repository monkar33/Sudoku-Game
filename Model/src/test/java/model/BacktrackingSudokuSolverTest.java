package model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solve() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        b.solveGame();
        for(int i=0; i<9; i++){
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int j=0; j<9; j++){
                list.add(b.get(i,j));
            }
            for(int j=1; j<10; j++){
                assertEquals(list.contains(j), true);
            }

        }

        for(int i=0; i<9; i++){
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int j=0; j<9; j++){
                list.add(b.get(i,j));
            }
            for(int j=1; j<10; j++){
                assertEquals(list.contains(j), true);
            }

        }

        for(int x=0; x<9; x+=3){
            for(int y=0; y<9; y+=3){
                int boxRowBegin = x - x % 3;
                int boxColBegin = y - y % 3;
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int i = boxRowBegin; i < boxRowBegin + 3; i++) {

                    for (int j = boxColBegin; j < boxColBegin + 3; j++) {
                        list.add(b.get(i,j));
                    }
                }
                for(int j=1; j<10; j++){
                    assertEquals(list.contains(j), true);
                }
            }
        }
    }


}