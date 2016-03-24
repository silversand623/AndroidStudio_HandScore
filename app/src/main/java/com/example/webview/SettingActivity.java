package com.example.webview;

import java.util.HashMap;

import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.DialogListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends Activity {
	SegmentSet segmentSet;
	EditText eTextInterval;
	
	 private TextView TVBack;
	    private TextView TVsubmit; 
	    int modelValueStr;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		segmentSet=(SegmentSet)findViewById(R.id.SegmentSetBar);
		eTextInterval=(EditText)findViewById(R.id.eTextInterval);
		
		 TVBack=(TextView)findViewById(R.id.TVBack);
		 TVsubmit=(TextView)findViewById(R.id.TVsubmit);
		 
		 SharedPreferences userInfo = getSharedPreferences("user_info",0);		
		 //填充数据，如果有已保存的数据
		 if (userInfo.contains("progressStep")) {
			 String aa=userInfo.getString("progressStep",null);
			 eTextInterval.setText(userInfo.getString("progressStep",null));
			}
		 if (userInfo.contains("modelValue")) {
			 String tempValue=userInfo.getString("modelValue",null);
			 modelValueStr=Integer.parseInt(tempValue);
			 if(tempValue.equals("0"))
			 {
				 segmentSet.getSegLeftText().setSelected(true);
				 segmentSet.getSegRightText().setSelected(false);
			 }
			 else
			 {
				 segmentSet.getSegLeftText().setSelected(false);
				 segmentSet.getSegRightText().setSelected(true);
			 }
			 
			}
		//设置显示文字
		segmentSet.setSegmentText("打分制",0);
		segmentSet.setSegmentText("扣分制",1);
		segmentSet.setOnSegmentViewClickListener(new SegmentSet.onSegmentViewClickListener() {
			
			@Override
			public void onSegmentViewClick(View v, int position) {
				// TODO 自动生成的方法存根
				//在此可以获得用户点击的数据
				modelValueStr=position;
				
			}
		} );
		eTextInterval.addTextChangedListener(textWatcher);		
	
		//返回按钮
    	TVBack.setOnClickListener(new View.OnClickListener(){
			   @Override  
	             public void onClick(View v) {
				   //返回时关闭本界面				  
				    Intent intent = new Intent();   
	   				intent.setClass(SettingActivity.this, ScoreActivity.class);
	   				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   			
	   				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	   				startActivity(intent);
	              }  
		});
		//保存设置数据
		TVsubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根	
				// 此处为失去焦点时的处理内容
				String AllNumber=eTextInterval.getText().toString();
	        	if(AllNumber.length()>0)
	        	{
	        		if(Float.parseFloat(AllNumber)==0)
	        		{
	        			DialogAlert("评分间隔不能为0。");
	        			eTextInterval.setText("1");
	        			return;
	        		}
	        		else
	        		{
	        			eTextInterval.setText(String.valueOf(Float.parseFloat(AllNumber)));
	        		}	        		
	        		
	        	}
	        	else
	        	{
	        		//如果为空则默认1
	        		DialogAlert("评分间隔不能为空。");
		     			eTextInterval.setText("1");
		     			return;
	        	}
	        	//判断如果没有修改就不提示，是否确认修改
	        	String flag="false";
	        	 SharedPreferences userInfo = getSharedPreferences("user_info",0);		
	    		 //填充数据，如果有已保存的数据
	    		 if (userInfo.contains("progressStep")) {
	    			 String aa=userInfo.getString("progressStep",null);
	    			 String bb=eTextInterval.getText().toString();
	    			 if(!aa.equals(bb))
	    			 {
	    				 flag="true";
	    			 }	    			
	    			}else
	    			{
	    				flag="true";
	    			}
	    		 if (userInfo.contains("modelValue")) {
	    			 String tempValue=userInfo.getString("modelValue",null);
	    			 if(!tempValue.equals(String.valueOf(modelValueStr)))
	    			 {
	    				 flag="true";
	    			 }
	    			}
	    		 else
	    		 {
	    			 flag="true";
	    		 }
	        	//提示是否保存设置,判断如果有修改的数据则保存，否则直接跳到登录界面
	    		 if(flag.equals("true"))
	    		 {
	        	 AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this); 
				 builder.setMessage("是否确认修改参数？");
				 builder.setTitle("提示");
					builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {							
						public void onClick(DialogInterface dialog, int which) 
						{
							SharedPreferences userInfo = getSharedPreferences("user_info",0);
							userInfo.edit()
							.putString("progressStep",eTextInterval.getText().toString())
							.putString("modelValue",String.valueOf(modelValueStr))
							.commit();
							
							//跳转到打分界面
			     			Intent intent = new Intent();   
				   			intent.setClass(SettingActivity.this, ScoreActivity.class);
				   			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   			
				   			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				   			startActivity(intent);
						 }
						});
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {					  
					 public void onClick(DialogInterface dialog, int which) {
						 dialog.dismiss();
					 }
					});
					builder.create().show(); 
	    		 }
	    		 else
	    		 {
	    			//跳转到打分界面
		     			Intent intent = new Intent();   
			   			intent.setClass(SettingActivity.this, ScoreActivity.class);
			   			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   			
			   			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			   			startActivity(intent);
	    		 }
/*		 
				 CustomDialog customDialog = new CustomDialog(SettingActivity.this,
	     					R.style.MyDialog, new DialogListener() {
	     						@Override
	     						public void refreshActivity(Object object) {
	     	
	     						}
	     					}, "参数设置成功。", false);
	     			customDialog.show();*/
	     			
			}
		});

	}
	/*@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		System.out.println(event.getKeyCode());
		return true;
	}*/
	private TextWatcher textWatcher = new TextWatcher() {  
		 
		@Override  
		public void onTextChanged(CharSequence s, int start, int before,int count) {		   
			
		}
		@Override  
		public void beforeTextChanged(CharSequence s, int start, int count,int after)
		{	
			
		} 
		
		@Override  
		public void afterTextChanged(Editable s) {  
		   
			String AllNumber=s.toString();
        	if(AllNumber.length()>0)
        	{
        		if(AllNumber.indexOf(".")!=-1)
        		{
        			String dotNumber=AllNumber.substring(AllNumber.indexOf("."));
		        	if(dotNumber.length()>2)
		        	{
		        		DialogAlert("只能精确到一位小数。");		        		     			
		     			//截取小数点后一位赋值给文本框
			        	eTextInterval.setText(AllNumber.substring(0, AllNumber.indexOf(".")+2));
		        	}		        	    	
        		}
        		
        	}
			
		} 
	};
	public void DialogAlert(String Message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this); 
		builder.setTitle("提示").setMessage(Message) 
	      .setPositiveButton("确定", 
	                    new DialogInterface.OnClickListener(){ 
	                               public void onClick(DialogInterface dialoginterface, int i){ 
	                                    //按钮事件 
	                                 } 
	                         }) 
	        .show();	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

}
