package com.example.mid_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    TextView users;
    TextView bookings;
    TextView notification;
    Button signo;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        users=(TextView)findViewById(R.id.textusers);
        bookings=(TextView)findViewById(R.id.textbookings);
        notification=(TextView)findViewById(R.id.textnotification);
        signo= (Button)findViewById(R.id.buttonsigno);
        mAuth= FirebaseAuth.getInstance();
        signo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i=new Intent(AdminActivity.this,LoginActivity.class);
                // i.putExtra("firstname",userfirst);
                //i.putExtra("lastname",userlast);
                startActivity(i);

            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminActivity.this,Usersadmin.class);
                startActivity(i);
            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminActivity.this,Usersadmin.class);
                startActivity(i);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminActivity.this,Notificationsadmin.class);
                startActivity(i);
            }
        });

    }
}