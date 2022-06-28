package com.example.mid_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        TextView finalname=(TextView)findViewById(R.id.txt_finalname);
        TextView finalfrom=(TextView)findViewById(R.id.txt_finalfrom);
        TextView finalto=(TextView)findViewById(R.id.txt_finalto);
        TextView finaldepdate=(TextView)findViewById(R.id.txt_finaldepdate);
        TextView finalredate=(TextView)findViewById(R.id.txt_finalredate);
        TextView finalpassengers=(TextView)findViewById(R.id.txt_finalpassen);
        TextView finalcabin=(TextView)findViewById(R.id.txt_finalcabin);
        TextView finalpayment=(TextView)findViewById(R.id.txt_finalpayment);

        Button confirmation=(Button)findViewById(R.id.btn_confirmation);
        Button backtoreservation=(Button)findViewById(R.id.btn_backtoreser);

        SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
        String savedfirst=sp.getString("firstname",null);
        String savedlast=sp.getString("lastname",null);
        finalname.setText(savedfirst+" "+savedlast);

        SharedPreferences rv=getSharedPreferences("reservation_info",MODE_PRIVATE);
        String savedfrom=rv.getString("zfrom",null);
        finalfrom.setText(savedfrom);

        String savedto=rv.getString("zto",null);
        finalto.setText(savedto);

        String saveddepartdate=rv.getString("zdepdate",null);
        finaldepdate.setText(saveddepartdate);

        String savedreturndate=rv.getString("zreturndate",null);
        finalredate.setText(savedreturndate);

        String savedpassengers=rv.getString("zpassengers",null);
        finalpassengers.setText(savedpassengers);

        String savedcabin=rv.getString("zcabin",null);
        finalcabin.setText(savedcabin);

        String savedpayment=rv.getString("zpayment",null);
        finalpayment.setText(savedpayment);

        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"The Flight Has Been Confirmed",Toast.LENGTH_LONG).show();
            }
        });

        backtoreservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oo=new Intent(FinalActivity.this,ReservationActivity.class);
                oo.putExtra("firstname",savedfirst);
                oo.putExtra("lastname",savedlast);
                startActivity(oo);
            }
        });





    }
}