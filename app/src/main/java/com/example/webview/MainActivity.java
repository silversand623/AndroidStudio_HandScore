package com.example.webview;


import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

import com.example.webview.LoginActivity.ResultType;
import com.example.webview.SegmentSeekBarView.onSegmentSeekBarViewClickListener;
import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.DialogListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.handscore.model.LoginInfoType;
import com.handscore.model.StudentInfo.Student;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.os.Bundle;
import android.os.Handler;
import android.R.id;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.handscore.model.StudentInfo;
import cn.king.swipelibrary.SwipeLayout;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("JavascriptInterface")
	public class MainActivity extends Activity implements OnItemClickListener {
	private ListView gv;
	private LoginInfoType loginItem;
private TextView TVExit;
private ProgressHUD mProgressHUD;
private ArrayList<HashMap<String, Object>> studentArray;
private ArrayList<HashMap<String, Object>> filterStudentArray;
private StudentAdapter adapter;
private final String Status[]=new String[] {"未定义","缺考","已考","未考"};
private SegmentView seg;
private Handler mHandler = new Handler();
	private Timer mTimer;
	private TimerTask mTimerTask;
	private  SwipeLayout wipe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GlobalSetting myApp = (GlobalSetting)getApplication();

		LoginInfoType info = myApp.gLoginItem;

		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				//checkNextStudent();
				//adapter.setList(studentArray);
				//adapter.notifyDataSetChanged();
			}
		};
		//开始一个定时任务
		mTimer.schedule(mTimerTask, 2000, 10000);

		try {
			seg=(SegmentView)findViewById(R.id.segView);
			//wipe=(SwipeLayout)findViewById(R.id.swipe);
			//wipe.setrsetr
			gv=(ListView)findViewById(R.id.LVList); 
			
			TVExit=(TextView)findViewById(R.id.TVExit); 
			//将图标图片和图标名称存入ArrayList中	
			studentArray = new ArrayList<HashMap<String, Object>>();	
			
			filterStudentArray = new ArrayList<HashMap<String, Object>>();	
			
			//SimpleAdapter对象，匹配ArrayList中的元素	
			adapter = new StudentAdapter(this, studentArray);
			

			    try
			    {
				    if (info != null) {
				    	TextView tvExamName = (TextView)findViewById(R.id.ExamName);
				    	tvExamName.setText(URLDecoder.decode(info.E_Name, "UTF-8"));
				    	TextView tvStationName = (TextView)findViewById(R.id.KaoZhan);
				    	tvStationName.setText(URLDecoder.decode(info.ES_Name, "UTF-8"));
				    	TextView RoomName = (TextView)findViewById(R.id.FangJian);
				    	RoomName.setText(URLDecoder.decode(info.Room_Name, "UTF-8"));
				    	TextView ScoreItems = (TextView)findViewById(R.id.PingFenBiaoCount);
				    	ScoreItems.setText(URLDecoder.decode(info.mark_sheet_count, "UTF-8"));
				    }
				    
				    for (HashMap<String, Object> map : myApp.gStudnetArray)
		            {
		            	String sUid = (String)map.get("U_ID");
		            	if (sUid == myApp.gStudentId)
		            	{
		            		TextView labelExamNo = (TextView)findViewById(R.id.TextXueHao);
		            		labelExamNo.setText(URLDecoder.decode((String)map.get("itemXuehao"), "UTF-8"));
		            		TextView labelName = (TextView)findViewById(R.id.TextKaoHao);
		            		labelName.setText(URLDecoder.decode((String)map.get("itemKaohao"), "UTF-8"));
		            		//TextView labelStudentNo = (TextView)findViewById(R.id.TextXingMing);
		            		//labelStudentNo.setText(URLDecoder.decode((String)map.get("itemName"), "UTF-8"));
		            		TextView labelClassName = (TextView)findViewById(R.id.TextBanji);
		            		labelClassName.setText(URLDecoder.decode((String)map.get("itemBanJi"), "UTF-8"));
		            		break;
		            	}
		            }
			    }catch (Exception e1)
			    {
			    	
			    }
			    Intent intent = getIntent(); 
				 String intentValue = intent.getStringExtra("viewIntent");
				 //如果从登录进来则重新加载数据，否则取原来的数据
				 if(intentValue!=null)
				 {			
					 if(intentValue.equals("Login"))
					 {
						 loginItem = myApp.getLoginItem();
				            mProgressHUD = ProgressHUD.show(this,"正在登录", true,true,null);
				            getStudentInfo();
			 			
					 }
	 				
				 }
				 else
				 {
					 int nIndex = myApp.gSegSelectedIndex;
		                //[[self filterSegment] setSelectedSegmentIndex:nIndex];
		                filterStudentArray = myApp.gStudnetArray;
		                studentArray = getStudentArray(nIndex);
		                adapter.setList(studentArray);
		            	adapter.notifyDataSetChanged();
		                updateSegment();
				 }          
			
			SharedPreferences userInfo = getSharedPreferences("user_info",0);
			if (!userInfo.contains("ipconfig")) {
				return;
			}
			String BaseUrl = userInfo.getString("ipconfig", null);
			String url="http://";
		    url=url+BaseUrl+"/AppDataInterface/HandScore.aspx/SearchStudentPhoto?U_ID=";
			adapter.setUrl(url);
			gv.setAdapter(adapter);
			
			gv.setOnItemClickListener(this);
			
			gv.setOnTouchListener(new View.OnTouchListener() {
	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	                Log.e("ListView","OnTouch");
	                return false;
	            }
	        });
			gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	            @Override
	            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
	                Log.e("ListView","onItemLongClick:" + position);
	                return false;
	            }
	        });
			gv.setOnScrollListener(new AbsListView.OnScrollListener() {
	            @Override
	            public void onScrollStateChanged(AbsListView view, int scrollState) {
	                Log.e("ListView","onScrollStateChanged");
	            }

	            @Override
	            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	            }
	        });

			gv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	                Log.e("ListView", "onItemSelected:" + position);
	            }

	            @Override
	            public void onNothingSelected(AdapterView<?> parent) {
	                Log.e("ListView", "onNothingSelected:");
	            }
	        });
			//已评曰，未评阅，全部
			seg.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {
	             @Override  
	             public void onSegmentViewClick(View v,int position) {
	            	 GlobalSetting myApp = (GlobalSetting)getApplication();
	            	myApp.gSegSelectedIndex = position;
	            	 studentArray = getStudentArray(position);
	            	adapter.setList(studentArray);
               	adapter.notifyDataSetChanged();
	                
	              }  
		     });
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		};
		//退出事件
		TVExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				MainActivity.this.finish();
			}
		});
		
	}

	public void checkNextStudent()
	{
		SharedPreferences userInfo = getSharedPreferences("user_info",0);
		if (!userInfo.contains("ipconfig")) {
			return;
		}
		String BaseUrl = userInfo.getString("ipconfig", null);
		String url="http://";
		url=url+BaseUrl+"/AppDataInterface/HandScore.aspx/SearchCurrentSystemDatetime";
		Ion.with(this)
				.load(url)
				.asString()
				.setCallback(new FutureCallback<String>() {
					@Override
					public void onCompleted(Exception e, String result) {
						if (e != null) {
							return;
						}
						//////
						try {

							//2015-08-24 15:38:53
							Date dateSystem = new Date();
							Date dateStart = new Date();
							Date dateEnd = new Date();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							GlobalSetting myApp = (GlobalSetting)getApplication();
							for (HashMap<String, Object> map : myApp.gStudnetArray) {
								String sUid = (String) map.get("U_ID");
								String sStartTime = result.substring(0, 5) + map.get("itemTime");
								String sEndTime = result.substring(0, 5) + map.get("itemEndTime");
								try {
									dateSystem = format.parse(result);
									dateStart = format1.parse(sStartTime);
									dateEnd = format1.parse(sEndTime);
									long lInterval1 = dateSystem.getTime() - dateStart.getTime();
									long lInterval2 = dateEnd.getTime() - dateSystem.getTime();
									if (lInterval1 >= 0.0 && lInterval2 >= 0.0) {

									} else if (lInterval1 < 0.0) {
										SharedPreferences userInfo = getSharedPreferences("user_info",0);
										userInfo.edit()
												.putString("U_ID",sUid)
												.commit();
										break;
									} else if (lInterval2 < 0.0) {

									}
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						} catch (Exception eJson) {
							////
						}
						/////

					}
				});
	}

	private void updateSegment() {
	    int nHaveScore = 0;
	    int nAll = 0;
	    GlobalSetting myApp = (GlobalSetting)getApplication();
	    nAll = myApp.gStudnetArray.size();
	    for (int i = 0;i<myApp.gStudnetArray.size();i++)
        {
        	HashMap<String, Object> map = myApp.gStudnetArray.get(i);
            String sState = (String)map.get("itemZhuangtai");
            if (sState == Status[2]) {
            	nHaveScore++;
            }
        }
	    String strLeft = String.format("未评分(%d人)", nAll-nHaveScore);
	    String strCenter = String.format("已评分(%d人)", nHaveScore);
	    String strRight = String.format("全部(%d人)", nAll);
	    seg.setSegmentText(strLeft,0);
	    seg.setSegmentText(strCenter,1);
	    seg.setSegmentText(strRight,2);
	}
	
	private boolean isArrayNotNull(ArrayList<HashMap<String, Object>> list)
	{
		boolean bRet = false;
		if (list != null)
		{
			if (list.size() >0)
			{
				bRet = true;
			}
		}
		return bRet;
	}
	
	private ArrayList<HashMap<String, Object>> getStudentArray(int nIndex)
	{
		ArrayList<HashMap<String, Object>> filter = new ArrayList<HashMap<String, Object>>();
	    
	    switch (nIndex) {
	        case 0:
	        {
	            for (int i = 0;i<filterStudentArray.size();i++)
	            {
	            	HashMap<String, Object> map = filterStudentArray.get(i);
	                String sState = (String)map.get("itemZhuangtai");
	                if (sState != Status[2]) {
	                    filter.add(map);
	                }
	            }
	            break;
	        }
	        case 1:
	        {
	        	for (int i = 0;i<filterStudentArray.size();i++)
	            {
	            	HashMap<String, Object> map = filterStudentArray.get(i);
	                String sState = (String)map.get("itemZhuangtai");
	                if (sState == Status[2]) {
	                    filter.add(map);
	                }
	            }
	            break;
	        }
	        default:
	        {
	            filter = filterStudentArray;
	            break;
	        }
	    }
	    return filter;
	}
	
	private void getStudentInfo(){
		SharedPreferences userInfo = getSharedPreferences("user_info",0);
		if (!userInfo.contains("ipconfig")) {
			return;
		}
		String BaseUrl = userInfo.getString("ipconfig", null);
		String url="http://";
	    url=url+BaseUrl+"/AppDataInterface/HandScore.aspx/SearchStudentInfo";
	    Ion.with(this)
        .load(url)
        .setBodyParameter("E_ID", loginItem.E_ID)
        .setBodyParameter("ES_ID", loginItem.ES_ID)
        .setBodyParameter("Room_ID", loginItem.Room_ID) 
        .setBodyParameter("U_ID", loginItem.U_ID) 
        .setBodyParameter("search_type","1") 
        .setBodyParameter("search_keyword","") 
        .setBodyParameter("page_index","1") 
        .setBodyParameter("page_size","1000") 
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                mProgressHUD.dismiss();
            	if (e != null) {
                    return;
                }
                //////
                try {
                	Gson gson =new Gson();
                	java.lang.reflect.Type type = new TypeToken<StudentInfo>() {}.getType();
                	StudentInfo studentInfos=gson.fromJson(result,type);
                	GlobalSetting myApp = (GlobalSetting)getApplication();  
                    myApp.gStudents = studentInfos; 
                    
                	for(Student obj :studentInfos.student_list)
                	{
                		HashMap<String, Object> map = new HashMap<String, Object>();
        				map.put("itemImage",R.drawable.username);
        				map.put("itemName", URLDecoder.decode(obj.U_TrueName, "UTF-8"));
        				map.put("itemTime", obj.Exam_StartTime);
        				map.put("itemKaohao",URLDecoder.decode(obj.EStu_ExamNumber, "UTF-8"));
        				map.put("itemXuehao", URLDecoder.decode(obj.U_Name, "UTF-8"));
        				map.put("itemBanJi", URLDecoder.decode(obj.O_Name, "UTF-8"));
        				map.put("itemZhuangtai", Status[Integer.parseInt(obj.student_state)]);
        				map.put("itemFenshu",obj.student_score);
        				map.put("U_ID", obj.U_ID);
        				map.put("itemEndTime", obj.Exam_EndTime);
        				filterStudentArray.add(map);
                	} 
                	
                	myApp.setStudentList(filterStudentArray);
                	studentArray = getStudentArray(0);
                	adapter.setList(studentArray);
                	adapter.notifyDataSetChanged();
                	updateSegment();
	            }
                catch (Exception eJson) {
                	////
                }
                /////
                
            }
        });
	}
	
	
	public void getSystemTime(final HashMap<String, String> map)
	{
		SharedPreferences userInfo = getSharedPreferences("user_info",0);
		if (!userInfo.contains("ipconfig")) {
			return;
		}
		String BaseUrl = userInfo.getString("ipconfig", null);
		String url="http://";
	    url=url+BaseUrl+"/AppDataInterface/HandScore.aspx/SearchCurrentSystemDatetime";
	    Ion.with(this)
        .load(url)
        .asString()
        .setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                if (e != null) {
                    return;
                }
                //////
                try {
                	
                	//2015-08-24 15:38:53
                	Date dateSystem=new Date();  
                    Date dateStart=new Date();
                    Date dateEnd=new Date();
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String sStartTime = result.substring(0,5)+ map.get("itemTime");
                    String sEndTime = result.substring(0,5) + map.get("itemEndTime");
                    try {                  
                    	dateSystem=format.parse(result);  
                    	dateStart=format1.parse(sStartTime);
                    	dateEnd = format1.parse(sEndTime);
                    	long lInterval1 = dateSystem.getTime() - dateStart.getTime();
                    	long lInterval2 = dateEnd.getTime()-dateSystem.getTime();

						SharedPreferences userInfo = getSharedPreferences("user_info",0);
						if (!userInfo.contains("timeinterval")) {
							return;
						}
						String sTime = userInfo.getString("timeinterval", null);
						float fTime = Float.valueOf(sTime);
						final Intent intent =new Intent(MainActivity.this,ScoreActivity.class);
						Bundle bundle = new Bundle();
						bundle.putFloat("remainTime",lInterval2-fTime*60*1000);
						intent.putExtra("bundle",bundle);

						if (lInterval1 >=0.0 && lInterval2 >=0.0)
                        {

                        	MainActivity.this.startActivity(intent);
                        } else if (lInterval1 < 0.0)
                        {
                        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); 
           				 builder.setMessage("当前学生还没有开始考试，请确认是否继续评分？");
           				 builder.setTitle("提示");
           					builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {							
           						public void onClick(DialogInterface dialog, int which) 
           						{
           							MainActivity.this.startActivity(intent);
           						 }
           						});
           					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {					  
           					 public void onClick(DialogInterface dialog, int which) {
           						 dialog.dismiss();
           					 }
           					});
           					builder.create().show();
                        } else if (lInterval2 < 0.0)
                        {
                        	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); 
              				 builder.setMessage("当前学生考试时间已过,请确认是否继续评分？");
              				 builder.setTitle("提示");
              					builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {							
              						public void onClick(DialogInterface dialog, int which) 
              						{
              							MainActivity.this.startActivity(intent);
              						 }
              						});
              					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {					  
              					 public void onClick(DialogInterface dialog, int which) {
              						 dialog.dismiss();
              					 }
              					});
              					builder.create().show();
                        }
                    } catch (ParseException e1) {  
                        // TODO Auto-generated catch block  
                        e1.printStackTrace();  
                    }
	            }
                catch (Exception eJson) {
                	////
                }
                /////
                
            }
        });
	}
	    
	@Override
	 public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	 {
		ListView listView = (ListView)parent;  
	    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
	    String userid = map.get("U_ID");
	    String sState = map.get("itemZhuangtai");
	    GlobalSetting myApp = (GlobalSetting)getApplication();  
        myApp.gStudentId = userid;
        if (sState == Status[2])
        {
        	Intent intent =new Intent(this,ScoreViewSeeActivity.class); 
			this.startActivity(intent);
        } else
        {
        	getSystemTime(map);
			
        }
	 } 
	
	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 
}
