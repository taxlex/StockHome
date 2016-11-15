package com.aalpblgmail.stockhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button save;
    static ArrayList<String> groceryList = new ArrayList<String>();
    EditText item;
    static ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switcherButton();
        save = (Button) findViewById(R.id.addButton);
        item = (EditText) findViewById(R.id.inputText);
        list = (ListView) findViewById(R.id.groceryList);


        item.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus)
                    item.setHint("");
                else
                    item.setHint("Enter Grocery");
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String input = item.getText().toString();
                if(input.length() > 0 && input.trim() != null){
                    groceryList.add(input);


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, groceryList);
                    list.setAdapter(adapter);
                    ((EditText)findViewById(R.id.inputText)).setText("");
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id){
                groceryList.remove(position);
                list.invalidateViews();
                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int temppos = position;
                Main2Activity.stockList.add(groceryList.get(temppos));
                groceryList.remove(temppos);
                list.invalidateViews();
            }
        });
    }



    private void switcherButton() {
        Button switch1 = (Button) findViewById(R.id.toggle1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }
    public void save(){
        Context context = getApplicationContext();
        SharedPreferences shared = context.getSharedPreferences("com.aalpblgmail.stockhome.preferences", MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        int i = 0;
        int j = groceryList.size();
        int k = Main2Activity.stockList.size();
        char t = (char)i;
        String se = "";
        se += t;
        edit.putString("glength", se);
        char v = (char)i;
        String sp = "";
        sp += v;
        edit.putString("slength", sp);



        char temp = (char)i;
        String s1 = "";
        s1 += temp;
        for(String s: groceryList){
            edit.putString(s1,s);
            i++;
            temp = (char)i;
            s1 = "";
            s1 += temp;
        }
        i++;
        temp = (char)i;
        s1 = "";
        s1 += temp;
        for(String s: Main2Activity.stockList){
            edit.putString(s1,s);
            i++;
            temp = (char)i;
            s1 = "";
            s1 += temp;
        }
        edit.commit();
    }
    public void load(){
        Context context = getApplicationContext();
        SharedPreferences shared = context.getSharedPreferences("com.aalpblgmail.stockhome.preferences", MODE_PRIVATE);
        int i = 0;
        char temp = (char)i;
        String s1 = "";
        s1 += temp;
            for (i = 0; i < Integer.parseInt(shared.getString("glength", "Bad seed")); i++) {
                groceryList.add(shared.getString(s1, "Bad seed"));
                temp = (char) i;
                s1 = "";
                s1 += temp;

            }
            for (i = 0; i < Integer.parseInt(shared.getString("slength", "Bad seed")); i++) {
                Main2Activity.stockList.add(shared.getString(s1, "Bad seed"));
                temp = (char) i;
                s1 = "";
                s1 += temp;

            }

    }
    @Override
    protected void onPause(){
        save();
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        save();
        super.onDestroy();
    }
    @Override
    protected void onResume(){
        SharedPreferences prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", false  )) {
            Log.i("first","runpart");
            load();
        }
        else{
            prefs.edit().putBoolean("firstrun",false).commit();
        }
        list.invalidateViews();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, groceryList);
        list.setAdapter(adapter);
        super.onResume();
    }

}



