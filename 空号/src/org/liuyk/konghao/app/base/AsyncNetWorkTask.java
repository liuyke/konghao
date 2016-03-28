package org.liuyk.konghao.app.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class AsyncNetWorkTask<T> {

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) {
			@SuppressWarnings("unchecked")
			T t = (T) msg.obj;
			AsyncNetWorkTask.this.post(t);
		};
	};
	
	protected abstract T doInBackground();
	
	protected abstract void post(T t);
	
	public void cancel() {
		handler.removeCallbacksAndMessages(null);
	}
	
	public AsyncNetWorkTask<T> excute() {
		new Thread() {
			public void run() {
				T t = doInBackground();
				handler.obtainMessage(1, t).sendToTarget();
			};
		}.start();
		return this;
	}
	
}
