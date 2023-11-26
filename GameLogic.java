package com.example.itay.sumika;

import android.graphics.Point;
import android.os.CountDownTimer;

import java.util.ArrayList;

/**
 * Created by Itay on 18/02/2018.
 */

public class GameLogic {

    public GameLogic(){}

    public static Boolean isValid(int cells[][],Point p,Point lastChoice, int validSpace) {
        boolean isEmpty = cells[p.x][p.y]==0;
        boolean isCloseToLastChoice = isCloseToLastChoice(p, lastChoice, validSpace);
        return (isEmpty && !isCloseToLastChoice);
    }

    private static Boolean isCloseToLastChoice(Point p,Point lastChoice,int validSpace) {
        boolean isCloseToLastChoice = false;
        boolean okBackX = (lastChoice.x - p.x > validSpace) && (lastChoice.x > p.x);
        boolean okUpToY = (p.y - lastChoice.y > validSpace) && (p.y > lastChoice.y);
        boolean okAfterX = (p.x - lastChoice.x > validSpace) && (lastChoice.x < p.x);
        boolean okDownToY = (lastChoice.y - p.y > validSpace) && (lastChoice.y > p.y);
        if (!(okBackX || okAfterX || okDownToY || okUpToY)) {
            isCloseToLastChoice = true;
        }
        return isCloseToLastChoice;
    }

    public static ArrayList<Point> getAvailableMoves(int cells[][],Point lastChoice, int validSpace) {
        ArrayList<Point> availableMoves = new ArrayList<>();
        for (int y = 0; y < cells[0].length; y++) {
            for (int x = 0; x < cells.length; x++) {
                Point p = new Point();
                p.set(x, y);
                if (isValid(cells,p,lastChoice,validSpace)) {
                    availableMoves.add(p);
                }
            }
        }
        return availableMoves;
    }

    public static boolean checkIfWiningMove(Point p,int cells[][], int validSpace){
        boolean flag;
        cells[p.x][p.y]=999;
        flag=checkEndOfGame(cells,p,validSpace);
        return flag;
    }

    public static Boolean checkEndOfGame(int cells[][],Point lastChoice,int validSpace) {
        boolean isAvailableSpot = false;
        Point p = new Point(0, 0);
        for (int y = 0; y < cells[0].length&&!isAvailableSpot; y++) {// not sure cells.length is rows
            for (int x = 0; x < cells.length&&!isAvailableSpot; x++) {// not sure cells.length is colu
                p.set(x, y);
                if (isValid(cells,p,lastChoice,validSpace))
                    isAvailableSpot = true;
            }
        }
        return !isAvailableSpot;
    }

    public static Point addChoiceLogic(int[][] cells,Point lastChoice){
        cells[lastChoice.x][lastChoice.y]=9999;
        return lastChoice;
    }

    public int getWhoseTurn(int turn) {
        int playerForTheNextMove=0;
        switch (turn % 2) {
            case 0:
                playerForTheNextMove = 1;
                break;
            case 1:
                playerForTheNextMove = 2;
                break;
        }
        return playerForTheNextMove;
    }

    public static int[][] deepCopyIntMatrix(int[][] input) {
        if (input == null)
            return null;
        int[][] result = new int[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
}
