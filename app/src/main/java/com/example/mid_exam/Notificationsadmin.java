package com.example.mid_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notificationsadmin extends AppCompatActivity {

    EditText enterednot;

    FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private ListView listView;
    private ArrayList<String> list= new ArrayList<>();
    public int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationsadmin);

        enterednot= (EditText) findViewById(R.id.txt_sendnot);
        Button sendnot = (Button) findViewById(R.id.btn_sendnot);

        listView= (ListView) findViewById(R.id.listnotadmin);
        final ArrayAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notifications");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        sendnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sentnot=enterednot.getText().toString();
                if (sentnot.isEmpty()){
                    Toast.makeText(Notificationsadmin.this,"Please Enter A notice",Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase.getInstance().getReference("Notifications").push().setValue(sentnot);
                    Toast.makeText(Notificationsadmin.this,"Notification Sent Successfully",Toast.LENGTH_LONG).show();
                    enterednot.setText("");
                }
            }
        });

    }
}