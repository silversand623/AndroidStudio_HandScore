package com.example.webview;

import java.util.Timer;
import java.util.TimerTask;

import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.DialogListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	// private Bitmap bmp;
	double scale = 0.0;
	private AbsoluteLayout layout;
	ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		layout = (AbsoluteLayout) findViewById(R.id.relativescale);
		Handler x = new Handler();
		x.postDelayed(new splashhandler(), 2000); // 延时2秒
	}

	class splashhandler implements Runnable {

		public void run() {
			if (isNetworkConnected(getApplicationContext()) == false) {
				CustomDialog customDialog = new CustomDialog(
						SplashActivity.this, R.style.MyDialog,
						new DialogListener() {
							@Override
							public void refreshActivity(Object object) {
								SplashActivity.this.finish();
							}
						}, "没有网络可用", false);
				customDialog.show();
			} else {
				startActivity(new Intent(getApplication(), LoginActivity.class));
				// 其中HelloAndroidActivity是你要进入的下一个Activity
				SplashActivity.this.finish();
				
			}

		}

		public boolean isNetworkConnected(Context context) {
			final ConnectivityManager connMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			final android.net.NetworkInfo wifi = connMgr
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifi.isAvailable())
				return true;
			else
				return false;
		}

	}
}
