package com.example.webview;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SegmentSet extends LinearLayout  {
	private TextView segLeftText;
	public TextView getSegLeftText() {
		return segLeftText;
	}

	public void setSegLeftText(TextView segLeftText) {
		this.segLeftText = segLeftText;
	}
	
	private TextView segRightText;
	public TextView getSegRightText() {
		return segRightText;
	}

	public void setSegRightText(TextView segRightText) {
		this.segRightText = segRightText;
	}
	
	
	private onSegmentViewClickListener listener;
	public SegmentSet(Context context, AttributeSet attrs) {
		super(context,attrs);
		init(); 
		// TODO 自动生成的构造函数存根
	}

	public SegmentSet(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		init();
	}

	private void init() {		
		segLeftText = new TextView(getContext());
		segRightText = new TextView(getContext());  
		segLeftText.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
		segRightText.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1)); 
		//segLeftText.setText("未评分(186)人");  
		//segCenterText.setText("已评分(0)人");  
		//segRightText.setText("全部(186)人");  
		      XmlPullParser xrp = getResources().getXml(R.drawable.seg_text_color_selector);    
		       try {    
		           ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);    
		           segLeftText.setTextColor(csl);
		           segRightText.setTextColor(csl);  
		        } catch (Exception e) {    
		        }   
		       segLeftText.setGravity(Gravity.CENTER);
		       segRightText.setGravity(Gravity.CENTER); 
		       segLeftText.setPadding(3, 6, 3, 6);
		      segRightText.setPadding(3, 6, 3, 6);  
		       setSegmentTextSize(16); 
		       segLeftText.setBackgroundResource(R.drawable.seg_left);
		       segRightText.setBackgroundResource(R.drawable.seg_right);
		       segLeftText.setSelected(true);  
		       this.removeAllViews();  
		       this.addView(segLeftText);
		       this.addView(segRightText);  
		       this.invalidate();
		       
		       segLeftText.setOnClickListener(new OnClickListener() {
		    	             @Override  
		    	             public void onClick(View v) {  
		    	               if (segLeftText.isSelected()) {  
		    	                     return;  
		    	                  }  
		    	               segLeftText.setSelected(true);
		    	               segRightText.setSelected(false);
		    	                 if (listener != null) {  
		    	                     listener.onSegmentViewClick(segLeftText, 0);  
		    	                 }  
		    	              }  
		    	 });

		       segRightText.setOnClickListener(new OnClickListener() {
	  	             @Override  
	  	             public void onClick(View v) {  
	  	               if (segRightText.isSelected()) {  
	  	                     return;  
	  	                  }  
	  	               segLeftText.setSelected(false);
	  	               segRightText.setSelected(true);  
	  	                 if (listener != null) {  
	  	                     listener.onSegmentViewClick(segRightText, 1);  
	  	                 }  
	  	              }  
			       });

	}

	 public void setSegmentTextSize(int dp) {  
		 segLeftText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
		 segRightText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp); 
	}  
		      
	private static int dp2Px(Context context, float dp) {  
	final float scale = context.getResources().getDisplayMetrics().density;  
	return (int) (dp * scale + 0.5f);  
	}  
		       
	 public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {  
		  this.listener = listener;  
	}
	public void setSegmentText(CharSequence text,int position) {  
		if (position == 0) {  
			segLeftText.setText(text);  
		  } 
		  if (position == 1) {  
			  segRightText.setText(text);  
		   } 
		}  

	public static interface onSegmentViewClickListener{  
		       /** 
		        *  
		        * <p>2014年7月18日</p> 
		        * @param v 
		       * @param position 0-左边 1-右边 
		        * @author RANDY.ZHANG 
		         */  
		       public void onSegmentViewClick(View v,int position);  
	}  

}
