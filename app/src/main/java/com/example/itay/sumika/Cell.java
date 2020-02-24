package com.example.itay.sumika;

import android.widget.Button;

/**
 * Created by itay on 3/11/17.
 */


public class Cell {
    private int x;
    private int y;
    private Button button;
    private int numOfClick=-1;// deafult value for invalid button that showed

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    void setY(int y) {
        this.y = y;
    }

    public int getPosition(){
        int posiotion=y*5+x-1;
        return  posiotion;
    }

    public void setNumOfClick(int numOfClick) {
        this.numOfClick = numOfClick;
    }

    public Button getButton() {
        return button;
    }

    public int getNumOfClick() {
        return numOfClick;
    }

    public boolean isEmpty(){
        boolean returnValue;
        if(numOfClick==-1)
            returnValue=true;
        else
            returnValue=false;
        return returnValue;
    }

    void setButton(Button btn){
        this.button=btn;

    }
}
