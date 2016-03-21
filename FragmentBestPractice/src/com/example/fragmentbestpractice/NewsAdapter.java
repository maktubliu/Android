package com.example.fragmentbestpractice;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<News>{//�����б�������
	private int resourceId;
	public NewsAdapter(Context context, int textViewResourceId, List<News> objects){//�����ģ�ListView����ֵ�ID, ���������
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){//����һ�����������ݵ�view
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
