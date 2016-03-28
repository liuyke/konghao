package org.liuyk.konghao.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 可以嵌套在ScrollView中的ListView
 * @author liu_yunke@sina.cn
 *
 */
public class ScrollInListView extends ListView {

	public ScrollInListView(Context context) {
		this(context, null);
	}

	public ScrollInListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollInListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
