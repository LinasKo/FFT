package com.example.fft;

import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

import com.example.fft.SearchActivity.Pair;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<Pair>{

	Context context;
	List<Pair> objects;
	public CustomArrayAdapter(Context context, List<Pair> objects) {
		super(context, R.layout.custom_list_item, objects);
		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false);
		Pair pair = objects.get(position);
		
		TextView recipeView = (TextView) view.findViewById(R.id.dd);
		recipeView.setText(pair.getRec().getName());
		TextView coeficient = (TextView) view.findViewById(R.id.ff);
		coeficient.setText(pair.getCoef()+"%");
		
		return view;
	}
	

	

}
