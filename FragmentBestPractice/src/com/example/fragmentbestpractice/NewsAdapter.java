package com.example.fragmentbestpractice;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<News>{//新闻列表适配器
	private int resourceId;
	public NewsAdapter(Context context, int textViewResourceId, List<News> objects){//上下文，ListView子项布局的ID, 适配的数据
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){//返回一个加载了数据的view
		News news = getItem(position);
		View view;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		}
		else{
			view = convertView;
		}
		TextView newsTitleText = (TextView) view.findViewById(R.id.news_title);
		newsTitleText.setText(news.gettitle());
		return view;
	}
}
