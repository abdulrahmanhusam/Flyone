package com.example.mid_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {
    ListView countries_list;
   private ArrayAdapter adapter;
   public  String selectedfrom;
    String usernamefirst1;
    String usernamelast1 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EditText thefilter=(EditText)findViewById(R.id.txt_filter);

        usernamefirst1 = getIntent().getStringExtra("firstname");
        usernamelast1 = getIntent().getStringExtra("lastname");


        countries_list=(ListView)findViewById(R.id.countries_list);
        Resources res=this.getResources();
        String [] values=res.getStringArray(R.array.countries_array);
        Context context;
         adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1,values);
        countries_list.setAdapter(adapter);
        thefilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                (SearchActivity.this).adapter.getFilter().filter(s);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        countries_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedfrom=countries_list.getItemAtPosition(position).toString();
                Intent j=new Intent(SearchActivity.this,ReservationActivity.class);
                j.putExtra("firstname",usernamefirst1);
                j.putExtra("lastname",usernamelast1);
                j.putExtra("key1",selectedfrom);
                startActivity(j);



            }
        });

    }



}