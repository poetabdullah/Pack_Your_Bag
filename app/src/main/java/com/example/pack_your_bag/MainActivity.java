package com.example.pack_your_bag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.pack_your_bag.Adapter.Adapter;
import com.example.pack_your_bag.Constants.MyConstants;
import com.example.pack_your_bag.Data.AppData;
import com.example.pack_your_bag.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //To hide the toolbar:
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerView);

        addAddTitles();
        addAllImages();
        persistAppData();

        database = RoomDB.getInstance(this);
        System.out.println("---------------->" + database.mainDao().
                getAllSelected(false).get(0).getItemname());

        adapter = new Adapter(this, titles, images, MainActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private static final int TIME_INTERVAL = 2000;

    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if(mBackPressed+TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(this, "Tap back button in order to exit.", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    public void persistAppData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        database = RoomDB.getInstance(this);
        AppData appData = new AppData(database);
        int last = prefs.getInt(AppData.LAST_VERSION, 0);
        if(!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, false)){
            appData.persistAllData();
            editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, true);
            editor.commit();
        }
        else if(last < AppData.NEW_VERSION){
            database.mainDao().deleteAllSystemItems(MyConstants.SYSTEM_SMALL);
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION);
            editor.commit();
        }
    }
    private void addAddTitles(){
        titles = new ArrayList<>();
        titles.add(MyConstants.BASIC_NEEDS_CAMEL_CASE); //1
        titles.add(MyConstants.CLOTHING_CAMEL_CASE); //2
        titles.add(MyConstants.PERSONAL_CARE_CAMEL_CASE); //3
        titles.add(MyConstants.BABY_NEEDS_CAMEL_CASE); //4
        titles.add(MyConstants.HEALTH_CAMEL_CASE); //5
        titles.add(MyConstants.TECHNOLOGY_CAMEL_CASE); //6
        titles.add(MyConstants.FOOD_CAMEL_CASE); //7
        titles.add(MyConstants.BEACH_SUPPLIES_CAMEL_CASE); //8
        titles.add(MyConstants.CAR_SUPPLIES_CAMEL_CASE); //9
        titles.add(MyConstants.NEEDS_CAMEL_CASE); //10
        titles.add(MyConstants.MY_LIST_CAMEL_CASE); //11
        titles.add(MyConstants.MY_SELECTIONS_CAMEL_CASE); //12
        titles.add(MyConstants.ANOTHER_LIST); //13 -- added one
        titles.add(MyConstants.ANOTHER_LIST2); // 14 -- added one
    }

    private void addAllImages(){
        images = new ArrayList<>();
        images.add(R.drawable.p1); //1
        images.add(R.drawable.p2); //2
        images.add(R.drawable.p3); //3
        images.add(R.drawable.p4); //4
        images.add(R.drawable.p5); //5
        images.add(R.drawable.p6); //6
        images.add(R.drawable.p7); //7
        images.add(R.drawable.p8); //8
        images.add(R.drawable.p9); //9
        images.add(R.drawable.p10); //10
        images.add(R.drawable.p11); //11
        images.add(R.drawable.p12); //12
    }
}