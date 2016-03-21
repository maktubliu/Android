package com.example.listviewtest;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class Fruit{
	private int imageId;
	private String name;
	public Fruit(String name, int imageId){
		this.name = name;
		this.imageId = imageId;
	}
	public String getName(){
		return name;
	}
	public int imageId(){
		return imageId;
	}
}
