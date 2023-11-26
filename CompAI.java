package com.example.itay.sumika;

import android.graphics.Point;
import android.widget.Button;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by itay on 23/11/17.
 */

public class CompAI extends Player {

    boolean[][] available;
    GameLogic gmLogic = new GameLogic();

    public CompAI(int[][] cells, Player player) {
        super(player);
        available = new boolean[cells[0].length][cells.length];
        for (int rows = 0; rows < cells[0].length; rows++) {
            for (int columns = 0; columns < cells.length; columns++)
                available[rows][columns] = (cells[rows][columns] == 0);
        }
    }

    //old function, for only random
    public void play(GameManager gm, int[][] cells, Point lastChoice, int validSpace) {
        if (!gm.checkEndOfTheGame(cells, lastChoice, validSpace)) {
            if (gm.getUserClicks == false) {
                Point p = chooseRandomMove(cells, lastChoice, validSpace);
                gm.game(p);
            }
        } else {
            gm.announceWinner();
        }
    }

    public Point chooseRandomMove(int[][] cells, Point lastChoice, int validSpace) {
        Random rnd = new Random();
        int x, y;
        fillWithTrue(available);
        Point p = new Point(-100, -100);//initialize initial values to know if no point was found
        int rows = available.length;
        int columns = available[0].length;
        boolean foundSolution = false;
        //while (!foundSolution&& isThereAvailableMoves(available)){
        while (!foundSolution) {
            x = rnd.nextInt(columns);
            y = rnd.nextInt(rows);
            p.set(x, y);
            if (gmLogic.isValid(cells, p, lastChoice, validSpace)||!isThereAvailableMoves(available)) {
                available[x][y] = false;
                foundSolution = true;
            }
        }
        return p;
    }

    //with minimax
    public void test(GameManager gm, Player player2, int compLevel, int cells[][], Point lastChoice, int validSpace) {
        if (!gm.checkEndOfTheGame(cells, lastChoice, validSpace)) {
            if (gm.getUserClicks == false) {
                MiniMaxAI miniMaxAI = new MiniMaxAI(player2.getNumOfPlayer(), cells, 4, lastChoice, gm.getTurn(), validSpace);
                Point p = miniMaxAI.callMiniMax();
                if (miniMaxAI.pointNull) {
                    p = chooseRandomMove(cells, lastChoice, validSpace);
                }
                gm.game(p);
            } else {
                gm.announceWinner();
            }
        }
    }

    public void test2(GameManager gm, Player player2, int compLevel, int cells[][], Point lastChoice, int validSpace) {
        if (!gm.checkEndOfTheGame(cells, lastChoice, validSpace)) {
            Point p;
            if (gm.getUserClicks == false) {
                switch (compLevel) {
                    case (1):
                        p = easy(cells, lastChoice, validSpace);
                        gm.game(p);
                        break;

                    case (2):
                        p = chooseRandomMove(cells, lastChoice, validSpace);
                        gm.game(p);
                        break;

                    case (3):
                        p = hard(cells, lastChoice, validSpace);
                        gm.game(p);
                        break;
                }
            }
        } else {
            gm.announceWinner();
        }
    }

    public Point easy(int[][] cells, Point lastChoice, int validSpace) {
        boolean foundGoodSolution=false;
        fillWithTrue(available);
        Random rnd = new Random();
        int x, y;
        Point p = new Point(-100, -100);//initialize initial values to know if no point was found
        int rows = available.length;
        int columns = available[0].length;
        boolean foundSolution = false;
        while (!foundSolution) {
            x = rnd.nextInt(columns);
            y = rnd.nextInt(rows);
            p.set(x, y);
            available[p.x][p.y]=false;
            if(GameLogic.isValid(cells,p,lastChoice,validSpace)){
                int[][] modifiCells=GameLogic.deepCopyIntMatrix(cells);
                foundGoodSolution=!GameLogic.checkIfWiningMove(p, modifiCells, validSpace);
            }
            if (foundGoodSolution||!isThereAvailableMoves(available)) {
                foundSolution = true;
            }
        }
        if (!foundGoodSolution)
            p = chooseRandomMove(cells, lastChoice, validSpace);
        return p;
    }

    public Point hard(int[][] cells, Point lastChoice, int validSpace) {
        boolean foundGoodSolution=false;
        fillWithTrue(available);
        Random rnd = new Random();
        int x, y;
        Point p = new Point(-100, -100);//initialize initial values to know if no point was found
        int rows = available.length;
        int columns = available[0].length;
        boolean foundSolution = false;
        while (!foundSolution){
            x = rnd.nextInt(columns);
            y = rnd.nextInt(rows);
            p.set(x, y);
            available[p.x][p.y]=false;
            if(GameLogic.isValid(cells,p,lastChoice,validSpace)){
                int[][] modifiCells=GameLogic.deepCopyIntMatrix(cells);
                foundGoodSolution=GameLogic.checkIfWiningMove(p, modifiCells, validSpace);
            }
            if (foundGoodSolution||!isThereAvailableMoves(available))
                foundSolution = true;
        }
        if (!foundGoodSolution)
            p = chooseRandomMove(cells, lastChoice, validSpace);
        return p;
    }

    private boolean isThereAvailableMoves(boolean[][] available) {
        boolean isAvailable = false;
        for (int rows = 0; rows < available[0].length; rows++) {
            for (int columns = 0; columns < available.length && !isAvailable; columns++) {
                if (available[rows][columns])
                    isAvailable = true;
            }
        }
        return isAvailable;
    }

    private void fillWithTrue(boolean[][] available){
        for (int rows = 0; rows < available[0].length; rows++) {
            for (int columns = 0; columns < available.length;columns++) {
                available[rows][columns]=true;
            }
        }
    }
}
