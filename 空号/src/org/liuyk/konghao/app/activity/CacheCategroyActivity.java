package org.liuyk.konghao.app.activity;

import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.app.adapter.CacheCategoriesAdpater;
import org.liuyk.konghao.app.base.BaseActivity;
import org.liuyk.konghao.db.Category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CacheCategroyActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = "CacheCategroyActivity";
	private ListView listView;
	private CacheCategoriesAdpater adapter;
	private RelativeLayout titleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cache_categroy);
		initViews();
		initDatas();
	}
	
	public static void actionStart(Context context) {
		Intent intent = new Intent(context, CacheCategroyActivity.class);
		context.startActivity(intent);
	}

	private void initViews() {
		listView = (ListView) findViewById(R.id.id_list_view);
		TextView title = (TextView) findViewById(R.id.id_tv_title);
		title.setText("缓存管理");
		TextView right = (TextView) findViewById(R.id.id_tv_title_right);
		right.setText("编辑");
		right.setOnClickListener(this);
		TextView left = (TextView) findViewById(R.id.id_tv_title_left);
		left.setText("返回");
		left.setOnClickListener(this);
		titleView = (RelativeLayout) findViewById(R.id.id_title_view);
		layoutTitleBar(titleView);
	}
	
	private void initDatas() {
		List<Category> allCategories = Category.findAll(Category.class, true);
		if(adapter == null) {
			adapter = new CacheCategoriesAdpater(this, allCategories);
			listView.setAdapter(adapter);
		} else {
			adapter.setDatas(allCategories);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tv_title_left://返回
			finish();
			break;
		case R.id.id_tv_title_right://编辑
			break;
		}
	}
	
}
