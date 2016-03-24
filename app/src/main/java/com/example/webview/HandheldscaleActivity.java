package com.example.webview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.CustomProgressDialog;
import com.example.webview.tools.DialogListener;
import com.example.webview.tools.ResizeLayout;
import com.example.webview.tools.ResizeLayout.OnResizeListener;
import com.example.webview.tools.SelectScoreAdapter;

public class HandheldscaleActivity extends Activity{
	private TextView txvshowname,txvlogout,txvresulttablename,txvoperationmethod,txvoperationcontent,txvallotedscore,txvthisscore,txvshowstudentid,txvresulttable,txvstudentid,txvfontsize,txvthiscontext,txvstudentexamname,txvstudentshowexamname,txvstudentexamnumname,txvstudentshowexamnumname;
	private ListView listView;
	//private AutoCompleteTextView custormAutoCompleteTextView;
	private Button btnsmallfont,btnmiddlefont,btnlargefont,btnpreview,btnback;
	private String name;
	private String resulttablename;
	SelectScoreAdapter adapter;
	  private ArrayList<HashMap<String, Object>> listItem;
	  private boolean notscoreflag=false;
	  public static CustomProgressDialog dialog;
		int num=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoring);
		//SeclectTableActivity.dialog.dismiss();
//		ResizeLayout relativeLayout = (ResizeLayout) findViewById(R.id.relative2);
//		relativeLayout.setOnResizeListener(this);
//		custormAutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.actv_selectid);
//		custormAutoCompleteTextView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				
//				custormAutoCompleteTextView.setFocusableInTouchMode(true); // 设置模式,以便获取焦点
//				custormAutoCompleteTextView.requestFocus();
//				return false;
//			}
//		});
		if(PreviewActivity.dialog!=null){
		PreviewActivity.dialog.dismiss();
		}
		txvfontsize=(TextView)findViewById(R.id.txvfontsize);
		txvshowname=(TextView)findViewById(R.id.txvshowspname);
		txvlogout=(TextView)findViewById(R.id.txvlogout);
		txvresulttable=(TextView)findViewById(R.id.txvresulttable);
		txvresulttablename=(TextView)findViewById(R.id.txvresulttablename);
		txvstudentid=(TextView)findViewById(R.id.txvstudentid);
		txvoperationmethod=(TextView)findViewById(R.id.txvoperationmethod);
		txvoperationcontent=(TextView)findViewById(R.id.txvoperationcontent);
		txvallotedscore=(TextView)findViewById(R.id.txvallotedscore);
		txvthisscore=(TextView)findViewById(R.id.txvthisscore);
		txvthiscontext=(TextView)findViewById(R.id.txvthiscontext);
		//txvscoreoperation=(TextView)findViewById(R.id.txvscoreoperation);
		txvshowstudentid=(TextView)findViewById(R.id.txvshowstudentid);
		txvstudentexamname=(TextView)findViewById(R.id.txvstudentexamname);
		txvstudentshowexamname=(TextView)findViewById(R.id.txvstudentshowexamname);
		txvstudentexamnumname=(TextView)findViewById(R.id.txvstudentexamnumname);
		txvstudentshowexamnumname=(TextView)findViewById(R.id.txvstudentshowexamnumname);
		
//		Intent intent=getIntent();
//		name=intent.getStringExtra("name");
		SharedPreferences userInfo = getSharedPreferences("user_info", 0);
		txvstudentshowexamname.setText(userInfo.getString("examname", ""));
		txvstudentshowexamnumname.setText(userInfo.getString("ES_NAME", ""));
		//resulttablename=intent.getStringExtra("num");
		txvshowname.setText("欢迎，"+userInfo.getString("truename", ""));
		TextPaint tp = txvshowname.getPaint(); 
		tp.setFakeBoldText(true);
		txvlogout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		txvresulttablename.setText(userInfo.getString("tablenum",""));
		txvshowstudentid.setText(userInfo.getString("studentnum", ""));
		txvlogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(HandheldscaleActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		listView=(ListView)findViewById(R.id.mylist);
		Intent intent=getIntent();
		if(intent.getSerializableExtra("returnlist")!=null){
			listItem=(ArrayList<HashMap<String, Object>>) intent.getSerializableExtra("returnlist");
		}else{
			 listItem = new ArrayList<HashMap<String, Object>>();  
		     
	          HashMap<String, Object> map = new HashMap<String, Object>();  
	          map.put("1", 1);//图像资源的ID  
	          map.put("2", "步骤1");  
	          map.put("3", "5");
	          HashMap<String, Object> map1 = new HashMap<String, Object>();  
	          map1.put("1", 2);//图像资源的ID  
	          map1.put("2", "步骤2");  
	          map1.put("3", "10");  
	          HashMap<String, Object> map2 = new HashMap<String, Object>();  
	          map2.put("1", 3);//图像资源的ID  
	          map2.put("2", "步骤3");  
	          map2.put("3", "12");  
	          listItem.add(map);  
	          listItem.add(map1);  
	          listItem.add(map2);  
		}
		final Typeface face = Typeface.createFromAsset (getAssets() , "MSYH.zip" );
		Log.i("UIOIHNIBHOUOIHIOUOPUOPI", listItem.toString());
if(listItem.get(0).containsKey("6")&&listItem.get(0).containsKey ("7")){
	
}else{
for(int i=0;i<listItem.size();i++){
	listItem.get(i).put("6", "false");
	listItem.get(i).put("7", "false");
}
}
		adapter=new SelectScoreAdapter(HandheldscaleActivity.this, listItem,10,face,HandheldscaleActivity.this);
		listView.setAdapter(adapter);
		listView.clearFocus();
		btnsmallfont=(Button)findViewById(R.id.btn_smallfont);
		for(int i=0;i<listItem.size();i++){
			List<String>list=(List<String>) listItem.get(i).get("3");
			num+=list.size();
		}
		btnsmallfont.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setfont(6);
				adapter=new SelectScoreAdapter(HandheldscaleActivity.this, listItem,6,face,HandheldscaleActivity.this);
				btnsmallfont.setBackgroundResource(R.drawable.fontbtnb);
				 RelativeLayout.LayoutParams btParams1 = new RelativeLayout.LayoutParams
				 (45,45);
				 btParams1.setMargins(1204, 70, 0, 0);
				 btnsmallfont.setLayoutParams(btParams1);
				btnmiddlefont.setBackgroundResource(R.drawable.fontbtna);
				 RelativeLayout.LayoutParams btParams2 = new RelativeLayout.LayoutParams
				 (40,40);
				 btParams2.setMargins(1139, 72, 0, 0);
				 btnmiddlefont.setLayoutParams(btParams2);
				btnlargefont.setBackgroundResource(R.drawable.fontbtna);
				 RelativeLayout.LayoutParams btParams3 = new RelativeLayout.LayoutParams
				 (40,40);
				 btParams3.setMargins(1074, 72, 0, 0);
				 btnlargefont.setLayoutParams(btParams3);
				listView.setAdapter(adapter);
//				adapter.showoperationcontent.setTextSize(14);
//				adapter.showoperationmethod.setTextSize(14);
//				adapter.showallotedscore.setTextSize(14);
//				adapter.notifyDataSetInvalidated();
//				adapter.small();
				//adapter.notifyDataSetInvalidated();
			}
		});
		btnmiddlefont=(Button)findViewById(R.id.btn_middlefont);
		btnmiddlefont.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setfont(8);
				btnsmallfont.setBackgroundResource(R.drawable.fontbtna);
				btnmiddlefont.setBackgroundResource(R.drawable.fontbtnb);
				 RelativeLayout.LayoutParams btParams2 = new RelativeLayout.LayoutParams
				 (45,45);
				 btParams2.setMargins(1139, 70, 0, 0);
				btnmiddlefont.setLayoutParams(btParams2);
				btnlargefont.setBackgroundResource(R.drawable.fontbtna);
				 RelativeLayout.LayoutParams btParams1 = new RelativeLayout.LayoutParams
				 (40,40);
				 btParams1.setMargins(1204, 72, 0, 0);
				 btnsmallfont.setLayoutParams(btParams1);
				 RelativeLayout.LayoutParams btParams3 = new RelativeLayout.LayoutParams
				 (40,40);
				 btParams3.setMargins(1074, 72, 0, 0);
				 btnlargefont.setLayoutParams(btParams3);
				adapter=new SelectScoreAdapter(HandheldscaleActivity.this, listItem,8,face,HandheldscaleActivity.this);
//				adapter.showoperationcontent.setTextSize(20);
//				adapter.showoperationmethod.setTextSize(20);
//				adapter.showallotedscore.setTextSize(20);
				listView.setAdapter(adapter);
//				adapter.notifyDataSetInvalidated();
//				adapter.middle();
				
			}
		});
		btnlargefont=(Button)findViewById(R.id.btn_largefont);
		btnlargefont.setBackgroundResource(R.drawable.fontbtnb);
		btnlargefont.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setfont(10);
				adapter=new SelectScoreAdapter(HandheldscaleActivity.this, listItem,10,face,HandheldscaleActivity.this);
				btnsmallfont.setBackgroundResource(R.drawable.fontbtna);
				btnmiddlefont.setBackgroundResource(R.drawable.fontbtna);
				btnlargefont.setBackgroundResource(R.drawable.fontbtnb);
				 RelativeLayout.LayoutParams btParams3 = new RelativeLayout.LayoutParams
				 (45,45);
				 btParams3.setMargins(1074, 70, 0, 0);
				 RelativeLayout.LayoutParams btParams2 = new RelativeLayout.LayoutParams
				 (40,40);
				 btParams2.setMargins(1139, 72, 0, 0);
				 RelativeLayout.LayoutParams btParams1 = new RelativeLayout.LayoutParams
				 (40,40);
				 btParams1.setMargins(1204, 72, 0, 0);
				 btnsmallfont.setLayoutParams(btParams1);
				 btnmiddlefont.setLayoutParams(btParams2);
				 btnlargefont.setLayoutParams(btParams3);
//				adapter.showoperationcontent.setTextSize(26);
//				adapter.showoperationmethod.setTextSize(26);
//				adapter.showallotedscore.setTextSize(26);
				listView.setAdapter(adapter);
//				adapter.notifyDataSetInvalidated();
//				adapter.large();
				//adapter.notifyDataSetInvalidated();
			}
		});
		btnpreview=(Button)findViewById(R.id.btn_preview);
		btnpreview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int n=0;
			
					
				if(!listItem.get(0).containsKey("4")){
					CustomDialog customDialog=new CustomDialog(HandheldscaleActivity.this,R.style.MyDialog,new DialogListener() {
						@Override
						public void refreshActivity(Object object) {							
						
						}
					}, "请打分在预览",false);
		customDialog.show();
				}else{
					
			for(int i=0;i<listItem.size();i++){
					HashMap<String,Object> map=(HashMap<String, Object>) listItem.get(i).get("4");
					//num+=map.size();
					if(map!=null){
					for(int j=0;j<map.size();j++){
						if(listItem.get(i).containsKey("4")&& map.containsKey(String.valueOf(j))){
							n+=1;
						
						}else {
							notscoreflag=true;
						}
					}
					}
			}
					if(n!=num){
						 CustomDialog customDialog=new CustomDialog(HandheldscaleActivity.this,R.style.MyDialog,new DialogListener() {
								@Override
								public void refreshActivity(Object object) {							
								
								}
							}, "请打分在预览",false);
				customDialog.show();
					}else{
						dialog=new CustomProgressDialog(HandheldscaleActivity.this,"正在进入预览页面");
						dialog.setTitle("稍等片刻");// 设置标题
						dialog.setMessage("正在加载，请稍候");
						dialog.setIndeterminate(false);// 设置进度条是否为不明确
						dialog.setCancelable(false);// 设置进度条是否可以按退回键取消
						if(dialog!=null){
							dialog.show();
						}
						Intent intent=new Intent();
						intent.setClass(HandheldscaleActivity.this, PreviewActivity.class);
						intent.putExtra("jump", "ok");
						//intent.putExtra("name", name);
						//intent.putExtra("num", resulttablename);
						intent.putExtra("list", listItem);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						finish();
					
					
				}
				
				}
			
			}
		});
		btnback=(Button)findViewById(R.id.btn_scoreback);
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(HandheldscaleActivity.this, SeclectTableActivity.class);
				dialog=new CustomProgressDialog(HandheldscaleActivity.this,"正在返回评分表页");
				dialog.setTitle("稍等片刻");// 设置标题
				dialog.setMessage("正在加载，请稍候");
				dialog.setIndeterminate(false);// 设置进度条是否为不明确
				dialog.setCancelable(false);// 设置进度条是否可以按退回键取消
				if(dialog!=null){
					dialog.show();
				}
				intent.putExtra("back", "back");
				intent.putExtra("jump", "ok");
				//intent.putExtra("num", resulttablename);
				intent.putExtra("list", listItem);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});
		setfontsize();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent=new Intent();
		Intent intentrerurnIntent=getIntent();
//		if(intentrerurnIntent.getStringExtra("jump")!=null){
//			
//		}else{
		dialog=new CustomProgressDialog(HandheldscaleActivity.this,"正在返回评分表页");
		dialog.setTitle("稍等片刻");// 设置标题
		dialog.setMessage("正在加载，请稍候");
		dialog.setIndeterminate(false);// 设置进度条是否为不明确
		dialog.setCancelable(false);// 设置进度条是否可以按退回键取消
		if(dialog!=null){
			dialog.show();
		}
		intent.setClass(HandheldscaleActivity.this, SeclectTableActivity.class);
		intent.putExtra("back", "back");
		//intent.putExtra("num", resulttablename);
		intent.putExtra("list", listItem);
		startActivity(intent);
		HandheldscaleActivity.this.finish();
		super.onBackPressed();
		//}
	}

	private void setfont(float a){
		//txvresulttable.setTextSize(a);
		//txvresulttablename.setTextSize(a);
		//txvstudentid.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		//txvresulttable.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		txvoperationmethod.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		txvoperationcontent.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		txvallotedscore.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		txvthisscore.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		txvthiscontext.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		//txvfontsize.setTextSize(TypedValue.COMPLEX_UNIT_PT, a);
		//txvscoreoperation.setTextSize(a);
	}
	private void setfontsize(){
		Typeface face = Typeface.createFromAsset (getAssets() , "MSYH.zip" );
		txvstudentid.setTypeface(face);
		txvfontsize.setTypeface(face);
		txvresulttable.setTypeface(face);
		txvshowname.setTypeface(face);
		txvlogout.setTypeface(face);
		txvresulttablename.setTypeface(face);
		txvoperationmethod.setTypeface(face);
		txvoperationcontent.setTypeface(face);
		txvallotedscore.setTypeface(face);
		txvthisscore.setTypeface(face);
		txvshowstudentid.setTypeface(face);
		btnsmallfont.setTypeface(face);
		btnmiddlefont.setTypeface(face);
		btnlargefont.setTypeface(face);
		btnpreview.setTypeface(face);
		btnback.setTypeface(face);
		txvthiscontext.setTypeface(face);
		txvstudentexamname.setTypeface(face);
		txvstudentshowexamname.setTypeface(face);
		txvstudentexamnumname.setTypeface(face);
		txvstudentshowexamnumname.setTypeface(face);
		TextPaint tp = txvstudentid.getPaint(); 
		tp.setFakeBoldText(true);
		TextPaint tp1 = txvfontsize.getPaint(); 
		tp1.setFakeBoldText(true);
		TextPaint tp2 = txvresulttable.getPaint(); 
		tp2.setFakeBoldText(true);
		TextPaint tp3 = txvresulttablename.getPaint(); 
		tp3.setFakeBoldText(true);
		TextPaint tp4 = txvshowstudentid.getPaint(); 
		tp4.setFakeBoldText(true);
		TextPaint tp5 = txvstudentexamname.getPaint(); 
		tp5.setFakeBoldText(true);
		TextPaint tp6 = txvstudentshowexamname.getPaint(); 
		tp6.setFakeBoldText(true);
		TextPaint tp7 = txvstudentexamnumname.getPaint(); 
		tp7.setFakeBoldText(true);
		TextPaint tp8 = txvstudentshowexamnumname.getPaint(); 
		tp8.setFakeBoldText(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		  if  (event.getAction() == MotionEvent.ACTION_DOWN) {  
	            System.out.println("down" );  
//	            if  (HandheldscaleActivity. this .getCurrentFocus() !=  null ) {  
//	                if  (HandheldscaleActivity. this .getCurrentFocus().getWindowToken() !=  null ) {  
//	                	InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
//	                    imm.hideSoftInputFromWindow(HandheldscaleActivity.this .getCurrentFocus().getWindowToken(),  
//	                            InputMethodManager.HIDE_NOT_ALWAYS);  
//	                }  
//	            }  
	            InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
			 boolean isOpen=m.isActive(); 
			 if(isOpen==true){
				  System.out.println("haha" );  
				  View view = getWindow().peekDecorView();
				  SelectScoreAdapter.editText1.clearFocus();
				  m.hideSoftInputFromWindow(view.getWindowToken(), 0);
//				  m.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS); 
				 
			 }
	           
	        }  
	        return   super .onTouchEvent(event);  
	}
}
