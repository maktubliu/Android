package com.example.listviewtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class FruitAdapter extends ArrayAdapter<Fruit>{
	public int resourceId;
	public FruitAdapter(Context context, int textViewResourceId,List<Fruit> objects){//上下文，子项布局的id，数据
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Fruit fruit = getItem(position);//当前实例
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);//layoutinflater加载传入的布局	
			viewHolder = new ViewHolder();
			viewHolder.fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
			viewHolder.fruitName = (TextView)view.findViewById(R.id.fruit_name);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}
		//ImageView fruitimage = (ImageView)view.findViewById(R.id.fruit_image);//获取示例
		//TextView fruitname = (TextView)view.findViewById(R.id.fruit_name);//获取示例
		viewHolder.fruitImage.setImageResource(fruit.imageId());//显示图片
		viewHolder.fruitName.setText(fruit.getName());//显示文字
		return view;
	}
	class ViewHolder{
		ImageView fruitImage;
		TextView fruitName;
	}
}
