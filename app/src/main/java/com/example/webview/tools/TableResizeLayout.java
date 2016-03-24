package com.example.webview.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class TableResizeLayout extends TableLayout  {
	private OnResizeListener mListener;
	public interface OnResizeListener{
		void OnResize(int  w,int h,int oldw,int oldh);
	}
  public void setOnResizeListener(OnResizeListener l)
  {
	  mListener=l;
  }
	@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// TODO Auto-generated method stub
	super.onSizeChanged(w, h, oldw, oldh);
	if(mListener!=null){
		mListener.OnResize(w, h, oldw, oldh);
	}
}
	public TableResizeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

}
