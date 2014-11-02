package com.example.fft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllRecipes extends Activity {
	ArrayList<FileBaseRecipe> all = new ArrayList<FileBaseRecipe>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_recipes);
		
		AccessDatabase access = new AccessDatabase();
		AccessDatabase2 access2 = new AccessDatabase2();
		
		
		all = access.recipes;
		all.addAll(access2.recipes);
		
		Collections.sort(all, new Comparator<FileBaseRecipe>() {

			@Override
			public int compare(FileBaseRecipe lhs, FileBaseRecipe rhs) {
				return lhs.getName().compareTo(rhs.getName());
			}
		});

		ArrayAdapter<FileBaseRecipe> adapter = new ArrayAdapter<FileBaseRecipe>(this, android.R.layout.simple_list_item_1, all);
		
		ListView list = (ListView)findViewById(android.R.id.list);
		
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				Intent intent = new Intent(AllRecipes.this, SpecificRecepie.class);
				intent.putExtra("object", all.get(pos));
				startActivity(intent);
			}
		});
		
		
	}

}
