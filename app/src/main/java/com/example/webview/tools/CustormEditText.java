package com.example.webview.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustormEditText extends EditText {
	 
    private Context mContext;

    public CustormEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
            this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);

            this.setFocusable(true);
            this.setBackgroundColor( Color.WHITE );
            Paint mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);        //        设置style
            mPaint.setAntiAlias(true); // 抗锯齿
            mPaint.setStrokeWidth( 2 ); // 设置边框宽度
            mPaint.setColor(Color.parseColor("#02b9f5")); // 设置边框颜色
             
             
            canvas.drawRect( new Rect(0, 0, getWidth(), getHeight()), mPaint);

    }

}
