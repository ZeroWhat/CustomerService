package com.example.listviewbuttonclick.adapter;

import java.util.List;

import com.example.listviewbuttonclick.R;
import com.example.listviewbuttonclick.adapter.inter.InterClick;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ContentAdapter extends BaseAdapter  {

	private static final String TAG = "ContentAdapter";
	private List<String> mContentList;
	private LayoutInflater mInflater;
	private InterClick mCallback;

	public ContentAdapter(Context context, List<String> contentList,
			InterClick callback) {
		mContentList = contentList;
		mInflater = LayoutInflater.from(context);
		mCallback = callback;
	}

	@Override
	public int getCount() {
		Log.i(TAG, "getCount");
		return mContentList.size();
	}

	@Override
	public Object getItem(int position) {
		Log.i(TAG, "getItem");
		return mContentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		Log.i(TAG, "getItemId");
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "getView");
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.button1 = (Button) convertView.findViewById(R.id.button1);
			holder.button2 = (Button) convertView.findViewById(R.id.button2);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mContentList.get(position));
		holder.button1.setOnClickListener(this);
		holder.button2.setOnClickListener(this);

		// 设置位置，获取点击的条目按钮
		holder.button1.setTag(position);
		holder.button2.setTag(position);
		return convertView;
	}

	public class ViewHolder {
		public TextView textView;
		public Button button1;
		public Button button2;

	}
	public class MyClick implements Oncli


}