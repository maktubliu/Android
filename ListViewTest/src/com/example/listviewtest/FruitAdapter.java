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
	public FruitAdapter(Context context, int textViewResourceId,List<Fruit> objects){//�����ģ�����ֵ�id������
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Fruit fruit = getItem(position);//��ǰʵ��
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);//layoutinflater���ش���Ĳ���	
			viewHolder = new ViewHolder();
			viewHolder.fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
			viewHolder.fruitName = (TextView)view.findViewById(R.id.fruit_name);
			view.setTag(viewHolder);
		}else{
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}
		//ImageView fruitimage = (ImageView)view.findViewById(R.id.fruit_image);//��ȡʾ��
		//TextView fruitname = (TextView)view.findViewById(R.id.fruit_name);//��ȡʾ��
		viewHolder.fruitImage.setImageResource(fruit.imageId());//��ʾͼƬ
		viewHolder.fruitName.setText(fruit.getName());//��ʾ����
		return view;
	}
	class ViewHolder{
		ImageView fruitImage;
		TextView fruitName;
	}
}
