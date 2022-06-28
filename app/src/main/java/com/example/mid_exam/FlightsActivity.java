package com.example.mid_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FlightsActivity extends AppCompatActivity {

    String price;
    String takeofftime;
    String returntime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        ImageView img1=(ImageView)findViewById(R.id.imgflight1);
        ImageView img2=(ImageView)findViewById(R.id.imgflight2);
        ImageView img3=(ImageView)findViewById(R.id.imgflight3);
        ImageView img4=(ImageView)findViewById(R.id.imgflight4);

        String getfrom = getIntent().getStringExtra("from");
       // String userlast = getIntent().getStringExtra("lastname");
    //    welcome.setText("Welcome"+" "+userfirst+" "+userlast);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price="456 $";
                String takeofftime="06:00";
                String returntime="17:55";
                Intent i=new Intent(FlightsActivity.this,FinalActivity.class);

                startActivity(i);

            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price="627 $";
                String takeofftime="17:00";
                String returntime="19:36";
                Intent i=new Intent(FlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price="756 $";
                String takeofftime="18:50";
                String returntime="19:36";
                Intent i=new Intent(FlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price="820 $";
                String takeofftime="18:50";
                String returntime="22:00";
                Intent i=new Intent(FlightsActivity.this,FinalActivity.class);
                startActivity(i);
            }
        });
    }
}