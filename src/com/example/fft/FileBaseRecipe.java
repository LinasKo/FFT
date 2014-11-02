package com.example.fft;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class FileBaseRecipe implements Serializable{
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object o) {
		return name.equals(((FileBaseRecipe)o).name);
	}

	private String name;
	private String[] ingredient;
	private String[] valueOfStep;
	private String[] shortIngredient;
	
	//name - recepto pavadinimas
	//ingredient - ingridientu String masyvas
	//aValueOfSteps - gaminimo zingsneliu masyvas
	//aShortIngredient - ingridientas (be vienetu)

	public FileBaseRecipe(String aName, String[] aIngredient,
			String[] aValueOfSteps, String[] aShortIngredient) {
		name = aName;
		ingredient = aIngredient;
		valueOfStep = aValueOfSteps;
		shortIngredient = aShortIngredient;
	}

	/*-----------------Setters -----------------------------*/

	public void setName(String name) {
		this.name = name;
	}

	public void setIngredient(String[] ingredient) {
		this.ingredient = ingredient;
	}

	public void setValueOfStep(String[] valueOfStep) {
		this.valueOfStep = valueOfStep;
	}

	public void setShortIngredient(String[] shortIngredient) {
		this.shortIngredient = shortIngredient;
	}

	/*----------------Getters----------------------------------*/
	public String getName() {
		return name;
	}

	public String[] getIngredient() {
		return ingredient;
	}

	public String getIngredient(int a) {
		return ingredient[a];
	}

	public String[] getValueOfStep() {
		return valueOfStep;
	}

	public String getValueOfStep(int a) {
		return valueOfStep[a];
	}

	public String[] getShortIngredient() {
		return shortIngredient;
	}


}
