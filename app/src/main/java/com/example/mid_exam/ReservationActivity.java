package com.example.mid_exam;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ReservationActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText departdate;
    EditText returndate;
    TextView from;
    Spinner spinnerto;
    TextView pas;
    TextView cabin;
    TextView payment;
    Button searchflights;
    ProgressBar sprogressBar;
    int counter=0;
    TextView welcome;
    SharedPreferences rv;
    String userfirst;
    String userlast;
    String userID;

    FirebaseAuth mAuth;
    private DatabaseReference myRef;
    GoogleSignInClient googleSignInClient;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Resources res=this.getResources();
        RadioGroup rg=(RadioGroup)findViewById(R.id.rg);
        //ImageView imgaccount=(ImageView)findViewById(R.id.imgaccount);
      //  RadioButton rb=(RadioButton)findViewById(rg.getCheckedRadioButtonId());

       departdate=(EditText)findViewById(R.id.departdate);
       returndate=(EditText)findViewById(R.id.returningdate);
       from=(TextView) findViewById(R.id.txt_from);
       pas=(TextView) findViewById(R.id.txt_pas);
       cabin=(TextView) findViewById(R.id.txt_cabin);
       payment=(TextView) findViewById(R.id.txt_payment);
       searchflights=(Button)findViewById(R.id.btn_searchflights);
       sprogressBar=(ProgressBar)findViewById(R.id.progressBar);

       welcome=(TextView)findViewById(R.id.txt_welcome);
      /*   userfirst = getIntent().getStringExtra("firstname");
         userlast = getIntent().getStringExtra("lastname");
        welcome.setText("Welcome"+" "+userfirst+" "+userlast);*/

        // Initialize firebase auth for google sign in and for get user data
        mAuth=FirebaseAuth.getInstance();


        // Initialize firebase user
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        userID=firebaseUser.getUid();

        //get user f name last name
        myRef= FirebaseDatabase.getInstance().getReference("Users");


        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile= snapshot.getValue(User.class);
                if (userprofile !=null){
                    String aaemail=userprofile.email;
                    String aafname=userprofile.firstname;
                    String aalname=userprofile.lastname;

                    welcome.setText("Welcome, "+aafname+" "+aalname);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReservationActivity.this,"Something wrong happend!",Toast.LENGTH_LONG).show();

            }
        });

         String unamegoogle=mAuth.getCurrentUser().getDisplayName();
         welcome.setText(unamegoogle);
        /*userlast = getIntent().getStringExtra("name0");
        welcome.setText(userlast);*/

         //imgaccount.setImageURI(mAuth.getCurrentUser().getPhotoUrl());

        // Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(ReservationActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        //bottom navigation bar code
        bottomNavigationView= findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.news:
                        startActivity(new Intent(getApplicationContext(),News.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(),Notifications.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });


       searchflights.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String finalselectedfrom=from.getText().toString();
               String finalto=spinnerto.getSelectedItem().toString();
               String finaldeptdate=departdate.getText().toString();
               String finalreturndate=returndate.getText().toString();
               String finalpassengers=pas.getText().toString();
               String finalcabin=cabin.getText().toString();
               String finalpayment=payment.getText().toString();
               rv=getSharedPreferences("reservation_info",MODE_PRIVATE);
               SharedPreferences.Editor editor=rv.edit();
               editor.putString("zfrom",finalselectedfrom);
               editor.putString("zto",finalto);
               editor.putString("zdepdate",finaldeptdate);
               editor.putString("zreturndate",finalreturndate);
               editor.putString("zpassengers",finalpassengers);
               editor.putString("zcabin",finalcabin);
               editor.putString("zpayment",finalpayment);
               editor.commit();
              Reservationinfo reservationinfo=new Reservationinfo(finalselectedfrom,finalto,finaldeptdate,finalreturndate,finalpassengers,finalcabin,finalpayment);

               FirebaseDatabase.getInstance().getReference("Reservations").child(userID).setValue(reservationinfo);




               sprogressBar.setVisibility(View.VISIBLE);
               Timer t=new Timer();
               TimerTask tt=new TimerTask() {
                   @Override
                   public void run() {

                       counter++;
                       sprogressBar.setProgress(counter);
                       if (counter==100)
                   //       t.cancel();
                       switch (rg.getCheckedRadioButtonId()) {
                           case (R.id.rd1): {

                               Intent i = new Intent(ReservationActivity.this, FlightsActivity.class);
                               startActivity(i);
                           }
                           break;

                           case (R.id.rd2): {
                               Intent j = new Intent(ReservationActivity.this, OnewayFlightsActivity.class);
                               startActivity(j);

                               break;
                           }
                       }
                   }
               };
               t.schedule(tt,0,50);




           }
       });

       payment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu popup=new PopupMenu(getBaseContext(), v);
               popup.setOnMenuItemClickListener(ReservationActivity.this::onMenuItemSelected);
               MenuInflater inflater=popup.getMenuInflater();
               inflater.inflate(R.menu.paymentmenu,popup.getMenu());
               popup.show();
           }
       });


       cabin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu popup=new PopupMenu(getBaseContext(), v);
               popup.setOnMenuItemClickListener(ReservationActivity.this::onMenuItemSelected);
               MenuInflater inflater=popup.getMenuInflater();
               inflater.inflate(R.menu.cabincalssmenu,popup.getMenu());
               popup.show();

           }
       });

       pas.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               AlertDialog.Builder builder= new AlertDialog.Builder(ReservationActivity.this);

               LayoutInflater inflater;
               final View passengerslayout=getLayoutInflater().inflate(R.layout.passenger_layout,null);
               builder.setTitle("Passengers");

               builder.setView(passengerslayout);

               builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       EditText adult=passengerslayout.findViewById(R.id.txt_adult);
                       String adults=adult.getText().toString();
                       EditText child=passengerslayout.findViewById(R.id.txt_child);
                       String children=child.getText().toString();
                       EditText infant=passengerslayout.findViewById(R.id.txt_infant);
                       String infants=infant.getText().toString();
                       pas.setText(adults+" "+"Adults"+",  "+children+" "+"Children"+",  "+infants+" "+"Infant");

                   }
               });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       dialog.cancel();
                   }
               });
               builder.create().show();


           }
       });




       spinnerto=(Spinner)findViewById(R.id.spinnerto);

        List<String> countrylist = Arrays.asList(res.getStringArray(R.array.countries_array));
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,countrylist);
        spinnerto.setAdapter(adapter);

        String selectedfrom = getIntent().getStringExtra("key1");


       from.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(ReservationActivity.this,SearchActivity.class);
               i.putExtra("firstname",userfirst);
               i.putExtra("lastname",userlast);
               startActivity(i);

           }
       });
      from.setText(selectedfrom);





        departdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR,year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updateLabel();

                    }
                };
                new DatePickerDialog(ReservationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        returndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                        myCalendar.set(Calendar.YEAR,year1);
                        myCalendar.set(Calendar.MONTH, month1);
                        myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth1);
                        updateLabel2();

                    }
                };
                new DatePickerDialog(ReservationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rg.getCheckedRadioButtonId()) {
                    case (R.id.rd1):
                     returndate.setVisibility(View.VISIBLE);

                        break;

                    case (R.id.rd2):

                        returndate.setVisibility(View.INVISIBLE);
                        break;
            }
            }
        });
        /*imgaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup=new PopupMenu(getBaseContext(), v);
                popup.setOnMenuItemClickListener(ReservationActivity.this::onMenuItemSelected);
                MenuInflater inflater=popup.getMenuInflater();
                inflater.inflate(R.menu.accountmenu,popup.getMenu());
                popup.show();
            }
        });*/


    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        departdate.setText(sdf.format(myCalendar.getTime()));
       // returndate.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


         returndate.setText(sdf.format(myCalendar.getTime()));

    }


    private boolean onMenuItemSelected(MenuItem menuItem) {

        if(menuItem.getItemId()==R.id.itemeconomy){
            cabin.setText("Economy");
        }
        else if(menuItem.getItemId()==R.id.itempremium){
            cabin.setText("Premium Economy");
        }
        else if(menuItem.getItemId()==R.id.itembusiness){
            cabin.setText("Business Class");
        }
        else if(menuItem.getItemId()==R.id.itemfirst){
            cabin.setText("First Class");
        }
        /*else if(menuItem.getItemId()==R.id.itemsout){
            Intent i=new Intent(ReservationActivity.this,LoginActivity.class);
           // i.putExtra("firstname",userfirst);
            //i.putExtra("lastname",userlast);
            startActivity(i);

            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Check condition
                    if (task.isSuccessful()) {
                        // When task is successful
                        // Sign out from firebase
                        mAuth.signOut();

                        // Display Toast
                        Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                        // Finish activity
                        finish();
                    }
                }
            });


        }*/
        else if(menuItem.getItemId()==R.id.itemmaster){
            payment.setText("MasterCard Credit");
        }
        else if(menuItem.getItemId()==R.id.itemvisa){
            payment.setText("Visa Credit");
        }
        else if(menuItem.getItemId()==R.id.itempaypal){
            payment.setText("PayPal");
        }
        else if(menuItem.getItemId()==R.id.itemamerican){
            payment.setText("American Express");
        }
        else if(menuItem.getItemId()==R.id.itembank){
            payment.setText("Bank Transfer");
        }
        return false;
    }



}