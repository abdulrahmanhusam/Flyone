package com.example.mid_exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText loginemail;
    EditText loginpassword;
    String enteredemail, enteredpas;
    private FirebaseAuth mAuth;
    private ProgressBar progressBarlog;
    public int onlineusernum=0;

    public static final int GOOGLE_SIGN_IN_CODE = 0;
    SignInButton signIng;
    GoogleSignInOptions gso;
    private GoogleSignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tosignup = (TextView) findViewById(R.id.txt_tosignup);
        TextView forgetpass = (TextView) findViewById(R.id.txt_forgetpass);
        //ImageView facebooklog=(ImageView)findViewById(R.id.facebooklogin);
        // ImageView gmaillog=(ImageView)findViewById(R.id.googlelogin);
        Button login = (Button) findViewById(R.id.btn_login);
        progressBarlog = (ProgressBar) findViewById(R.id.progressBarlog);
        mAuth = FirebaseAuth.getInstance();
        loginemail = (EditText) findViewById(R.id.txt_emaillog);
        loginpassword = (EditText) findViewById(R.id.txt_passlog);
        String usernamefirst = getIntent().getStringExtra("firstname");
        String usernamelast = getIntent().getStringExtra("lastname");

        signIng = findViewById(R.id.bt_sign_in);
        tosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Forget Your Password?");
                builder.setMessage("Please Enter Your Email");
                EditText input = new EditText(LoginActivity.this);
                input.setHint("Email");
                builder.setView(input);
                builder.setPositiveButton("Send me reset instructions", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputvalue = input.getText().toString();
                        Toast.makeText(getBaseContext(), "Email Sent to" + " " + inputvalue, Toast.LENGTH_LONG).show();

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
        /*facebooklog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                startActivity(i);
            }
        });
        gmaillog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/"));
                startActivity(i);
            }
        });*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredemail = loginemail.getText().toString();
                String enteredpas = loginpassword.getText().toString();


                if (enteredemail.isEmpty()) {
                    loginemail.setError("Email Is Required!");
                    loginemail.requestFocus();
                    return;
                }
                if (enteredpas.isEmpty()) {
                    loginpassword.setError("Password Is Required!");
                    loginpassword.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(enteredemail).matches()) {
                    loginemail.setError("Please Enter A valid Email!");
                    loginemail.requestFocus();
                    return;
                }
                if (enteredpas.length() < 6) {
                    loginpassword.setError("Minimum password length must be 6 characters!");
                    loginpassword.requestFocus();
                    return;
                }
                progressBarlog.setVisibility(View.VISIBLE);


                mAuth.signInWithEmailAndPassword(enteredemail, enteredpas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                //redirect to user profile
                                progressBarlog.setVisibility(View.GONE);
                                //String nameshow = user.getEmail();
                                onlineusernum=onlineusernum+1;
                                String numm= String.valueOf(onlineusernum);
                                Intent i = new Intent(LoginActivity.this, ReservationActivity.class);
                                //i.putExtra("name0", nameshow);
                                i.putExtra("onlinenum", numm);
                                startActivity(i);
                            }


                            else {
                                user.sendEmailVerification();
                                progressBarlog.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Check your Email to verify your account", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            //admin login
                            DatabaseReference reff=FirebaseDatabase.getInstance().getReference("Admins");
                            reff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        String hh2=snapshot.child("email").getValue().toString();
                                        String hh=snapshot.child("password").getValue().toString();
                                        if (enteredemail.equalsIgnoreCase(hh2) && enteredpas.equals(hh)){
                                            progressBarlog.setVisibility(View.GONE);
                                            Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(i);
                                            break;
                                        }
                                        else{
                                            progressBarlog.setVisibility(View.GONE);
                                            Toast.makeText(LoginActivity.this,"Wrong Email Or Password", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressBarlog.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                    }
                });

               /*
                SharedPreferences sp=getSharedPreferences("user_info",MODE_PRIVATE);
                String savedemail=sp.getString("email",null);
                String savedpass=sp.getString("password",null);
                if(savedemail.equals(enteredemail) && savedpass.equals(enteredpas)) {
                    Intent i = new Intent(LoginActivity.this, ReservationActivity.class);
                    i.putExtra("firstname",usernamefirst);
                    i.putExtra("lastname",usernamelast);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getBaseContext(),"Incorrect Email Or Password",Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        //start sign in google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("501611323963-jfttl970bevis093hslv78f08irf5kfh.apps.googleusercontent.com")
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null || mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, ReservationActivity.class));

        }
        signIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = signInClient.getSignInIntent();
                startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAcc.getIdToken(), null);

                mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "Connected with Google Account Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ReservationActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }

}