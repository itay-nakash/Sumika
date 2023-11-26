package com.example.itay.sumika;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class InfoActivity extends AppCompatActivity {

    ImageView rightArrow,leftArrow,instructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initializeVaribales();
        initializeLiseners();
    }
    public void initializeVaribales(){
        this.rightArrow=findViewById(R.id.right_arrow);
        this.leftArrow=findViewById(R.id.left_arrow);
        this.instructions=findViewById(R.id.imageView_instructions);
    }
    public void initializeLiseners(){
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructions.setImageResource(R.drawable.menu_instruction);
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructions.setImageResource(R.drawable.game_instruction);
            }
        });
    }

}
