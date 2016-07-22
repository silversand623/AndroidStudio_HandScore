package com.example.webview;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.DialogInterface.OnCancelListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.CustomProgressDialog;
import com.example.webview.tools.DialogListener;
import com.example.webview.tools.ResizeLayout;
import com.example.webview.tools.ResizeLayout.OnResizeListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.handscore.model.LoginInfoType;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class LoginActivity extends Activity implements OnResizeListener,OnCancelListener {

	private Button loginbutton, selectexambtn;
	// private CheckBox saveInfoCheckBox;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private TextView TVSetIp;
	private ImageView usernameimg, passwordimg;
	Boolean flag;
	private TextView mTView, mTViewName;
	private List<String> nameList = new ArrayList<String>();
	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	Timer tExit = new Timer();
	SharedPreferences userInfo;
	ArrayList<String> listexam;
	ArrayList<String> listexamid;
	private int examid;
	public static CustomProgressDialog pDialog;
	private ProgressHUD mProgressHUD;
	private LinearLayout LoginHidPanel;
	
	public class ResultType {
	    //以下是枚举成员
		public static final int LoginTypeError = -3;
		public static final int PwdError = -2;
		public static final int UserError = -1;
		public static final int NoExam = 0;
		public static final int Success = 1;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginbutton = (Button) findViewById(R.id.btn_login);
		selectexambtn = (Button) findViewById(R.id.btn_selectexam);
		usernameimg = (ImageView) findViewById(R.id.usernameimg);
		passwordimg = (ImageView) findViewById(R.id.passwordimg);
		
		usernameEditText = (EditText) findViewById(R.id.edt_username);
		passwordEditText = (EditText) findViewById(R.id.edt_password);
		//设置IP地址
		TVSetIp = (TextView) findViewById(R.id.tv_setIP);

		passwordEditText.setInputType(0x81);
		ResizeLayout relativeLayout = (ResizeLayout) findViewById(R.id.relative1);
		relativeLayout.setOnResizeListener(this);
		
		//LoginHidPanel=(LinearLayout) findViewById(R.id.LoginHidPanel);
		
		setupViews();
		//setfont();
		userInfo = getSharedPreferences("user_info", 0);
		if (userInfo.contains("username")) {
			usernameEditText.setText(userInfo.getString("username", ""));
			
		} 
		
		passwordEditText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							passwordEditText.setFocusable(false);
							passwordEditText.setFocusableInTouchMode(false);

						}
						return false;
					}
				});
		
		passwordEditText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				passwordEditText.setFocusableInTouchMode(true); // 设置模式,以便获取焦点
				passwordEditText.requestFocus();
				return false;
			}
		});
		
		usernameEditText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							usernameEditText.setFocusable(false);
							usernameEditText.setFocusableInTouchMode(false);

						}
						return false;
					}
				});
		
		usernameEditText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub			
				usernameEditText.setFocusableInTouchMode(true); // 设置模式,以便获取焦点
				usernameEditText.requestFocus();
				return false;
			}
		});		

	
		final OnCancelListener cancel = this;
		
		loginbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences userInfo = getSharedPreferences("user_info",0);
				///////temp
				
				//userInfo.edit()
				//.putString("ipconfig","192.168.4.130:8080")
				//.commit();
				//////
				if (usernameEditText.getText().toString().equals("")) {
					dealError(10);
				} else if (!usernameEditText.getText().toString().equals("")
						&& passwordEditText.getText().toString().equals("")) {
					dealError(9);
				} else if (!userInfo.contains("ipconfig")) {
					dealError(11);
				}
				
				else {
					
					mProgressHUD = ProgressHUD.show(arg0.getContext(),"正在登陆", true,true,cancel);		
					userInfo.edit()
							.putString("username",usernameEditText.getText().toString())
							.commit();
					
					getExamInfo();
					
				}
			}
		});
		TVSetIp.setOnClickListener(new View.OnClickListener() {
			//点击后修改ip设置
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				OpenIPDialog();
			}
		});
		
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		mProgressHUD.dismiss();
	}
	
	private void getExamInfo(){
		SharedPreferences userInfo = getSharedPreferences("user_info",0);
		if (!userInfo.contains("ipconfig")) {
			return;
		}
		String BaseUrl = userInfo.getString("ipconfig", null);
		String url="http://";
	    url=url+BaseUrl+"/AppDataInterface/UserLogin.aspx/HandScoreUserLogin";
	    Ion.with(this)
        .load(url)
        .setBodyParameter("U_Name", usernameEditText.getText().toString())
        .setBodyParameter("U_PWD", passwordEditText.getText().toString())     
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                mProgressHUD.dismiss();
            	if (e != null) {
            		if(result==null)
            		{
            			AlertMessage("网络连接错误。");
            		}
                }
                //////
                try {
	                Gson gson =new Gson();
	                LoginInfoType info = gson.fromJson(result.toString(), LoginInfoType.class);
	                if (info != null){
	                	int nCase = Integer.parseInt(info.result);
	                	dealError(nCase);
	                	if (nCase == ResultType.Success) {
	                		////////
	                		GlobalSetting myApp = (GlobalSetting)getApplication();  
	                        myApp.setLoginItem(info); 
	                		//startActivity(new Intent(LoginActivity.this,MainActivity.class));	                		
	                		Intent intent = new Intent();   
	        				intent.setClass(LoginActivity.this, MainActivity.class);	        				
	        				intent.putExtra("viewIntent", "Login");
	        				startActivity(intent);
	                	}
	                
	                }
	            }
                catch (Exception eJson) {                	
                }
                /////
                
            }
        });
	}
	
	private void dealError(int nCase) {
		String sMessage="";
	    switch (nCase) {
	        case ResultType.UserError:
	        {
	            sMessage = "用户名输入错误";
	        	break;
	        }
	        case ResultType.PwdError:
	        {
	        	sMessage = "密码输入错误";
	            break;
	        }
	        case ResultType.NoExam:
	        {
	        	sMessage = "当前时间没有考试";
	            break;
	        }
	        case ResultType.Success:
	        {
	            break;
	        }
	        case ResultType.LoginTypeError:
	        {
	        	sMessage = "远程评委不允许登录手持设备";
	            break;
	        }
	        case 9:
	        {
	        	sMessage = "请输入密码";
	        	break;
	        }
	        case 10:
	        {
	        	sMessage = "请输入用户名";
	        	break;
	        }
	        case 11:
	        {
	        	sMessage = "请设置IP地址";
	        	break;
	        }
	        default:
	        {
	        	sMessage = "其他内部错误";
	            break;
	        }
	    }
	    if (nCase != ResultType.Success){
	    	AlertMessage(sMessage);
	    }
	}	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		passwordEditText.setText("");
		Intent intent = getIntent();
		if (intent.getStringExtra("login") == null) {

		} else {
			usernameimg.setVisibility(View.VISIBLE);
			passwordimg.setVisibility(View.VISIBLE);
			usernameEditText.setVisibility(View.VISIBLE);
			passwordEditText.setVisibility(View.VISIBLE);
			loginbutton.setVisibility(View.VISIBLE);
			
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(LoginActivity.this, "再次点击返回键退出应用程序！",
						Toast.LENGTH_LONG).show();
				if (!hasTask) {
					tExit.schedule(task, 2000);
				}
			} else {
				finish();
				System.exit(0);
			}

		}
		return false;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {
			OpenIPDialog();
		}
		return super.onOptionsItemSelected(item);
	}
//弹出设置ip框
	public void OpenIPDialog()
	{
		SharedPreferences userInfo = getSharedPreferences("user_info", 0);
		final EditText inputServer = new EditText(this);
		if (userInfo.contains("ipconfig")) {
			inputServer.setText(userInfo.getString("ipconfig", ""));
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请输入ip地址")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(inputServer).setNegativeButton("取消", null);
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Pattern pattern = Pattern
								.compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2}:\\d{0,5}$)");
						Matcher matcher = pattern.matcher(inputServer
								.getText().toString()); // 以验证127.400.600.2为例
						if (matcher.find()) {
							Toast.makeText(getApplicationContext(), "设置成功",
									Toast.LENGTH_LONG);
							SharedPreferences userInfo = getSharedPreferences(
									"user_info", 0);

							userInfo.edit()
									.putString(
											"ipconfig",
											inputServer.getText()
													.toString()).commit();
						} else {
							Toast.makeText(getApplicationContext(), "设置失败",
									Toast.LENGTH_LONG);
						}

					}
				});
		builder.show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "设置地址");
		return true;
	}

	@Override
	public void OnResize(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		if (h >= oldh) {
			usernameEditText.setFocusable(false);
			usernameEditText.setFocusableInTouchMode(false);
			passwordEditText.setFocusable(false);
			passwordEditText.setFocusableInTouchMode(false);
		}
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	private void showmessage() {
		// pd.show();
		Message message = new Message();
		message.what = 0;
		handler1.sendMessageDelayed(message, 1000);

	}

	
	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			// getexam("");
			// dopost("");

			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, SeclectTableActivity.class);
			userInfo.edit().putString("examname", nameList.get(0).toString())
					.commit();
			userInfo.edit().putString("examid", String.valueOf(examid))
					.commit();
			startActivity(intent);
			LoginActivity.this.finish();
			
		}
	};
	
	private void setupViews() {

		mTView = (TextView) findViewById(R.id.tv_value);
		mTViewName = (TextView) findViewById(R.id.tv_valuename);
		mTView.setVisibility(View.INVISIBLE);
		mTViewName.setVisibility(View.INVISIBLE);
		// mBtnDropDown = (ImageButton) findViewById(R.id.bt_dropdown);
		// mBtnDropDown.setOnClickListener(this);
		// mBtnDropDown.setVisibility(View.INVISIBLE);

	}

	
	private void setfont() {
		Typeface face = Typeface.createFromAsset(getAssets(), "MSYH.zip");
		usernameEditText.setTypeface(face);
		passwordEditText.setTypeface(face);
		mTView.setTypeface(face);
		mTViewName.setTypeface(face);
		// progresstext.setTypeface(face);
		loginbutton.setTypeface(face);
		selectexambtn.setTypeface(face);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("down");
			if (LoginActivity.this.getCurrentFocus() != null) {
				if (LoginActivity.this.getCurrentFocus().getWindowToken() != null) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(LoginActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					usernameEditText.setFocusable(false);
					usernameEditText.setFocusableInTouchMode(false);
					passwordEditText.setFocusable(false);
					passwordEditText.setFocusableInTouchMode(false);
				}
			}
		}
		return super.onTouchEvent(event);
	}
	private void AlertMessage(String strMsg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); 
		builder.setTitle("提示").setMessage(strMsg) 
	      .setPositiveButton("确定", 
	                    new DialogInterface.OnClickListener(){ 
	                               public void onClick(DialogInterface dialoginterface, int i){ 
	                                    //按钮事件 
	                                 } 
	                         }) 
	        .show();	        	
	}
}
