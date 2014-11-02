package com.example.fft;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fft.SpecificRecepie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class Favorite extends Activity {
	ListView listView;
	List<FileBaseRecipe> favoriteRecipes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		listView = (ListView) findViewById(R.id.favoriteList);
		
	/*	String[] values = new String[] 
		{ 
				"Receptas - 1", 
                "Receptas - 2",
                "Receptas - 3",
                "Receptas - 4", 
                "Receptas - 5", 
                "Receptas - 6", 
                "Receptas - 7", 
                "Receptas - 8",
                "Receptas - 9",
                "Receptas - 10",
                "Receptas - 11",
                "Receptas - 12",
                "Receptas - 13",
                "Receptas - 14",
                "Receptas - 15"
        };*/
		
		
		//get list of favourites recipes from shared preferences and json
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); 
		Gson gson = new Gson();
		String jsonStr = pref.getString("objectList", "");
		Type type = new TypeToken<List<FileBaseRecipe>>(){}.getType();
	
		favoriteRecipes = gson.fromJson(jsonStr, type);
		
		List<String> favoriteRecipesString = new ArrayList<String>();
		if (favoriteRecipes != null)
		for (FileBaseRecipe specific : favoriteRecipes){
			favoriteRecipesString.add(specific.getName());
		}
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, favoriteRecipesString);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			 
             @Override
             public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
              
              // ListView Clicked item value
              String itemValue = (String) listView.getItemAtPosition(position);
                 
               // Show Alert 
              Intent intent = new Intent(Favorite.this, SpecificRecepie.class);
              intent.putExtra("object", favoriteRecipes.get(position));
              startActivity(intent);            
             }

        }); 
	}


}
