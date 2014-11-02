package com.example.fft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	

	private static final String TAG = "SearchActivity";
	private static final String KEY_INDEX = "index";



	Button addProductButton, deleteLastProductButton;
	AutoCompleteTextView multiText;
	TextView showEnteredItems;
	ListView list;
	CustomArrayAdapter listAdapter;
	ArrayList<Pair> listArray = new ArrayList<Pair>();
	ArrayList<String> foodProducts;
	//database stuff
	AccessDatabase access;
	AccessDatabase2 access2;
	ArrayList<FileBaseRecipe> recipes = new ArrayList<FileBaseRecipe>();
	
	ArrayList<String> rememberProducts = new ArrayList<String>();
	public static final String DEFAULT_TEXT = "Items: ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search);

		if (savedInstanceState != null) {
			 rememberProducts = savedInstanceState.getStringArrayList(KEY_INDEX);
		 } 
		
		multiText = (AutoCompleteTextView)findViewById(R.id.autoText);
		showEnteredItems = (TextView)findViewById(R.id.enteredProducts);
		addProductButton = (Button)findViewById(R.id.addProduct);
		deleteLastProductButton = (Button)findViewById(R.id.deleteProduct);
		list = (ListView)findViewById(android.R.id.list);

		foodProducts = getFoodProducts();
		//dropdown list adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, foodProducts);
		multiText.setAdapter(adapter);
		
		//list adapter        name ----- coeficient
		listAdapter = new CustomArrayAdapter(this, listArray);
		list.setAdapter(listAdapter);
		list.setClickable(true);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(SearchActivity.this, SpecificRecepie.class);
				intent.putExtra("object", topRecipes.get(position));
				startActivity(intent);
			}
		});
		
		showEnteredItems.setText(DEFAULT_TEXT);
		
		//creating databases
		access = new AccessDatabase();
		access2 = new AccessDatabase2();
		recipes = access.recipes;
		recipes.addAll(access2.recipes);
		
		addItemsToTextView();
		
	}
	public ArrayList<String> getFoodProducts(){
		ArrayList<String> products = new ArrayList<String>();
		AssetManager am = this.getAssets();
		try {
			InputStream input = am.open("foodProducts.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String prod;
			while((prod = reader.readLine()) != null){
				products.add(prod);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return products;
	}
	/** when add button is clicked we check if product exists in out database. if it exists
	 * but we already have it-- nothing happens. If it is unique -- we add it. */
	public void clickedToAdd(View view){
		String entered = multiText.getText().toString();
		//exists in database
		if (foodProducts.contains(entered)){
			//if it is already included
			if (rememberProducts.contains(entered)){
				multiText.setText("");
			}
			else{
				rememberProducts.add(entered);
				addItemsToTextView();
				multiText.setText("");
			}
		}
		else{
			Toast.makeText(this, "we don't have such ingredient in our database", Toast.LENGTH_LONG).show();
		}
	}
	
	public void clickedToDelete(View view){
		//remove last entered product
		rememberProducts.remove(rememberProducts.size()-1);
		addItemsToTextView();
		if (rememberProducts.isEmpty()){
			deleteLastProductButton.setVisibility(View.INVISIBLE);
		}
		
	}
	public void addItemsToTextView(){
		StringBuilder productsInText = new StringBuilder(rememberProducts.toString());
		//removing [] brackets
		String newText = DEFAULT_TEXT + productsInText.substring(1, productsInText.length()-1);
		showEnteredItems.setText(newText);
		
		deleteLastProductButton.setVisibility(View.VISIBLE);
		
		updateList();
	}

	//String [] randomText = new String [] {"ksdlafk", "klsk", "ioeroi","5654564","s5f45s4d","kdjowi4","kjf687f54sd","fkjoimv64","kfjsk"};
	private static final int QUEUE_SIZE = 10;
	private  ArrayList<FileBaseRecipe> topRecipes = new ArrayList<FileBaseRecipe>();
	
	/** algorithm to match products with recipes in the database*/
	private void updateList() {
		//top recipes changed
		topRecipes.clear();
		listArray.clear();
		PriorityQueue<Pair> queue = new PriorityQueue<Pair>(10,new Comparator<Pair>() {

			@Override
			public int compare(Pair lhs, Pair rhs) {
				if (lhs.getCoef() > rhs.getCoef())
					return 1;
				return -1;
			}
		});
		
		for(FileBaseRecipe recipe : recipes){
			String [] ingredients = recipe.getShortIngredient();
			float neededAmount = ingredients.length, coef = 0;
			int matches = 0, coefInt;
			
			for (String ing : ingredients){
				if (rememberProducts.contains(ing.toLowerCase())){
					matches++;
				}
			}

			coef = matches/neededAmount*100;
			coefInt = (int)coef;
			if (coefInt != 0){
				Pair pair = new Pair(recipe, coefInt);
				queue.add(pair);
				if (queue.size() > QUEUE_SIZE){
					queue.poll();
				}
			}

		}
		
		while(!queue.isEmpty()){
			Pair unique = queue.poll();
			FileBaseRecipe recipeData = unique.getRec();
			topRecipes.add(recipeData);
			listArray.add(unique);
		}
		
		Collections.reverse(listArray);	
		Collections.reverse(topRecipes);
		list.setAdapter(listAdapter);
	}
	
	/** helper class */
	public static class Pair{
		FileBaseRecipe rec;
		int coef;
		Pair(FileBaseRecipe rec, int coef){
			this.rec = rec;
			this.coef = coef;
		}
		
		public FileBaseRecipe getRec(){
			return rec;
		}
		public int getCoef(){
			return coef;
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putStringArrayList(KEY_INDEX, rememberProducts);
		super.onSaveInstanceState(savedInstanceState);
	}
}
