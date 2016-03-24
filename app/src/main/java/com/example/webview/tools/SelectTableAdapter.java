package com.example.webview.tools;

import java.util.ArrayList;
import java.util.HashMap;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.webview.R;
import com.example.webview.SeclectTableActivity;

public class SelectTableAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HashMap<String, Object>> videoItems;
	public TextView txvshowScoretablename,txvshowScoretablemethod1,txvshowScoretablemethod2,txvshowScoretablemethod3,txvshowScoretablemethod4,txvshowScoretablemethod5,txvshowScoretablemethod6,txvshowScoretablemethod7,txvshowScoretablemethod8;
    LayoutInflater inflater;
    View tableRow;
    private int selectItem=-1;
    private int top=-1;
    Typeface font;
  //int number;
//    int width,height;
    public SelectTableAdapter(Context _context,ArrayList<HashMap<String, Object>> _items,Typeface face){
    	context=_context;
    	videoItems=_items;
    	inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	font=face;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videoItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return videoItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		tableRow=inflater.inflate(R.layout.itemtable, null);
		 RelativeLayout layout = (RelativeLayout)tableRow.findViewById(R.id.relativetable);
		 ArrayList<HashMap<String, Object>> tableItem = new ArrayList<HashMap<String, Object>>();
		 tableItem=(ArrayList<HashMap<String, Object>>) videoItems.get(arg0).get("2");
		 int i1=tableItem.size();
		// Typeface face = Typeface.createFromAsset (context.getAssets() , "MSYH.zip" );
		 TextView Btn[] = new TextView[i1];
		 int j = -1;
		 for (int i=0; i<i1; i++) {
		 Btn[i]=new TextView(context);
		 Btn[i].setId(2000+i);
		 Btn[i].setTextColor(context.getResources().getColor(R.color.temple));
		 Btn[i].setTextSize(TypedValue.COMPLEX_UNIT_PT,9);
		 Btn[i].setTypeface(font);
		 TextPaint tp =  Btn[i].getPaint(); 
			tp.setFakeBoldText(true);
		 Btn[i].setEllipsize(TruncateAt.END);
		// 设定text内容与边框的距离  
		 RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams
		 (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		 btParams.setMargins(20, 0, 0, 0);
			
		
		 //这里不需要findbtnId，因为创建的时候已经确定哪个按钮对应哪个Id
			 Btn[i].setText(tableItem.get(i).get("0").toString());
			 if(i==0){
				 btParams.leftMargin =20; //横坐标定位
				 btParams.topMargin = 40; //纵坐标定位
				 top= btParams.topMargin;
			 }
			 else if(tableItem.get(i).get("0").toString().length()<=25){
				 
				 if(tableItem.get(i-1).get("0").toString().length()>25){
					 btParams.leftMargin =20; //横坐标定位
					 btParams.topMargin = top+80; //纵坐标定位
					 top= btParams.topMargin;
				 }else{
				 btParams.leftMargin =20; //横坐标定位
				 btParams.topMargin = top+30; //纵坐标定位
				 top= btParams.topMargin;
				 }
			 }else{
				 if(tableItem.get(i-1).get("0").toString().length()<=25){
					 btParams.leftMargin =20; //横坐标定位
					 btParams.topMargin =  top+35; //纵坐标定位
					 top= btParams.topMargin;
				 }else{
					 if(tableItem.get(i-1).get("0").toString().length()<50){
						 btParams.leftMargin =20; //横坐标定位
						 btParams.topMargin =  top+60; //纵坐标定位
						 top= btParams.topMargin;
					 }else{
				 btParams.leftMargin =20; //横坐标定位
				 btParams.topMargin = top+85; //纵坐标定位
				 top= btParams.topMargin;
					 }
				 }
			 }
			
		
		 layout.addView(Btn[i],btParams); //将按钮放入layout组件
		 // intent.putExtra("num", tableArrayList.get(i).toString());
		 }
		txvshowScoretablename=(TextView)tableRow.findViewById(R.id.txvshowScoretablename);
//		txvshowScoretablemethod1=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod1);
//		txvshowScoretablemethod2=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod2);
//		txvshowScoretablemethod3=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod3);
//		txvshowScoretablemethod4=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod4);
//		txvshowScoretablemethod5=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod5);
//		txvshowScoretablemethod6=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod6);
//		txvshowScoretablemethod7=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod7);
//		txvshowScoretablemethod8=(TextView)tableRow.findViewById(R.id.txvshowScoretablemethod8);
//	 showoperationmethod.setTextSize(size);
//		showoperationcontent.setTextSize(size);
//		showallotedscore.setTextSize(size);
//		final Spinner  spinner = (Spinner)scoreRow.findViewById(R.id.Spinner02);  
//		Button addButton=(Button)scoreRow.findViewById(R.id.btn_scoreadd);
//		Button reduceButton=(Button)scoreRow.findViewById(R.id.btn_scorereduce);
		txvshowScoretablename.setText(videoItems.get(arg0).get("1").toString());
		txvshowScoretablename.setTypeface(font);
		TextPaint tp = txvshowScoretablename.getPaint(); 
		tp.setFakeBoldText(true);
//		txvshowScoretablemethod1.setText(videoItems.get(arg0).get("1").toString());
//		txvshowScoretablemethod2.setText(videoItems.get(arg0).get("2").toString());
//		txvshowScoretablemethod3.setText(videoItems.get(arg0).get("3").toString());
//		txvshowScoretablemethod4.setText(videoItems.get(arg0).get("4").toString());
//		txvshowScoretablemethod5.setText(videoItems.get(arg0).get("5").toString());
//		txvshowScoretablemethod6.setText(videoItems.get(arg0).get("6").toString());
//		txvshowScoretablemethod7.setText(videoItems.get(arg0).get("7").toString());
//		txvshowScoretablemethod8.setText(videoItems.get(arg0).get("8").toString());
//		if (arg0 == selectItem) {
//			//tableRow.setBackgroundResource(R.color.red);
//			tableRow.setBackgroundResource(R.drawable.et_border);
//			SeclectTableActivity.selecttableflag=true;
////			 for ( int k = 0; k <= Btn.length-1; k++) {
////				 //这里不需要findbtnId，因为创建的时候已经确定哪个按钮对应哪个Id
////					
////					// Btn[k].setTextColor(Color.RED);
////				 // intent.putExtra("num", tableArrayList.get(i).toString());
////				 }
//		//	 txvshowScoretablename.setTextColor(Color.RED);
//			tableRow.setFocusable(true);
//			}
//			else {
//				//tableRow.setBackgroundResource(R.color.transparent);
//				tableRow.setBackgroundResource(R.color.transparent);
////				tableRow.setFocusable(false);
////				 for ( int k = 0; k <= Btn.length-1; k++) {
////					 //这里不需要findbtnId，因为创建的时候已经确定哪个按钮对应哪个Id
////						
////						 Btn[k].setTextColor(Color.BLACK);
////					 // intent.putExtra("num", tableArrayList.get(i).toString());
////					 }
////				 txvshowScoretablename.setTextColor(Color.BLACK);
//			}
//		 final ArrayList<String> allcountries = new ArrayList<String>();
//		  for(int i=0;i<=Integer.parseInt(videoItems.get(arg0).get("3").toString());i++){
//		   allcountries.add(i, String.valueOf(i));
//		  }
////		  SpinnerAdapter adapter = new SpinnerAdapter(context,
////		            android.R.layout.simple_spinner_item, allcountries,size);
//		  ArrayAdapter<String> adapter= new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,allcountries);
//		  RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams (width,height);  
//	        btParams.setMargins(850, 0, 0, 0);
//	        spinner.setLayoutParams(btParams);
//		  spinner.setAdapter(adapter);
//		  if(videoItems.get(a).containsKey("5")){
//			  spinner.setSelection(Integer.parseInt(videoItems.get(arg0).get("5").toString()));
//		  }else{
//		  spinner.setSelection(Integer.parseInt(videoItems.get(arg0).get("3").toString()));
//		  videoItems.get(a).put("5",videoItems.get(arg0).get("3").toString());
//		  }
//	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			 videoItems.get(a).put("5",arg0.getItemAtPosition(arg2).toString());
//			
//		}
//
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//	});
//		  
//		  addButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				int score=Integer.parseInt(spinner.getSelectedItem().toString());
//				String bb=allcountries.get(allcountries.size()-1);
//				if(score==Integer.parseInt(bb)){
//					  CustomDialog customDialog=new CustomDialog(context,new DialogListener() {
//							@Override
//							public void refreshActivity(Object object) {							
//							
//							}
//						}, "已到最高分",false);
//			customDialog.show();
//				}else{
//				spinner.setSelection(score+1);
//				videoItems.get(a).put("4",String.valueOf(score+1));
//				videoItems.get(a).put("5",String.valueOf(score+1));
//				}
//			}
//		});
//		  reduceButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				int score=Integer.parseInt(spinner.getSelectedItem().toString());
//				String bb=allcountries.get(0);
//				if(score==Integer.parseInt(bb)){
//					  CustomDialog customDialog=new CustomDialog(context,new DialogListener() {
//							@Override
//							public void refreshActivity(Object object) {							
//							
//							}
//						}, "已到最低分",false);
//			customDialog.show();
//				}else{
//				spinner.setSelection(score-1);
//				videoItems.get(a).put("4",String.valueOf(score-1));
//				videoItems.get(a).put("5",String.valueOf(score-1));
//				}
//			}
//		});
		return tableRow;
	}
	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
		}
}
