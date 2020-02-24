package com.example.itay.sumika;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity{

    GameScreen gameScreen;
    GridView gridView;
    ImageView circleTurn;
    TextView timeTv, announceTv;
    int numOfHorizontal,numOfVertical, spacingCounter;
    boolean isOnePlayer;
    int compLevel,validSpace,timeForMove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeVariables();
        gameScreen= new GameScreen(numOfHorizontal,
                numOfVertical,
                gridView,
                circleTurn,
                timeTv,
                announceTv,
                getApplicationContext(),
                compLevel,
                isOnePlayer,
                validSpace,
                timeForMove);
    }

    public void initializeVariables(){
        circleTurn=findViewById(R.id.circleWhosTurnIsIt);
        gridView=findViewById(R.id.gridView);
        timeTv=findViewById(R.id.timeTv);
        announceTv=findViewById(R.id.announceTv);
        getExtras();
    }

    public void getExtras(){
        numOfHorizontal=getIntent().getExtras().getInt("numOfSqure");
        numOfVertical=getIntent().getExtras().getInt("numOfSqure");
        isOnePlayer=getIntent().getExtras().getBoolean("onePlayer");
        compLevel=getIntent().getExtras().getInt("compLevel");
        validSpace=getIntent().getExtras().getInt("validSpace");
        timeForMove=getIntent().getExtras().getInt("timeForMove");
        spacingCounter=getIntent().getExtras().getInt("spacingCounter");
    }
}
