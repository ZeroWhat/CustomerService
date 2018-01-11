package com.example.listviewbuttonclick;

import java.util.ArrayList;

import java.util.List;

import com.example.listviewbuttonclick.adapter.ContentAdapter;
import com.example.listviewbuttonclick.adapter.inter.InterClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

//MainActivity需要实现自定义接口
public class MainActivity extends Activity implements OnItemClickListener,
		InterClick {

	// 模拟listview中加载的数据
	private static final String[] CONTENTS = { "北京", "上海", "广州", "深圳", "苏州",
			"南京", "武汉", "长沙", "杭州" };
	private List<String> contentList;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		mListView = (ListView) findViewById(R.id.listview);
		contentList = new ArrayList<String>();
		for (int i = 0; i < CONTENTS.length; i++) {
			contentList.add(CONTENTS[i]);
		}
		mListView.setAdapter(new ContentAdapter(this, contentList, this));
		mListView.setOnItemClickListener(this);
	}

	/**
	 * 响应ListView的条目点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		Toast.makeText(this, "点击的条目位置是-->" + position, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 接口方法，响应ListView按钮点击事件
	 */

	@Override
	public void commentClick(View v) {
		Toast.makeText(
				MainActivity.this,
				"listview的内部的评论按钮被点击了！，位置是-->" + (Integer) v.getTag()
						+ ",内容是-->" + contentList.get((Integer) v.getTag()),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void shareClick(View v) {
		Toast.makeText(
				MainActivity.this,
				"listview的内部的分享按钮被点击了！，位置是-->" + (Integer) v.getTag()
						+ ",内容是-->" + contentList.get((Integer) v.getTag()),
				Toast.LENGTH_SHORT).show();
	}
}