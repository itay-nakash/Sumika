package com.example.itay.sumika;

/**
 * Created by itay on 18/11/17.
 */

public class Player {
    boolean isTurn;
    int imageId;
    int numOfPlayer;//if 1= uneven if 2=even, maybe in future 3 player

    public Player(Player player){
        this.isTurn=player.isTurn;
        this.imageId=player.imageId;
        this.numOfPlayer=player.numOfPlayer;
    }
    public Player(int numOfPlayer, int imageId){
        if(numOfPlayer==1)
            this.isTurn=true;
        else
            this.isTurn=false;
        this.numOfPlayer =numOfPlayer;
        this.imageId=imageId;
    }
    public int getImageId(){
        return imageId;
    }

    public int getNumOfPlayer() {
        return numOfPlayer;
    }
}
