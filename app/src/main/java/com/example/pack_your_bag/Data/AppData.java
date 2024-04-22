package com.example.pack_your_bag.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.pack_your_bag.Constants.MyConstants;
import com.example.pack_your_bag.Database.RoomDB;
import com.example.pack_your_bag.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {
    RoomDB database;
    String category;
    Context context;
    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData(){
        category = "BasicNeeds";
        List<Items> basicItems = new ArrayList<>();
        basicItems.add(new Items("Foreign Visa", category, false));
        basicItems.add(new Items("Passport", category, false));
        basicItems.add(new Items("Umbrella", category, false));
        basicItems.add(new Items("Driving License", category, false));
        basicItems.add(new Items("Foreign Currency", category, false));
        basicItems.add(new Items("Apartment Keys", category, false));
        basicItems.add(new Items("Classic Novels", category, false));
        basicItems.add(new Items("Travel Pillow", category, false));
        return basicItems;
    }

    public List<Items> getTechnologyData(){
        String[] data = {"Earphones",  "Kindle", "Laptop", "SD Card", "Camera", "Smartphone",
                "Foreign SIM", "Mobile Charger", "Laptop Charger"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data);
    }

    public List<Items> getHealthData(){
        String[] data = {"Paracetamol",  "Stomach ache drug", "Throat ache drug", "Flu drug",
                "First Aid Kit", "Pain relief drugs"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE, data);
    }

    public List<Items> getBeachSuppliesData(){
        String[] data = {"Slippers",  "Sunscreen", " Beach Umbrella", "Beach mat", "Swim suit",
                "Beach Volleyball"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data);
    }
    public List<Items> getCarSuppliesData(){
        String[] data = {"Jack",  "Puncture kit", "Battery terminals", "Spare car key", "Car papers"
        , "Car cushions", "Window shades", "Car charger"};
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> getNeedsData(){
        String[] data = {"Backpack",  "Laundry bag", "Magazines", "Important Numbers", "Sewing Kit",
        "Cricket Equipment", "Luggage", "Hand-carry"};
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE, data);
    }

    public List<Items> getBabyNeedsData(){
        String[] data = {"Diapers",  "Wipes", "Milk", "Feeder", "Baby clothes", "Spare clothes",
        "Baby Toys", "Param", "Shoes", "Socks", "Shampoo", "Soap", "Rash cream", "Baby lotion"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }
    public List<Items> getClothingData(){
        String[] data = {"T-Shirts",  "Undershirts", "Underwear", "Polo Shirts", "Jeans", "Shorts",
                "Casual Shirts", "Beach Shirts", "Suits", "Tie", "Belts", "Sneakers", "Oxford Shoes",
                "Soaks", "Jackets", "Hat", "P-Caps", "Gym clothes", "Raincoats", "Gloves"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE, data);
    }

    public List<Items> getFoodData(){
        String[] data = {"Snacks",  "Fresh Juices", "Sandwiches", "Coffee", "Tea", "Water", "Thermos"};
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
    }
    public List<Items> getPersonalCareData(){
        String[] data = {"Toothbrush",  "Toothpaste", "Floss", "Shaving kit", "Mouthwash"};
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
    }
    /*
    // extra data 1:
    public List<Items> getAnotherList1(){
        String[] data = {"Something", "Everything", "Nothing", "Someone", "Anyone"};
        return prepareItemsList(MyConstants.ANOTHER_LIST, data);
    }

    // extra data 2:
    public List<Items> getAnotherList2(){
        String[] data = {"Once", "upon", "a", "time", "there", "was", "an", "ice", "age"};
        return prepareItemsList(MyConstants.ANOTHER_LIST2, data);
    }*/

    // To output array into list:
    public List<Items> prepareItemsList(String category, String []data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear(); //Clearing the existing data
        for(int i = 0; i<list.size(); i++){
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
    }


    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getNeedsData());
        //listOfAllItems.add(getAnotherList1()); //added 1
        //listOfAllItems.add(getAnotherList2()); //added 2
        return listOfAllItems;
    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for(List<Items> list: listOfAllItems){
            for(Items items:list){
                database.mainDao().saveItem(items);
            }
        }
        System.out.println("Data added.");
    }

    public void persistDataByCategory(String category, Boolean onlyDelete){
        try{
            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);
            if(!onlyDelete){
                for(Items item:list){
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(context, "Reset Successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "Reset Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
           ex.printStackTrace();
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
    private List<Items> deleteAndGetListByCategory(String category, Boolean onlyDelete){
        if(onlyDelete){
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        }
        else{
            database.mainDao().deleteAllByCategory(category);
        }

        switch(category){
            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();

            case MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();

            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();

            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();

            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();

            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();

            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliesData();

            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliesData();

            case MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();
/*
            case MyConstants.ANOTHER_LIST:
                return getAnotherList1(); // added 1

            case MyConstants.ANOTHER_LIST2:
                return getAnotherList2(); // added 2
*/
            default:
                return new ArrayList<>();
        }
    }
}
