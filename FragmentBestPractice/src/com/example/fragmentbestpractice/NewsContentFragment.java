package com.example.fragmentbestpractice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsContentFragment extends Fragment{
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		view = inflater.inflate(R.layout.news_content_frag, container, false);//
		return view;
	}
	public void refresh(String newstitle, String newscontent){
		View visibilityLayout = view.findViewById(R.id.visibility_layout);
		visibilityLayout.setVisibility(View.VISIBLE);
		TextView newsTitleTest = (TextView)view.findViewById(R.id.news_title);
		TextView newsContentTest = (TextView)view.findViewById(R.id.news_content);
		newsTitleTest.setText(newstitle);//刷新新闻的标题
		newsContentTest.setText(newscontent);//刷新新闻的内容
	}
}
