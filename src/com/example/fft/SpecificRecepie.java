package com.example.fft;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.color;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SpecificRecepie extends Activity {

	Button bFav;
	FileBaseRecipe meal;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		int buttonWidth = width / 2;
		bFav = new Button(this);
		bFav.setWidth(width / 4);
		bFav.setHeight(width / 4);
		meal = (FileBaseRecipe) getIntent().getSerializableExtra("object");

		checkIfFavorite();
		ScrollView scroll = new ScrollView(this);
		setContentView(scroll);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(15, 0, 15, 15);
		scroll.addView(layout);

		// get info
		String recName = meal.getName();
		int numOfProducts = meal.getIngredient().length;
		String[] productEntries = meal.getIngredient();
		int numOfsteps = meal.getValueOfStep().length;

		// Recipe name
		LinearLayout layoutTopH = new LinearLayout(this);
		layoutTopH.setOrientation(LinearLayout.HORIZONTAL);

		TextView tvRecepieName = new TextView(getApplicationContext());
		tvRecepieName.setTextSize(30);
		tvRecepieName.setText(recName);
		tvRecepieName.setGravity(Gravity.CENTER_HORIZONTAL);
		tvRecepieName.setTextColor(Color.BLACK);
		tvRecepieName.setWidth(width / 4 * 3);
		tvRecepieName.setPadding(0, 0, 0, 25);

		layoutTopH.addView(tvRecepieName);
		layoutTopH.addView(bFav);
		layout.addView(layoutTopH);

		// set product names
		for (int i = 0; i < numOfProducts; i++) {
			productEntries[i] = meal.getIngredient(i);
		}
		int length = productEntries.length;
		for (int i = 0; i < length; i++) {
			TextView tv = new TextView(getApplicationContext());
			tv.setText(productEntries[i]);
			tv.setTextColor(Color.DKGRAY);
			layout.addView(tv);
		}

		// Instructions:
		TextView prepMethod = new TextView(getApplicationContext());
		prepMethod.setTextSize(15);
		prepMethod.setText("\nPreparation method:\n");
		layout.addView(prepMethod);

		// set steps
		for (int i = 0; i < numOfsteps; i++) {
			TextView tv = new TextView(getApplicationContext());
			TextView tv2 = new TextView(getApplicationContext());
			tv.setText("Step # " + (i + 1) + ":");
			tv2.setText(meal.getValueOfStep(i) + "\n");
			tv.setTextColor(Color.BLACK);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			tv2.setGravity(Gravity.CENTER_HORIZONTAL);
			tv2.setTextColor(Color.BLACK);
			tv2.setTextSize(20);
			tv2.setTypeface(null, Typeface.ITALIC);
			tv.setTypeface(null, Typeface.BOLD);
			tv.setPadding(15, 0, 15, 0);
			layout.addView(tv);
			layout.addView(tv2);
		}

		final Chronometer chron = new Chronometer(this);
		chron.setTextSize(25);
		chron.setTextColor(Color.RED);
		chron.setGravity(Gravity.CENTER_HORIZONTAL);

		Button bStart = new Button(this);
		bStart.setText("Start Chronometer");
		bStart.setWidth(buttonWidth);
		Button bStop = new Button(this);
		bStop.setText("Stop Chronometer");
		bStop.setWidth(buttonWidth);

		LinearLayout layoutBotH = new LinearLayout(this);
		layoutBotH.setOrientation(LinearLayout.HORIZONTAL);

		layout.addView(chron);
		layoutBotH.addView(bStart);
		layoutBotH.addView(bStop);

		layout.addView(layoutBotH);

		bStart.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chron.setBase(SystemClock.elapsedRealtime());
				chron.start();
			}
		});

		bStop.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chron.stop();
				chron.setText("00:00");

			}
		});
		bFav.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				favorite = !favorite;
				setPicture(favorite);
			}
		});

	}

	boolean favorite = false;

	public void checkIfFavorite() {

		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"MyPref", 0);
		Gson gson = new Gson();
		String jsonStr = pref.getString("objectList", "");
		Type type = new TypeToken<List<FileBaseRecipe>>() {
		}.getType();

		List<FileBaseRecipe> favoriteRecipes = gson.fromJson(jsonStr, type);
		if (favoriteRecipes != null) {
			// if recipe is in our favourite list --- lets put red color

			if (favoriteRecipes.contains(meal)) {
				// bFav.setBackgroundColor(Color.RED);
				favorite = true;
			}
		}
		setPicture(favorite);
	}

	private void setPicture(boolean favorite2) {
		if (favorite2) {
			bFav.setBackgroundResource(R.drawable.like1);
		} else {
			bFav.setBackgroundResource(R.drawable.unlike1);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"MyPref", 0);
		Gson gson = new Gson();
		String jsonStr = pref.getString("objectList", "");
		Type type = new TypeToken<List<FileBaseRecipe>>() {
		}.getType();

		List<FileBaseRecipe> favoriteRecipes = gson.fromJson(jsonStr, type);
		if (favoriteRecipes == null)
			favoriteRecipes = new ArrayList<FileBaseRecipe>();
		// ArrayList<String> names = new ArrayList<String>();

		if (favorite) {
			// if there wasn't before
			if (!favoriteRecipes.contains(meal)) {
				favoriteRecipes.add(meal);
			}
		} else {
			// if contains then remove
			if (favoriteRecipes.contains(meal)) {
				favoriteRecipes.remove(meal);

			}

		}

		Editor editor = pref.edit();
		String res = gson.toJson(favoriteRecipes);
		editor.putString("objectList", res);
		editor.commit();

	}

}
