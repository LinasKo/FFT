package com.example.fft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//getActionBar().hide();
	}

	public void findRecipe(View v){
		Intent i = new Intent(MainActivity.this, SearchActivity.class);
		startActivity(i);
	}
	public void showRecipes(View v){
		Intent i = new Intent(MainActivity.this, AllRecipes.class);
		startActivity(i);
	}
	public void showForum(View v){
		/*Intent i = new Intent(MainActivity.this, Favorite.class);
		startActivity(i);*/
		Toast.makeText(this, "Forum feature comming soon!", Toast.LENGTH_SHORT).show();
	}
	public void favorites(View v){
		Intent i = new Intent(MainActivity.this, Favorite.class);
		startActivity(i);
	}
}
