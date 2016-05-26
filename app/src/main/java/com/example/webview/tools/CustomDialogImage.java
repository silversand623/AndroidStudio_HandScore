package com.example.webview.tools;

import com.example.webview.R;
import com.koushikdutta.ion.Ion;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

public class CustomDialogImage extends Dialog{
	private String bb;
	private String bbUpload;
	Context context;
	LayoutParams p ;
	boolean dd;
	DialogListener dialogListener;	
	private ImageView imageView;
	private ImageView imageViewUpload;
	public CustomDialogImage(Context context,int theme,DialogListener dialogListener,String aa,String aaUpload,boolean cc) {
		super(context,theme);
		this.context = context;
		this.dialogListener = dialogListener;
		bb=aa;
		bbUpload=aaUpload;
		dd=cc;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.dialogimage_layout);
		imageView=(ImageView)findViewById(R.id.dialog_title_imageNormal);
		//设置图片内容
    	Ion.with(imageView)
        // use a placeholder google_image if it needs to load from the network
        .placeholder(R.drawable.username)
        .error(R.drawable.username)
        // load the url
        .load(bb);
    	imageViewUpload=(ImageView)findViewById(R.id.dialog_title_imageUpload);
/*
    	Ion.with(imageViewUpload)
        // use a placeholder google_image if it needs to load from the network
        .placeholder(R.drawable.username)
        .error(R.drawable.username)
        // load the url
        .load(bbUpload);
*/
		Ion.with(context)
				.load(bbUpload)
				.noCache()
				.withBitmap()
				.placeholder(R.drawable.username)
				.error(R.drawable.username)
				.intoImageView(imageViewUpload);

		Button btnOk = (Button) findViewById(R.id.tablet_ok);		
		
		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {

					CustomDialogImage.this.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		/*Button btnCancel = (Button)findViewById(R.id.tablet_cancel);
		if(dd==true){
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cancel();
			}
		});
		}
		else{
			btnCancel.setVisibility(View.GONE);
		}*/
	}
}
