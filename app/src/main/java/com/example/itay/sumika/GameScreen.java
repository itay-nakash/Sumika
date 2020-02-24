package com.example.itay.sumika;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by itay on 10/12/17.
 */

/**
 * class that takes care for the screen. ImageViews, boards and more.
 **/
public class GameScreen {

    int imageTurnId;
    Context context;
    ImageView turnImage;
    TextView timeTv, announceTv;
    Board board;

    public GameScreen(int numOfColumnsX,
                      int numOfRowsY,
                      GridView gridView,
                      ImageView turnImage,
                      TextView timetv,
                      TextView announceTv,
                      Context context,
                      int compLevel,
                      boolean onePlayer,
                      int validSpace,
                      int timeForMove) {
        this.turnImage = turnImage;
        this.timeTv = timetv;
        this.announceTv= announceTv;
        this.context=context;
        this.board = new Board(numOfColumnsX,
                numOfRowsY,
                gridView,
                context,
                compLevel,
                onePlayer,
                this,
                validSpace,
                timeForMove
        );
        if(timeForMove!=0)
            timetv.setText(Integer.toString(timeForMove));
        board.createBoard();
    }

    public void changeImageTurn(int turn) {
        switch (turn % 2) {
            case 0:
                imageTurnId = R.drawable.cirecle_pink_new;
                break;
            case 1:
                imageTurnId = R.drawable.cirecle_blue_new;

                break;
        }
        turnImage.setImageResource(imageTurnId);
    }

    public void setTimeTv(int timeLeft){
            timeTv.setText(Integer.toString(timeLeft));
    }

    public void setAnnounceTv2Players(int res,int res2,int winNum){
        String announce=context.getString(res)+" "+Integer.toString(winNum)+" "+context.getString(res2);
        announceTv.setText(announce);
    }

    public void setAnnounceTv1Player(int res,int numOfClick){
        String announce=context.getString(res)+" "+Integer.toString(numOfClick)+"!";
        announceTv.setText(announce);
    }


}
