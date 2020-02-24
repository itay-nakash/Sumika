package com.example.itay.sumika;

import android.graphics.Point;
import android.widget.Button;

import java.io.Serializable;

/**
 * Created by Itay on 24/01/2018.
 */

public class MiniMaxAI {
    GameLogic gmLogic;
    int playerNum;
    int[][] cells;
    int turn;
    int maxPly, validSpace;
    Point lastChocie;
    Point pointOfBestMove;
    boolean pointNull = true;


    public MiniMaxAI(int playerNum, int[][] cells, int maxPly, Point lastChocie, int turn, int validSpace) {
        this.playerNum = playerNum;
        this.turn = turn;
        this.validSpace = validSpace;
        this.maxPly = maxPly;
        this.cells = cells;
        this.lastChocie = lastChocie;
        this.gmLogic=new GameLogic();
    }

    public Point callMiniMax() {
        miniMax(cells, lastChocie, 0);
        return pointOfBestMove;
    }


    private int miniMax(int[][] cells, Point lastChoice, int currentPly) {
        currentPly++;
        if (currentPly >= maxPly || gmLogic.checkEndOfGame(cells, lastChoice, validSpace)) {// if it terminal return the value of this move
            return score();
        }

        if (gmLogic.getWhoseTurn(turn) == playerNum) {
            return getMax(cells, currentPly);
        } else {
            return getMin(cells, currentPly);
        }

    }

    private int getMax(int[][] cells, int currentPly) {
        int bestScore = Integer.MIN_VALUE;
        pointOfBestMove = new Point(-100, -100);
        int sizeOfAvailable = gmLogic.getAvailableMoves(cells, lastChocie, validSpace).size();
        //for (Integer theMove : board.getAvailableMoves()) {
        for (int i = 0; i < gmLogic.getAvailableMoves(cells, lastChocie, validSpace).size(); i++) {
            int[][] modifiedCells = gmLogic.deepCopyIntMatrix(cells);
            Point p = gmLogic.getAvailableMoves(modifiedCells, lastChocie, validSpace).get(i);
            lastChocie = gmLogic.addChoiceLogic(modifiedCells, p);
            turn++;
            int score = miniMax(modifiedCells,lastChocie, currentPly);
            if (score >= bestScore) {
                pointNull = false;
                bestScore = score;
                pointOfBestMove = p;
            }
        }

        return bestScore;
    }


    private int getMin(int[][] cells, int currentPly) {
        int bestScore = Integer.MAX_VALUE;
        pointOfBestMove = new Point(-100, -100);
        int sizeOfAvailable = gmLogic.getAvailableMoves(cells, lastChocie, validSpace).size();
        for (int i = sizeOfAvailable-1; i >= 0; i--) {
            int[][] modifiedCells = gmLogic.deepCopyIntMatrix(cells);
            Point p = gmLogic.getAvailableMoves(modifiedCells, lastChocie, validSpace).get(i);
            gmLogic.addChoiceLogic(modifiedCells, p);
            turn++;
            int score = miniMax(modifiedCells,lastChocie, currentPly);
            if (score < bestScore) {
                pointNull = false;
                bestScore = score;
                //TODO the point saves the last move of the tree and not the first.
                pointOfBestMove = p;
            }
        }
        return bestScore;
    }

    //if this function got to the end of the game(it depends on the depth of the search) it will return the score
    //else it will return 0 as a represent of neutral
    private int score() {
        int opponentNum=-100;
        if (playerNum == 1)
            opponentNum = 2;

        if (gmLogic.checkEndOfGame(cells, lastChocie, validSpace) && gmLogic.getWhoseTurn(turn) == opponentNum) {
            return 1;
        } else if (gmLogic.checkEndOfGame(cells, lastChocie, validSpace) && gmLogic.getWhoseTurn(turn) == playerNum) {
            return -1;
        } else {
            return 0;
        }
    }
}
