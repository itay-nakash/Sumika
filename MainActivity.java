package com.example.itay.sumika;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int maxNumOfSquare =7, minNumOfSquare =3;
    int validSpace=1, timeForMove=0;//the spacing is initialize between 1 to 3, the time limit to off
    boolean onePlayer=false;
    int compLevel=0;// 0 is without comp, 1 is easy, 2 is medium, 3 is hard
    ImageView gameImage;
    SquareImageView info;
    TextView namOfSquareTv;
    Button buttonVsComp, buttonVsFriend, buttonPractice, rightArrow, leftArrow, buttonTimer, buttonSpacing;
    int numOfSquareCounter = 3; //3=3on3, 4=4on4, 5=5on5, 6=6on6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
        changeFonts();
        initializeIntentListeners();
        initializeMenuListeners();
    }

    private void initializeVariables() {
        buttonVsComp = findViewById(R.id.buttonVsComp);
        buttonVsFriend = findViewById(R.id.buttonVsFriend);
        buttonPractice = findViewById(R.id.practice);
        buttonTimer= findViewById(R.id.buttonTimer);
        buttonSpacing=findViewById(R.id.buttonSpaceing);
        rightArrow = findViewById(R.id.rightArrowButton);
        leftArrow = findViewById(R.id.leftArrowButton);
        gameImage = findViewById(R.id.imageViewGame);
        info=findViewById(R.id.info);
        namOfSquareTv = findViewById(R.id.numOfSqure);
    }

    public void changeFonts(){
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/concertone-regular.ttf");
        namOfSquareTv.setTypeface(face);
        buttonVsComp.setTypeface(face);
        buttonVsFriend.setTypeface(face);
        buttonPractice.setTypeface(face);
    }

    private void initializeIntentListeners(){
        final Intent intentToGame = new Intent(this, GameActivity.class);

        buttonVsComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onePlayer=false;
                createDialog(intentToGame);
            }
        });
        buttonVsFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compLevel=0;
                onePlayer=false;
                putExtraForIntent(intentToGame);
                startActivity(intentToGame);
            }
        });

        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compLevel=0;
                onePlayer=true;
                putExtraForIntent(intentToGame);
                startActivity(intentToGame);
            }
        });

        final Intent infoIntent=new Intent(this,InfoActivity.class);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(infoIntent);
            }
        });

    }

    private void initializeMenuListeners(){


        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateNumOfSquare(false)){
                    updateGameSizeMenu();
                }
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateNumOfSquare(true)){
                    updateGameSizeMenu();
                }
            }
        });

        buttonSpacing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSpacing();
            }
        });

        buttonTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTimer();
            }
        });
    }

    public void putExtraForIntent(Intent intent){
        intent.putExtra("onePlayer", onePlayer);
        intent.putExtra("compLevel", compLevel);
        intent.putExtra("numOfSqure", numOfSquareCounter);
        intent.putExtra("timeLimit", timeForMove);
        intent.putExtra("spacingCounter", validSpace);
        intent.putExtra("validSpace", validSpace);
        intent.putExtra("timeForMove",timeForMove);
    }
    //returns true if did updated, false if not
    public boolean updateNumOfSquare(boolean clickRight) {
        boolean returnFlag = false;
        if (clickRight) {
            if (numOfSquareCounter < maxNumOfSquare) {
                numOfSquareCounter++;
                returnFlag = true;
            }
        } else {
            if (numOfSquareCounter > minNumOfSquare) {
                numOfSquareCounter--;
                returnFlag = true;
            }
        }
        return returnFlag;
    }

    public void updateGameSizeMenu() {
        namOfSquareTv.setText(numOfSquareCounter+" X "+numOfSquareCounter);
        switch (numOfSquareCounter) {
            case 3:
                gameImage.setImageResource(R.drawable.image3on3);
                break;
            case 4:
                gameImage.setImageResource(R.drawable.image4on4);
                break;
            case 5:
                gameImage.setImageResource(R.drawable.image5on5);
                break;
            case 6:
                gameImage.setImageResource(R.drawable.image6on6);
                break;
            case 7:
                gameImage.setImageResource(R.drawable.image7on7);
        }
    }

    public void updateSpacing() {
        switch (validSpace) {
            case 1:
                validSpace=2;
                buttonSpacing.setBackgroundResource(R.drawable.spacing2);
                break;
            case 2:
                validSpace=3;
                buttonSpacing.setBackgroundResource(R.drawable.spacing3);
                break;
            case 3:
                validSpace = 1;
                buttonSpacing.setBackgroundResource(R.drawable.spacing1);
                break;
        }
    }

    public void updateTimer(){
        switch (timeForMove){
            case 0:
                timeForMove=5;
                buttonTimer.setBackgroundResource(R.drawable.stopwatch_5);
                break;
            case 5:
                timeForMove=10;
                buttonTimer.setBackgroundResource(R.drawable.stopwatch_10);
                break;
            case 10:
                timeForMove=15;
                buttonTimer.setBackgroundResource(R.drawable.stopwatch_15);
                break;
            case 15:
                timeForMove=20;
                buttonTimer.setBackgroundResource(R.drawable.stopwatch_20);
                break;
            case 20:
                timeForMove=30;
                buttonTimer.setBackgroundResource(R.drawable.stopwatch_30);
                break;
            case 30:
                timeForMove=60;
                buttonTimer.setBackgroundResource(R.drawable.stopwatch_60);
                break;
            case 60:
                timeForMove=0;
                buttonTimer.setBackgroundResource(R.drawable.stopwatch_off);
                break;
        }

    }

    public void createDialog(Intent intentToGame){
        final Intent intent=intentToGame;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setPositiveButton(R.string.hard_level, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                compLevel=3;
                dialogInterface.dismiss();
                putExtraForIntent(intent);
                startActivity(intent);
            }
        });
        mBuilder.setNeutralButton(R.string.easy_level, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                compLevel=1;
                dialogInterface.dismiss();
                putExtraForIntent(intent);
                startActivity(intent);
            }
        });
        mBuilder.setNegativeButton(R.string.mediun_level, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                compLevel=2;
                dialogInterface.dismiss();
                putExtraForIntent(intent);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog=mBuilder.create();

        setDialogCustomTitle(alertDialog);
        alertDialog.show();
        setDialogButtonToMiddle(alertDialog);
    }
    public void setDialogCustomTitle(AlertDialog alertDialog){
        TextView title=new TextView(this);
        title.setText(R.string.titleDialog);
        title.setBackgroundColor(getResources().getColor(R.color.pinkSqure_uneven));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        alertDialog.setCustomTitle(title);
    }
    public void setDialogButtonToMiddle(AlertDialog alertDialog){
        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeutral = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnNeutral.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
    }
    public void changeButtonDialogColor(AlertDialog alertDialog,int color){
        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNeutral = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        btnPositive.setTextColor(color);
        btnNeutral.setTextColor(color);
        btnNegative.setTextColor(color);
    }
}
