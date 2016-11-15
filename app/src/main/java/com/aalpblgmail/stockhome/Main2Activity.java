package com.aalpblgmail.stockhome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    static ArrayList<String> stockList = new ArrayList<String>();
    static ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switcherButton();
        list = (ListView) findViewById(R.id.stockList);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, stockList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int temppos = position;
                MainActivity.groceryList.add(stockList.get(temppos));
                stockList.remove(temppos);
                list.invalidateViews();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                        int position, long id){
                stockList.remove(position);
                list.invalidateViews();
                return true;
            }
        });
    }

    private void switcherButton() {
        Button switch1 = (Button) findViewById(R.id.toggle2);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.list.invalidateViews();
                finish();
            }
        });
    }

}
