package com.example.itay.sumika;

import android.graphics.Point;
import android.os.CountDownTimer;
import android.widget.Button;

import java.util.ArrayList;


public class GameManager {
    GameLogic gameLogic;
    CountDownTimer timer;
    int validSpace, timeForMove;
    boolean getUserClicks = true, onePlayer;
    int compLevel;
    String colorP1, colorP2;
    GameScreen gameScreen;
    Player player1, player2, lastPlayerPlayed;
    final int numOfplayers = 2;
    Point lastChoice;
    CompAI comp;
    int cells[][];
    int numOfClick = 0;// should it be static?
    private int turn = 0;//1=player 1, 2=player 2

    public GameManager() {
    }


    GameManager(String colorP1, String colorP2, int[][] cells, GameScreen gameScreen, boolean onePlayer, int compLevel, int validSpace, int timeForMove) {
        this.colorP1 = colorP1;
        this.colorP2 = colorP2;
        this.player1 = new Player(1, getImageId(colorP1));
        this.player2 = new Player(2, getImageId(colorP2));
        lastChoice = new Point(-100, -100);//initialize unrelevant value- to let the user choose first square freely.
        this.cells = cells;
        this.compLevel = compLevel;
        this.validSpace = validSpace;
        this.timeForMove = timeForMove;
        this.onePlayer = onePlayer;
        this.gameScreen = gameScreen;
        this.comp = new CompAI(cells, player2);
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

    public void game(Point p) {
        switch (turn % numOfplayers) {
            case 0: { //first player turn
                if (!checkEndOfTheGame(cells,lastChoice,validSpace)) {
                    if (isValid(cells,p,lastChoice,validSpace)) {
                        pauseGame(250);
                        if(!(numOfClick==0&&checkIfWiningMove(p,cells,validSpace))) {//make sure the user/comp wont win on first move !
                                addChoice(p);
                                drawTheMove(p,numOfClick,getLastPlayerPlay());
                                changeTurn();
                                if (checkEndOfTheGame(cells,lastChoice,validSpace)) {
                                    announceWinner();
                                }
                        }
                    }
                    else{ //TODO check if worked
                        if(!onePlayer)
                            changeTurn();
                    }
                } else {
                    announceWinner();
                }
                break;
            }
            case 1: {//when player 2 or the comp playing
                if (isValid(cells,p,lastChoice,validSpace)) {
                    pauseGame(250);
                    addChoice(p);
                    drawTheMove(p,numOfClick,getLastPlayerPlay());
                    changeTurn();
                }
                else{
                    if(!onePlayer)
                        changeTurn();
                }
                if (checkEndOfTheGame(cells,lastChoice,validSpace)) {
                    announceWinner();
                }
                break;
            }
        }
    }
    public void pauseGame(int timeInMillis){
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void changeTurn() {
        if (!onePlayer) {
            turn++;
            gameScreen.changeImageTurn(turn);
            timerCounter();
            if (turn % numOfplayers == 1 && compLevel != 0) {
                getUserClicks = false;
                comp.test2(this, player2, compLevel, cells, lastChoice, validSpace);
            } else
                getUserClicks = true;
        }
        else {//TODO check if worked onr player
            timerCounter();
        }
    }

    public void addChoice(Point p) {//the function that starts the move
        numOfClick++;
        cells[p.x][p.y]=numOfClick;
        lastChoice = p;
    }

    public Player getLastPlayerPlay() {
        switch (turn % 2) {
            case 0:
                lastPlayerPlayed = player2;
                break;
            case 1:
                lastPlayerPlayed = player1;
                break;
        }
        return lastPlayerPlayed;
    }

    public void announceWinner() {
        int winNum;
        int resForAnnounce1,resForAnnounce2;
        if (!onePlayer) // if its one player- the turn need to stay on pink
        gameScreen.changeImageTurn(turn+1);
        if(timer!=null)
        timer.cancel();
        if (!onePlayer) {
            winNum=getLastPlayerPlay().getNumOfPlayer();
            resForAnnounce1=R.string.two_plater_announce1;
            resForAnnounce2=R.string.two_plater_announce2;
            gameScreen.setAnnounceTv2Players(resForAnnounce1,resForAnnounce2,winNum);
        }
        else{
            gameScreen.setAnnounceTv1Player(R.string.single_player_announce,numOfClick);
        }
    }

    private int getImageId(String colorP) {
        int imageId = -1;
        switch (colorP) {
            case "blue":
                imageId = R.drawable.btnshape_game_blue;
                break;
            case "pink":
                imageId = R.drawable.btnshape_game_pink;
                break;
        }
        return imageId;
    }

    public int getNumOfClick() {
        return numOfClick;
    }

    public void timerCounter() {
        if(timer!=null)
        timer.cancel();
        if (timeForMove != 0) {
            timer = new CountDownTimer(timeForMove*1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    long secondsUntilFinished=millisUntilFinished/1000 + 1;
                    gameScreen.setTimeTv((int)secondsUntilFinished);
                }

                public void onFinish() {
                    getUserClicks = false;
                    gameScreen.setTimeTv(0);
                    announceWinner();
                }
            }.start();
        }
    }


    /** using gameLogic **/
    public boolean checkEndOfTheGame(int cells[][],Point lastChoice, int validSpace){
        return gameLogic.checkEndOfGame(cells,lastChoice,validSpace);
    }

    public boolean isValid(int cells[][],Point p,Point lastChoice, int validSpace){
        return gameLogic.isValid(cells,p,lastChoice,validSpace);
    }

    public boolean checkIfWiningMove(Point p,int cells[][], int validSpace){
        int[][] dpOfCellsInInt=deepCopyIntMatrix(cells);
        return gameLogic.checkIfWiningMove(p,dpOfCellsInInt,validSpace);
    }
    /** using gameLogic **/

    /** using board **/
    public void drawTheMove(Point p, int numOfClick, Player lastPlayerPlayed) {
        gameScreen.board.drawTheMove(p,numOfClick,lastPlayerPlayed);
    }
    /** using board **/

    public int addTurn() {
        this.turn++;
        return turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setGetUserClicks(boolean getUserClicks) {
        this.getUserClicks = getUserClicks;
    }

    public void setOnePlayer(boolean onePlayer) {
        this.onePlayer = onePlayer;
    }

    public void setColorP1(String colorP1) {
        this.colorP1 = colorP1;
    }

    public void setColorP2(String colorP2) {
        this.colorP2 = colorP2;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setLastChoice(Point lastChoice) {
        this.lastChoice = lastChoice;
    }

    public void setComp(CompAI comp) {
        this.comp = comp;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public void setNumOfClick(int numOfClick) {
        this.numOfClick = numOfClick;
    }

    public void setCompLevel(int compLevel) {
        this.compLevel = compLevel;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }

    public void setValidSpace(int validSpace) {
        this.validSpace = validSpace;
    }

    public void setTimeForMove(int timeForMove) {
        this.timeForMove = timeForMove;
    }

    public GameManager getDeepCopy() {
        GameManager deepCopygm = new GameManager();
        deepCopygm.setCells(cells);
        deepCopygm.setColorP1(colorP1);
        deepCopygm.setColorP2(colorP2);
        deepCopygm.setComp(comp);
        deepCopygm.setGameScreen(gameScreen);
        deepCopygm.setGetUserClicks(getUserClicks);
        deepCopygm.setCompLevel(compLevel);
        deepCopygm.setLastChoice(lastChoice);
        deepCopygm.setNumOfClick(numOfClick);
        deepCopygm.setOnePlayer(onePlayer);
        deepCopygm.setPlayer1(player1);
        deepCopygm.setPlayer2(player2);
        deepCopygm.setTurn(turn);
        deepCopygm.setValidSpace(validSpace);
        deepCopygm.setTimeForMove(timeForMove);
        deepCopygm.setTimer(timer);
        return deepCopygm;
    }

}