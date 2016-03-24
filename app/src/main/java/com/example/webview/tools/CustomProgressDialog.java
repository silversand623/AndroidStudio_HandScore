package com.example.webview.tools;

import com.example.webview.R;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class CustomProgressDialog extends ProgressDialog{  
	private TextView progresstext;
      static String textString;
    public CustomProgressDialog(Context context, int theme) {  
        super(context, theme);  
      
    }  
  
    public CustomProgressDialog(Context applicationContext,String aa) {
    	 super(applicationContext);
    	  textString=aa;
	}

	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.dialog_progress);
        progresstext=(TextView)findViewById(R.id.txvprogresstext);
        progresstext.setText(textString);
    }  
      
    public static CustomProgressDialog show(Context ctx){  
        CustomProgressDialog d = new CustomProgressDialog(ctx,textString);  
        d.show();  
        return d;  
    }  
}  
