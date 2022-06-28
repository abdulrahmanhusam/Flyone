package com.example.mid_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class OnewayFlightsActivity extends AppCompatActivity {
    String price2;
    String takeofftime2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneway_flights);
        ImageView imgone=(ImageView)findViewById(R.id.imgoneflight1);
        ImageView imgtwo=(ImageView)findViewById(R.id.imgoneflight2);
        ImageView imgthree=(ImageView)findViewById(R.id.imgoneflight3);
        ImageView imgfour=(ImageView)findViewById(R.id.imgoneflight4);
        ImageView imgfive=(ImageView)findViewById(R.id.imgoneflight5);

        imgone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price2="405 $";
                String takeofftime2="03:10";
                Intent i=new Intent(OnewayFlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });
        imgtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price2="483 $";
                String takeofftime2="18:30";

                Intent i=new Intent(OnewayFlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });
        imgthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price2="680 $";
                String takeofftime2="01:50";
                Intent i=new Intent(OnewayFlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });
        imgfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price2="718 $";
                String takeofftime2="23:00";
                Intent i=new Intent(OnewayFlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });
        imgfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price2="794 $";
                String takeofftime2="00:50";
                Intent i=new Intent(OnewayFlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });
    }
}