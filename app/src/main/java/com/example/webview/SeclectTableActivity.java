package com.example.webview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.InputType;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanner.camera.CameraManager;
import com.example.scanner.decoding.CaptureActivityHandler;
import com.example.scanner.decoding.InactivityTimer;
import com.example.scanner.view.ViewfinderView;
import com.example.webview.R.id;
import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.CustomProgressDialog;
import com.example.webview.tools.DialogListener;
import com.example.webview.tools.MySlipSwitch;
import com.example.webview.tools.MySlipSwitch.OnSwitchListener;
import com.example.webview.tools.NetTool;
import com.example.webview.tools.ResizeLayout.OnResizeListener;
import com.example.webview.tools.SelectTableAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class SeclectTableActivity extends Activity implements OnResizeListener,
		Callback {
	private AutoCompleteTextView studentnumEditText;
	private com.example.webview.tools.MySlipSwitch slipswitch_MSL;
	// private Button btnsearchexam;
	private ProgressDialog pd;
	private ImageView imgstudent;
	private TextView txvshowtablename, txvshowtableclass, txvtableclass,
			txvtablename, txvselecttable, txvshowname, txvlogout,
			showtableexamnum, showtablestudentname, tableexamnum,
			tablestudentname, showtableexamname, showtableexamclassname,
			tableexamname, tableexamclassname, showtableroomnum, tableroomnum,
			textView_showtableroomnum, textViewtitlestudent,
			textViewtitlescanner;
	private ListView listView;
	private boolean clickflag = false;
	SelectTableAdapter adapter;
	View itemView;
	private ArrayList<HashMap<String, Object>> listItem;
	private Button btnnextstepButton, btnloginback;
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private boolean isTrue = true;
	private boolean isTrue1 = true;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	SharedPreferences userInfo;
	public static boolean selecttableflag = false;
	private RelativeLayout relativeLayout;
	private RelativeLayout relativeLayoutall;
	Bitmap bitmap;
	String[] str;
	private List<String> studentlist = new ArrayList<String>();
	private List<String> tableitem = new ArrayList<String>();
	private List<String> tablescore = new ArrayList<String>();
	private List<String> tablelist = new ArrayList<String>();
	private List<String> tablenum = new ArrayList<String>();
	// private List<String> tablescore = new ArrayList<String>();
	private TestThread mTestThread;
	CustomProgressDialog pDialog;
	TextView tView1;
	ArrayList<HashMap<String, Object>> tableItem;
	public static CustomProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selecttable);
		LoginActivity.pDialog.dismiss();
		if (HandheldscaleActivity.dialog != null) {
			HandheldscaleActivity.dialog.dismiss();
		}
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setVisibility(View.INVISIBLE);
		hasSurface = false;
		relativeLayout = (RelativeLayout) findViewById(R.id.relativescanner);
		relativeLayoutall = (RelativeLayout) findViewById(R.id.relativeall);
		userInfo = getSharedPreferences("user_info", 0);
		inactivityTimer = new InactivityTimer(this);
		slipswitch_MSL = (MySlipSwitch) findViewById(R.id.main_myslipswitch);
		slipswitch_MSL.setImageResource(R.drawable.bkg_switch,
				R.drawable.bkg_switch, R.drawable.btn_slip);
		slipswitch_MSL.setSwitchState(false);
		relativeLayout.setVisibility(View.INVISIBLE);
		// pd = new ProgressDialog(SeclectTableActivity.this);
		// pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		// pd.setTitle("稍等片刻");// 设置标题
		// pd.setMessage("正在加载，请稍候");
		// pd.setIndeterminate(false);// 设置进度条是否为不明确
		// pd.setCancelable(false);// 设置进度条是否可以按退回键取消
		// pd.show();
		pDialog = new CustomProgressDialog(this, "正在获取数据");
		pDialog.setTitle("稍等片刻");// 设置标题
		pDialog.setMessage("正在加载，请稍候");
		pDialog.setIndeterminate(false);// 设置进度条是否为不明确
		pDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
		if (pDialog != null) {
			pDialog.show();
		}
		newThread.start();

		// btnscanner=(Button)findViewById(R.id.btn_tablescanner);
		// btnscanner.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent=new Intent();
		// intent.setClass(SeclectTableActivity.this, CaptureActivity.class);
		// startActivity(intent);
		// }
		// });
		// listView.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// if(event.getAction() == MotionEvent.ACTION_DOWN){
		// listView.setBackgroundColor(Color.BLUE);
		// }
		// // else if(event.getAction() == MotionEvent.ACTION_UP){
		// // lv.setBackgroundColor(Color.BLACK);
		// // Intent intent =new Intent(Activity1.this,Activity2.class);
		// // startActivity(intent);
		// // }
		// return false;
		// }
		// });
		// btnsearchexam.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// if(examnumEditText.getText().toString().equals("")){
		// CustomDialog customDialog=new
		// CustomDialog(SeclectTableActivity.this,new DialogListener() {
		// @Override
		// public void refreshActivity(Object object) {
		//
		// }
		// }, "请输入考站号",false);
		// customDialog.show();
		// }else{
		// pd.show();
		// newThread.start();
		//
		// //ArrayList list = getexam("");
		//
		// if(pd.isShowing()==true){
		// //自定义layout组件;
		//
		// }
		//
		//
		// //
		// }
		//
		// }
		// });

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(SeclectTableActivity.this, LoginActivity.class);
		intent.putExtra("login", "login");
		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	@SuppressWarnings("rawtypes")
	// private ArrayList getexam(String url){
	// String result="";
	// URL surl=null;
	// ArrayList list=new ArrayList();
	// try{
	// // surl=new URL(url);
	// // HttpURLConnection urlConn=(HttpURLConnection)surl.openConnection();
	// // urlConn.setDoInput(true);
	// // urlConn.setDoOutput(true);
	// // urlConn.setRequestMethod("POST");
	// // urlConn.setUseCaches(false);
	// // urlConn.setRequestProperty("Content-Type",
	// "application/x-www-form-urlencoded");
	// // urlConn.setRequestProperty("Charset", "utf-8");
	// // urlConn.connect();
	// // BufferedReader bufferedReader=new BufferedReader(new
	// InputStreamReader(urlConn.getInputStream()));
	// // String readLine=null;
	// // while ((readLine=bufferedReader.readLine())!=null) {
	// // result+=readLine;
	// // }
	// // bufferedReader.close();
	// // urlConn.disconnect();
	// // JSONArray jsonArray = null;
	// // jsonArray = new JSONArray(result);
	//
	// list.add(0, "评分表1");
	// list.add(1, "评分表2");
	// list.add(2, "评分表3");
	// list.add(3, "评分表4");
	// list.add(4, "评分表5");
	// list.add(5, "评分表6");
	// // for (int i = 0; i < jsonArray.length(); i++) {
	// // JSONObject jsonObject = jsonArray.getJSONObject(i);
	// // // 初始化map数组对象
	// // list.add(i,jsonObject.getString("examname"));
	// // }
	// pd.dismiss();
	//
	// }catch (Exception e) {
	// e.printStackTrace();
	// }
	// return list;
	// }
	@Override
	public void OnResize(int w, int h, int oldw, int oldh) {
		if (h >= oldh) {
			studentnumEditText.setFocusable(false);
			studentnumEditText.setFocusableInTouchMode(false);
		}

	}

	private void showmessage() {
		// pd.show();
		Message message = new Message();
		message.what = 0;
		handler1.sendMessageDelayed(message, 1000);

	}

	// Thread newThread = new Thread(new Runnable() {
	// @Override
	// public void run() {
	// // SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	// // String string=df.format(new Date());// new Date()为获取当前系统时间
	// //getexam("");
	// try {
	// Thread.sleep(1000);
	// //getexam("");
	// } catch (InterruptedException e) {
	//
	// e.printStackTrace();
	// }
	// Message message = new Message();
	// message.what=0;
	// handler.sendMessage(message);
	// }
	// });
	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {

			pDialog.dismiss();
			createFile(bitmap);
			imgstudent.setImageBitmap(bitmap);
			imgstudent.setVisibility(View.VISIBLE);
			studentnumEditText.setText(userInfo.getString("Exam_Student_Code",
					""));
			// imgstudent.setImageResource(R.drawable.studentimg);
			txvshowtablename.setVisibility(View.VISIBLE);
			txvshowtableclass.setVisibility(View.VISIBLE);
			txvtablename.setText(studentnumEditText.getText().toString());
			txvtableclass.setText(userInfo.getString("O_Name", ""));
			showtableexamnum.setVisibility(View.VISIBLE);
			showtablestudentname.setVisibility(View.VISIBLE);
			tableexamnum.setText(userInfo.getString("U_StudentCode", ""));
			tablestudentname.setText(userInfo.getString("U_TrueName", ""));
			showtableexamname.setVisibility(View.VISIBLE);
			showtableexamclassname.setVisibility(View.VISIBLE);
			tableexamname.setText(userInfo.getString("examname", ""));
			tableexamclassname.setText(userInfo.getString("ES_NAME", ""));
			showtableroomnum.setVisibility(View.VISIBLE);
			tableroomnum.setText(userInfo.getString("Room_Name", ""));
			studentnumEditText.setFocusable(false);
			studentnumEditText.setFocusableInTouchMode(false);

			// relativeLayout.setVisibility(View.VISIBLE);
		}
	};
	Handler handler3 = new Handler() {
		public void handleMessage(Message msg) {
			imgstudent.setVisibility(View.VISIBLE);
			// imgstudent.setImageResource(R.drawable.studentimg);
			txvshowtablename.setVisibility(View.VISIBLE);
			txvshowtableclass.setVisibility(View.VISIBLE);
			txvtablename.setText(studentnumEditText.getText().toString());
			txvtableclass.setText(userInfo.getString("O_Name", ""));
			showtableexamnum.setVisibility(View.VISIBLE);
			showtablestudentname.setVisibility(View.VISIBLE);
			tableexamnum.setText(userInfo.getString("U_StudentCode", ""));
			tablestudentname.setText(userInfo.getString("U_TrueName", ""));
			showtableexamname.setVisibility(View.VISIBLE);
			showtableexamclassname.setVisibility(View.VISIBLE);
			tableexamname.setText(userInfo.getString("examname", ""));
			tableexamclassname.setText(userInfo.getString("ES_NAME", ""));
			showtableroomnum.setVisibility(View.VISIBLE);
			tableroomnum.setText(userInfo.getString("Room_Name", ""));
			studentnumEditText.setFocusable(false);
			studentnumEditText.setFocusableInTouchMode(false);
			// relativeLayout.setVisibility(View.VISIBLE);
		}
	};
	Handler handler5 = new Handler() {
		public void handleMessage(Message msg) {
			pDialog.dismiss();
			CustomDialog customDialog = new CustomDialog(
					SeclectTableActivity.this, R.style.MyDialog,
					new DialogListener() {
						@Override
						public void refreshActivity(Object object) {
							// Intent intent=new Intent();
							// intent.setClass(SeclectTableActivity.this,
							// LoginActivity.class);
							// intent.putExtra("login", "login");
							// startActivity(intent);
							// SeclectTableActivity.this.finish();
							setlayout();
						}
					}, "网络连接错误", false);
			customDialog.show();
			// relativeLayout.setVisibility(View.VISIBLE);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		pDialog.dismiss();
		pDialog = null;
		if (dialog != null) {
			dialog.dismiss();
		}
		// CameraManager.get().closeDriver();
		// dialog.dismiss();
		super.onDestroy();
	}

	/**
	 * 处理扫描结果
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(final Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			// Toast.makeText(MipcaActivityCapture.this, "Scan failed!",
			// Toast.LENGTH_SHORT).show();
		} else {
			relativeLayout.setVisibility(View.INVISIBLE);
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			if (barcode == null) {
			} else {

				// Drawable drawable = new BitmapDrawable(barcode);
				dialog.setIcon(R.drawable.company_icon);
			}
			dialog.setTitle("扫描结果");
			dialog.setMessage("扫描到的学号为：" + result.getText() + ",返回到评分页");
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							studentnumEditText.setText(result.getText());
							finish();
							Intent intent = new Intent();
							intent.setClass(SeclectTableActivity.this,
									SeclectTableActivity.class);
							intent.putExtra("activityback", result.getText());
							// intent.putExtra("table",
							// txvselecttable.getText().toString());
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish();
							// String result=dopost("192.168.4.30:8001");
							// 用默认浏览器打开扫描得到的地址
							// Intent intent = new Intent();
							// intent.setClass(SeclectTableActivity.this,
							// MainActivity.class);
							// //
							// intent.setAction("android.intent.action.VIEW");
							// // Uri content_url = Uri.parse(result.getText());
							// // intent.setData(content_url);
							// startActivity(intent);
							// finish();
						}
					});
			dialog.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// finish();
							finish();
							Intent intent = new Intent();
							intent.setClass(SeclectTableActivity.this,
									SeclectTableActivity.class);
							intent.putExtra("activityback", "");
							// intent.putExtra("table",
							// txvselecttable.getText().toString());
							startActivity(intent);
							// relativeLayout.setVisibility(View.VISIBLE);
						}
					});
			dialog.create().show();
			// inactivityTimer.onActivity();
			// playBeepSoundAndVibrate();
			// String resultString = result.getText();
			// if (resultString.equals("")) {
			// // Toast.makeText(MipcaActivityCapture.this, "Scan failed!",
			// Toast.LENGTH_SHORT).show();
			// }else {
			// AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			// if (barcode == null)
			// {
			// dialog.setIcon(null);
			// }
			// else
			// {
			//
			// Drawable drawable = new BitmapDrawable(barcode);
			// dialog.setIcon(drawable);
			// }
			// dialog.setTitle("扫描结果");
			// dialog.setMessage(result.getText());
			// dialog.setNegativeButton("确定", new
			// DialogInterface.OnClickListener()
			// {
			// @Override
			// public void onClick(DialogInterface dialog, int which)
			// {
			// //用默认浏览器打开扫描得到的地址
			// Intent intent = new Intent();
			// intent.setAction("android.intent.action.VIEW");
			// Uri content_url = Uri.parse(result.getText());
			// intent.setData(content_url);
			// startActivity(intent);
			// finish();
			// }
			// });
			// dialog.setPositiveButton("取消", new
			// DialogInterface.OnClickListener()
			// {
			// @Override
			// public void onClick(DialogInterface dialog, int which)
			// {
			// finish();
			// }
			// });
			// dialog.create().show();
		}
		// MipcaActivityCapture.this.finish();
	}

	//
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			// vibrator.vibrate(VIBRATE_DURATION);
			long[] pattern = { 100, 10, 100, 1000 }; // OFF/ON/OFF/ON...

			vibrator.vibrate(pattern, -1);
		}
	}

	//
	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	// Handler handler=new Handler(){
	// public void handleMessage(Message msg) {
	// // pd.dismiss();
	// DisplayMetrics dm = new DisplayMetrics();
	// getWindowManager().getDefaultDisplay().getMetrics(dm);
	// int width = dm.widthPixels;
	// int height = dm.heightPixels;
	// RelativeLayout layout = (RelativeLayout)findViewById(R.id.relativetable);
	// final ArrayList tableArrayList=getexam("");
	// int i1=tableArrayList.size();
	// Button Btn[] = new Button[i1];
	// int j = -1;
	// for (int i=0; i<i1; i++) {
	// Btn[i]=new Button(SeclectTableActivity.this);
	// Btn[i].setId(2000+i);
	// Btn[i].setText(tableArrayList.get(i).toString());
	// RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams
	// ((layout.getWidth()-400),(layout.getHeight()-50)/i1);
	// btParams.setMargins(300, 0, 300, 0);
	//
	// btParams.leftMargin = 300; //横坐标定位
	// btParams.topMargin = 20+((layout.getHeight()-50)/i1)*i; //纵坐标定位
	// layout.addView(Btn[i],btParams); //将按钮放入layout组件
	// }
	// for ( int k = 0; k <= Btn.length-1; k++) {
	// //这里不需要findId，因为创建的时候已经确定哪个按钮对应哪个Id
	// Btn[k].setTag(k); //为按钮设置一个标记，来确认是按下了哪一个按钮
	// Btn[k].setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// int i = (Integer) v.getTag();
	// Intent intent=getIntent();
	// String name=intent.getStringExtra("name");
	// intent.setClass(SeclectTableActivity.this, HandheldscaleActivity.class);
	// // intent.putExtra("name", name);
	// SharedPreferences userInfo = getSharedPreferences("user_info", 0);
	// userInfo.edit()
	// .putString("tablenum",
	// tableArrayList.get(i).toString())
	// .commit();
	// // intent.putExtra("num", tableArrayList.get(i).toString());
	// startActivity(intent);
	// }
	// });
	// }
	//
	// super.handleMessage(msg);
	// }
	// };
	private void setfont() {
		Typeface face = Typeface.createFromAsset(getAssets(), "MSYH.zip");
		txvshowtablename.setTypeface(face);
		txvshowtableclass.setTypeface(face);
		txvtableclass.setTypeface(face);
		txvtablename.setTypeface(face);
		txvshowname.setTypeface(face);
		txvlogout.setTypeface(face);
		showtableexamnum.setTypeface(face);
		showtablestudentname.setTypeface(face);
		tableexamnum.setTypeface(face);
		tablestudentname.setTypeface(face);
		showtableexamname.setTypeface(face);
		showtableexamclassname.setTypeface(face);
		tableexamname.setTypeface(face);
		tableexamclassname.setTypeface(face);
		btnnextstepButton.setTypeface(face);
		btnloginback.setTypeface(face);
		showtableroomnum.setTypeface(face);
		tableroomnum.setTypeface(face);

		textViewtitlescanner.setTypeface(face);
		textViewtitlestudent.setTypeface(face);
		TextPaint tp = textViewtitlescanner.getPaint();
		tp.setFakeBoldText(true);
		TextPaint tp1 = textViewtitlestudent.getPaint();
		tp1.setFakeBoldText(true);
		TextPaint tp2 = txvshowtablename.getPaint();
		tp2.setFakeBoldText(true);
		TextPaint tp3 = txvshowtableclass.getPaint();
		tp3.setFakeBoldText(true);
		TextPaint tp4 = txvtableclass.getPaint();
		tp4.setFakeBoldText(true);
		TextPaint tp5 = txvtablename.getPaint();
		tp5.setFakeBoldText(true);
		TextPaint tp6 = showtableexamnum.getPaint();
		tp6.setFakeBoldText(true);
		TextPaint tp7 = showtablestudentname.getPaint();
		tp7.setFakeBoldText(true);
		TextPaint tp8 = tableexamnum.getPaint();
		tp8.setFakeBoldText(true);
		TextPaint tp9 = tablestudentname.getPaint();
		tp9.setFakeBoldText(true);
		TextPaint tp10 = showtableexamname.getPaint();
		tp10.setFakeBoldText(true);
		TextPaint tp11 = showtableexamclassname.getPaint();
		tp11.setFakeBoldText(true);
		TextPaint tp12 = tableexamname.getPaint();
		tp12.setFakeBoldText(true);
		TextPaint tp13 = tableexamclassname.getPaint();
		tp13.setFakeBoldText(true);
		TextPaint tp14 = showtableroomnum.getPaint();
		tp14.setFakeBoldText(true);
		TextPaint tp15 = tableroomnum.getPaint();
		tp15.setFakeBoldText(true);
		txvshowtablename.setTypeface(face);
		txvshowtableclass.setTypeface(face);
		txvtableclass.setTypeface(face);
		txvtablename.setTypeface(face);
		showtableexamnum.setTypeface(face);
		showtablestudentname.setTypeface(face);
		tableexamnum.setTypeface(face);
		tablestudentname.setTypeface(face);
		showtableexamname.setTypeface(face);
		showtableexamclassname.setTypeface(face);
		tableexamname.setTypeface(face);
		tableexamclassname.setTypeface(face);
		btnnextstepButton.setTypeface(face);
		btnloginback.setTypeface(face);
		showtableroomnum.setTypeface(face);
		tableroomnum.setTypeface(face);
	}

	Thread newThread = new Thread(new Runnable() {
		@SuppressWarnings("null")
		@Override
		public void run() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("E_ID", userInfo.getString("", "examid"));
			// 服务器请求路径
			String urlPath = "http://"
					+ userInfo.getString("ipconfig", "")
					+ "/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchExamInfo";
			InputStream is = null;

			try {
				is = NetTool.getInputStreamByPost(urlPath, map, "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (is == null) {
				Message message = new Message();
				message.what = 0;
				handler5.sendMessageDelayed(message, 0);
				isTrue = false;
			} else {

				byte[] data = null;

				try {
					data = NetTool.readStream(is);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message message = new Message();
					message.what = 0;
					handler5.sendMessageDelayed(message, 0);
				}

				String result = new String(data);
				JSONObject jsonObject = null;
				Log.i("OPPOOOOOOOOOOOOOOOOOOOOOOO", result.toString());
				try {
					jsonObject = new JSONObject(result.toString());
					userInfo.edit()
							.putString(
									"ES_NAME",
									URLDecoder.decode(jsonObject
											.getString("ES_Name"))).commit();
					userInfo.edit()
							.putString(
									"Room_Name",
									URLDecoder.decode(jsonObject
											.getString("Room_Name"))).commit();
					JSONObject jsonObject1 = new JSONObject(result.toString());
					JSONArray jsonArray = jsonObject1
							.getJSONArray("ExamStudentCodeList");
					JSONArray jsonArray1 = jsonObject1
							.getJSONArray("MarkSheetList");
					for (int i = 0; i < jsonArray.length(); i++) {
						// 初始化map数组对象
						studentlist.add(jsonArray.getString(i));
					}
					listItem = new ArrayList<HashMap<String, Object>>();

					for (int i = 0; i < jsonArray1.length(); i++) {
						// 初始化map数组对象
						JSONObject jsonObject2 = jsonArray1.getJSONObject(i);

						JSONArray jsonArray2 = jsonObject2
								.getJSONArray("MarkSheetItemList");
						tableItem = new ArrayList<HashMap<String, Object>>();
						for (int j = 0; j < jsonArray2.length(); j++) {
							JSONObject jsonObject3 = jsonArray2
									.getJSONObject(j);
							JSONArray jsonArray3 = jsonObject3
									.getJSONArray("MSI_Item");
							JSONArray jsonArray4 = jsonObject3
									.getJSONArray("MSI_Score");
							HashMap<String, Object> mapcontentlist = new HashMap<String, Object>();
							mapcontentlist.put("0", URLDecoder
									.decode(jsonObject3.getString("MSI_ID")));
							mapcontentlist.put("1", URLDecoder
									.decode(jsonObject3
											.getString("MSI_ItemName")));
							List<String> tableitem = new ArrayList<String>();
							for (int m = 0; m < jsonArray3.length(); m++) {
								// 初始化map数组对象
								tableitem.add(URLDecoder.decode(jsonArray3
										.getString(m)));
							}
							List<String> tablescore = new ArrayList<String>();
							for (int n = 0; n < jsonArray4.length(); n++) {
								// 初始化map数组对象
								tablescore.add(URLDecoder.decode(jsonArray4
										.getString(n)));
							}
							mapcontentlist.put("2", tableitem);
							mapcontentlist.put("3", tablescore);
							tableItem.add(mapcontentlist);
						}
						HashMap<String, Object> maplist = new HashMap<String, Object>();
						;
						maplist.put("0", jsonObject2.getString("MS_ID"));
						maplist.put("1", URLDecoder.decode(jsonObject2
								.getString("MS_Name")));
						maplist.put("2", tableItem);
						listItem.add(maplist);
						userInfo.edit()
						.putString("tablenum",
								listItem.get(0).get("1").toString())
						.commit();
					}
					Map<String, String> map1 = new HashMap<String, String>();
					if (!userInfo.contains("studenttruenum")) {
						map1.put("Exam_Student_Code", studentlist.get(0)
								.toString());
					} else {
						int a=Integer.parseInt(userInfo.getString("studenttruenum", ""));
						map1.put("Exam_Student_Code", studentlist.get(a)
								.toString());
					}
					// 服务器请求路径
					String urlPath1 = "http://"
							+ userInfo.getString("ipconfig", "")
							+ "/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentInfo";
					InputStream is1 = null;

					is1 = NetTool.getInputStreamByPost(urlPath1, map1, "UTF-8");

					byte[] data1 = null;

					data1 = NetTool.readStream(is1);

					String result1 = new String(data1);
					JSONObject jsonObject3 = null;

					jsonObject3 = new JSONObject(result1.toString());
					userInfo.edit()
							.putString(
									"U_TrueName",
									URLDecoder.decode(jsonObject3
											.getString("U_TrueName"))).commit();
					userInfo.edit()
							.putString("U_StudentCode",
									jsonObject3.getString("U_StudentCode"))
							.commit();
					userInfo.edit()
							.putString(
									"O_Name",
									URLDecoder.decode(jsonObject3
											.getString("O_Name"))).commit();
					bitmap = getHttpBitmap("http://"
							+ userInfo.getString("ipconfig", "")
							+ "/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentPhoto?Exam_Student_Code="
							+ studentlist.get(0).toString());

					Message message = new Message();
					message.what = 0;
					handler2.sendMessageDelayed(message, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	});

	private void setlayout() {
		studentnumEditText = (AutoCompleteTextView) findViewById(R.id.edt_examnum);
		imgstudent = (ImageView) findViewById(R.id.imageview_tableimg);
		txvshowtablename = (TextView) findViewById(R.id.textView_showtablename);
		txvshowtableclass = (TextView) findViewById(R.id.textView_showtableclass);
		txvtablename = (TextView) findViewById(R.id.textView_tablename);
		txvtableclass = (TextView) findViewById(R.id.textView_tableclass);
		// txvselecttable = (TextView) findViewById(R.id.textViewselecttable);
		txvlogout = (TextView) findViewById(R.id.txvlogout);
		txvshowname = (TextView) findViewById(R.id.txvshowspname);

		// listView = (ListView) findViewById(R.id.mylist1);
		// listView.setItemsCanFocus(true);
		btnnextstepButton = (Button) findViewById(R.id.btn_nextstep);
		txvshowname.setText("欢迎，" + userInfo.getString("truename", ""));
		TextPaint tp = txvshowname.getPaint();
		tp.setFakeBoldText(true);
		TextPaint tp1 = studentnumEditText.getPaint();
		tp1.setFakeBoldText(true);
		showtableexamnum = (TextView) findViewById(R.id.textView_showtableexamnum);
		showtablestudentname = (TextView) findViewById(R.id.textView_showtablestudentname);
		tableexamnum = (TextView) findViewById(R.id.textView_tableexamnum);
		tablestudentname = (TextView) findViewById(R.id.textView_tablestudentname);
		showtableexamname = (TextView) findViewById(R.id.textView_showtableexamname);
		showtableexamclassname = (TextView) findViewById(R.id.textView_showtableexamclassnum);
		tableexamname = (TextView) findViewById(R.id.textView_tableexamname);
		tableexamclassname = (TextView) findViewById(R.id.textView_tableexamclassnum);
		showtableroomnum = (TextView) findViewById(R.id.textView_showtableroomnum);
		tableroomnum = (TextView) findViewById(R.id.textView_tableroomnum);
		textView_showtableroomnum = (TextView) findViewById(R.id.textView_showtableroomnum);
		textViewtitlestudent = (TextView) findViewById(R.id.textViewtitlestudent);
		textViewtitlescanner = (TextView) findViewById(R.id.textViewtitlescanner);
		// if (userInfo.contains("tablenum")) {
		// txvselecttable.setText(userInfo.getString("tablenum", ""));
		// }
		txvlogout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		txvlogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SeclectTableActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		// btnsearchexam=(Button)findViewById(R.id.btn_tablesumbit);
		studentnumEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				studentnumEditText.showDropDown();

			}
		});
		studentnumEditText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {

							// pd = new
							// ProgressDialog(SeclectTableActivity.this);
							// pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);//
							// 设置风格为圆形进度条
							// pd.setTitle("稍等片刻");// 设置标题
							// pd.setMessage("正在加载，请稍候");
							// pd.setIndeterminate(false);// 设置进度条是否为不明确
							// pd.setCancelable(true);// 设置进度条是否可以按退回键取消
							// pd.show();
							pDialog = new CustomProgressDialog(
									SeclectTableActivity.this, "正在获取考生信息");
							pDialog.setTitle("稍等片刻");// 设置标题
							pDialog.setMessage("正在加载，请稍候");
							pDialog.setIndeterminate(false);// 设置进度条是否为不明确
							pDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
							pDialog.show();
							userInfo.edit()
									.putString(
											"Exam_Student_Code",
											studentnumEditText.getText()
													.toString()).commit();

							if (newThread2 == null) {
								isTrue1 = true;
								newThread2.start();
							} else {
								if (isTrue == false) {
									isTrue = true;
									mTestThread = new TestThread();
									mTestThread.start();
								} else {
									mTestThread = new TestThread();
									mTestThread.start();
								}

							}

							// showmessage();
						} else if (actionId == EditorInfo.IME_ACTION_NEXT) {
							studentnumEditText.setFocusable(false);
							studentnumEditText.setFocusableInTouchMode(false);
							// pd = new
							// ProgressDialog(SeclectTableActivity.this);
							// pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);//
							// 设置风格为圆形进度条
							// pd.setTitle("稍等片刻");// 设置标题
							// pd.setMessage("正在加载，请稍候");
							// pd.setIndeterminate(false);// 设置进度条是否为不明确
							// pd.setCancelable(true);// 设置进度条是否可以按退回键取消
							// pd.show();
							pDialog = new CustomProgressDialog(
									SeclectTableActivity.this, "正在获取考生信息");
							pDialog.setTitle("稍等片刻");// 设置标题
							pDialog.setMessage("正在加载，请稍候");
							pDialog.setIndeterminate(false);// 设置进度条是否为不明确
							pDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
							pDialog.show();
							userInfo.edit()
									.putString(
											"Exam_Student_Code",
											studentnumEditText.getText()
													.toString()).commit();
							if (newThread2 == null) {
								isTrue1 = true;
								newThread2.start();
							} else {
								if (isTrue == false) {
									isTrue = true;
									mTestThread = new TestThread();
									mTestThread.start();
								} else {
									mTestThread = new TestThread();
									mTestThread.start();
								}
							}
							// showmessage();
						}
						return false;
					}
				});
		studentnumEditText
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						txvtablename.setText(arg0.getItemAtPosition(arg2)
								.toString());
						// pd = new ProgressDialog(SeclectTableActivity.this);
						// pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);//
						// 设置风格为圆形进度条
						// pd.setTitle("稍等片刻");// 设置标题
						// pd.setMessage("正在加载，请稍候");
						// pd.setIndeterminate(false);// 设置进度条是否为不明确
						// pd.setCancelable(true);// 设置进度条是否可以按退回键取消
						// pd.show();
						pDialog = new CustomProgressDialog(
								SeclectTableActivity.this, "正在获取考生信息");
						pDialog.setTitle("稍等片刻");// 设置标题
						pDialog.setMessage("正在加载，请稍候");
						pDialog.setIndeterminate(false);// 设置进度条是否为不明确
						pDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
						pDialog.show();
						userInfo.edit()
								.putString("Exam_Student_Code",
										arg0.getItemAtPosition(arg2).toString())
								.commit();
						if (newThread1 == null) {

							newThread1.start();
							isTrue = true;
						} else {
							if (isTrue == false) {
								isTrue = true;
								mTestThread = new TestThread();
								mTestThread.start();
							} else {
								mTestThread = new TestThread();
								mTestThread.start();
							}

						}
					}
				});
		// String[] str = { "0810440101", "0810440201", "0810440301",
		// "0810440202", "0810440301", "0810440401", "0810440501",
		// "0810440601" };
		ArrayAdapter<String> textadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, studentlist);
		studentnumEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		studentnumEditText.setAdapter(textadapter);
		studentnumEditText.setThreshold(0);
		studentnumEditText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				studentnumEditText.setFocusableInTouchMode(true); // 设置模式,以便获取焦点
				studentnumEditText.requestFocus();
				return false;
			}
		});
		relativeLayout.setVisibility(View.INVISIBLE);
		slipswitch_MSL.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if (isSwitchOn) {
					viewfinderView.setVisibility(View.VISIBLE);
					relativeLayout.setVisibility(View.VISIBLE);

					// createCamera();
					// Toast.makeText(MainActivity.this, "寮�叧宸茬粡寮�惎",
					// 300).show();
				} else {
					relativeLayout.setVisibility(View.INVISIBLE);
					// Toast.makeText(MainActivity.this, "寮�叧宸茬粡鍏抽棴",
					// 300).show();
				}
			}
		});
		// listItem = new ArrayList<HashMap<String, Object>>();
		//
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("0", "评分表1");
		// map.put("1", "用物：注射药物");// 图像资源的ID
		// map.put("2", "用物：准备3分钟");
		// map.put("3",
		// "着装整洁，洗手，戴口罩查对医嘱，夹无菌巾于治疗盘中核对治疗单与药液检查药液病人病情，合作程度及注射部位状况向病人讲解操作目的及注意事项人病情");
		// map.put("4", "病人病情，合作程度及注射部位状况");
		// map.put("5",
		// "着装整洁，洗手，戴口罩查对医嘱，夹无菌巾于治疗盘中核对治疗单与药液检查药液病人病情，合作程度及注射部位状况向病人讲解操作目的及注意事项人病情");
		// map.put("6", "查对医嘱，夹无菌巾于治疗盘中");
		// map.put("7", "核对治疗单与药液检查药液");
		// map.put("8", "着装整洁,洗手，戴口罩查对医嘱，夹无菌巾于治疗盘中核对治疗单与药液检查药液");
		// map.put("9",
		// "着装整洁，洗手，戴口罩查对医嘱，夹无菌巾于治疗盘中核对治疗单与药液检查药液病人病情，合作程度及注射部位状况向病人讲解操");
		// map.put("10",
		// "着装整洁，洗手，戴口罩查对医嘱，夹无菌巾于治疗盘中核对治疗单与药液检查药液病人病情，合作程度及注射部位状况向病人讲");
		// HashMap<String, Object> map1 = new HashMap<String, Object>();
		// map1.put("0", "评分表2");
		// map1.put("1", "步骤1");// 图像资源的ID
		// map1.put("2", "步骤2");
		// map1.put("3", "步骤3");
		// map1.put("4", "步骤4");
		// map1.put("5", "步骤5");
		// map1.put("6", "步骤6");
		// map1.put("7", "步骤7");
		// map1.put("8", "步骤8");
		// HashMap<String, Object> map2 = new HashMap<String, Object>();
		// map2.put("0", "评分表3");
		// map2.put("1", "步骤1");// 图像资源的ID
		// map2.put("2", "步骤2");
		// map2.put("3", "步骤3");
		// map2.put("4", "步骤4");
		// map2.put("5", "步骤5");
		// map2.put("6", "步骤6");
		// map2.put("7", "步骤7");
		// map2.put("8", "步骤8");
		// HashMap<String, Object> map3 = new HashMap<String, Object>();
		// map3.put("0", "评分表4");
		// map3.put("1", "步骤1");// 图像资源的ID
		// map3.put("2", "步骤2");
		// map3.put("3", "步骤3");
		// map3.put("4", "步骤4");
		// map3.put("5", "步骤5");
		// map3.put("6", "步骤6");
		// map3.put("7", "步骤7");
		// map3.put("8", "步骤8");
		// listItem.add(map);
		// listItem.add(map1);
		// listItem.add(map2);
		// listItem.add(map3);
		Typeface face = Typeface.createFromAsset(getAssets(), "MSYH.zip");

		// adapter = new SelectTableAdapter(SeclectTableActivity.this,
		// listItem,face);
		// listView.setAdapter(adapter);
		Intent intent = getIntent();
		if (intent.getStringExtra("clear") == null
				&& intent.getStringExtra("back") == null
				&& intent.getStringExtra("activityback") == null) {
			// txvselecttable.setText("");
			imgstudent.setVisibility(View.INVISIBLE);
			txvshowtablename.setVisibility(View.INVISIBLE);
			txvshowtableclass.setVisibility(View.INVISIBLE);
			txvtablename.setText("");
			txvtableclass.setText("");
			tableexamnum.setText("");
			tablestudentname.setText("");
			showtableexamnum.setVisibility(View.INVISIBLE);
			showtablestudentname.setVisibility(View.INVISIBLE);
			showtableexamname.setVisibility(View.INVISIBLE);
			showtableexamclassname.setVisibility(View.INVISIBLE);
			tableexamname.setText("");
			tableexamclassname.setText("");
		} else if (intent.getStringExtra("back") == null
				&& intent.getStringExtra("activityback") == null
				&& intent.getStringExtra("clear").equals("clear")) {
			// if (userInfo.contains("tablenum")) {
			// txvselecttable.setText(userInfo.getString("tablenum", ""));
			// }
			studentnumEditText.setText("");
			imgstudent.setVisibility(View.INVISIBLE);
			txvshowtablename.setVisibility(View.INVISIBLE);
			txvshowtableclass.setVisibility(View.INVISIBLE);
			txvtablename.setText("");
			txvtableclass.setText("");
			tableexamnum.setText("");
			tablestudentname.setText("");
			showtableexamnum.setVisibility(View.INVISIBLE);
			showtablestudentname.setVisibility(View.INVISIBLE);
			showtableexamname.setVisibility(View.INVISIBLE);
			showtableexamclassname.setVisibility(View.INVISIBLE);
			tableexamname.setText("");
			tableexamclassname.setText("");
			// adapter.setSelectItem(Integer.parseInt(userInfo.getString("num",
			// "")));
		} else if (intent.getStringExtra("activityback") == null
				&& intent.getStringExtra("back").equals("back")) {
			// if (userInfo.contains("tablenum")) {
			// txvselecttable.setText(userInfo.getString("tablenum", ""));
			// }
			Log.i("hahahahaha", "++++++++++++++++++++++++++++++");
			studentnumEditText.setText(userInfo.getString("studentnum", ""));
			studentnumEditText.setText(userInfo.getString("studentnum", ""));
			imgstudent.setVisibility(View.VISIBLE);
			imgstudent.setImageResource(R.drawable.studentimg);
			txvshowtablename.setVisibility(View.VISIBLE);
			txvshowtableclass.setVisibility(View.VISIBLE);
			txvtablename.setText(userInfo.getString("studentnum", ""));
			txvtableclass.setText(userInfo.getString("O_Name", ""));
			showtableexamnum.setVisibility(View.VISIBLE);
			showtablestudentname.setVisibility(View.VISIBLE);
			tableexamnum.setText(userInfo.getString("U_StudentCode", ""));
			tablestudentname.setText(userInfo.getString("U_TrueName", ""));
			showtableexamclassname.setVisibility(View.VISIBLE);
			tableexamname.setText(userInfo.getString("examname", ""));
			tableexamclassname.setText(userInfo.getString("ES_NAME", ""));
			showtableexamnum.setVisibility(View.VISIBLE);
			showtablestudentname.setVisibility(View.VISIBLE);
			showtableexamname.setVisibility(View.VISIBLE);
			tableexamname.setText(userInfo.getString("examname", ""));
			showtableroomnum.setVisibility(View.VISIBLE);
			tableroomnum.setText(userInfo.getString("Room_Name", ""));
			// adapter.setSelectItem(Integer.parseInt(userInfo.getString("num",
			// "")));
		} else {
			Log.i("hahahahaha", "____________________________");
			if (intent.getStringExtra("activityback").equals("")) {
				viewfinderView.setVisibility(View.VISIBLE);
				relativeLayout.setVisibility(View.VISIBLE);
				// adapter.setSelectItem(Integer.parseInt(userInfo.getString("num",
				// "")));
			} else {
				Log.i("hahahahaha", "____________________________222");
				// showmessage();
				// pd = new ProgressDialog(SeclectTableActivity.this);
				// pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);//
				// 设置风格为圆形进度条
				// pd.setTitle("稍等片刻");// 设置标题
				// pd.setMessage("正在加载，请稍候");
				// pd.setIndeterminate(false);// 设置进度条是否为不明确
				// pd.setCancelable(true);// 设置进度条是否可以按退回键取消
				// pd.show();
				viewfinderView.setVisibility(View.VISIBLE);
				relativeLayout.setVisibility(View.VISIBLE);
				pDialog = new CustomProgressDialog(SeclectTableActivity.this,
						"正在加载信息");
				pDialog.setTitle("稍等片刻");// 设置标题
				pDialog.setMessage("正在加载，请稍候");
				pDialog.setIndeterminate(false);// 设置进度条是否为不明确
				pDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
				pDialog.show();
				userInfo.edit()
						.putString("Exam_Student_Code",
								intent.getStringExtra("activityback")).commit();
				if (newThread2 == null) {
					isTrue1 = true;
					newThread2.start();
				} else {
					if (isTrue == false) {
						isTrue = true;
						mTestThread = new TestThread();
						mTestThread.start();
					} else {
						mTestThread = new TestThread();
						mTestThread.start();
					}
				}
				// studentnumEditText.setText(intent.getStringExtra("activityback"));
				// studentnumEditText.setText(userInfo.getString("studentnum",
				// ""));
				// imgstudent.setVisibility(View.VISIBLE);
				// imgstudent.setImageResource(R.drawable.studentimg);
				// txvshowtablename.setVisibility(View.VISIBLE);
				// txvshowtableclass.setVisibility(View.VISIBLE);
				// txvtablename.setText(intent.getStringExtra("activityback"));
				// tableexamnum.setText(userInfo.getString("U_StudentCode",
				// ""));
				// tablestudentname.setText(userInfo.getString("U_TrueName",
				// ""));
				// txvtableclass.setText(userInfo.getString("O_Name", ""));
				// showtableexamnum.setVisibility(View.VISIBLE);
				// showtablestudentname.setVisibility(View.VISIBLE);
				// showtableexamname.setVisibility(View.VISIBLE);
				// showtableexamclassname.setVisibility(View.VISIBLE);
				// tableexamname.setText(userInfo.getString("examname",""));
				// tableexamclassname.setText(userInfo.getString("ES_NAME",
				// ""));
				// showtableroomnum.setVisibility(View.VISIBLE);
				// tableroomnum.setText(userInfo.getString("Room_Name", ""));
				// if(intent.getStringExtra("table").equals("")){
				// txvselecttable.setText("");
				// }else{
				// if (userInfo.contains("tablenum")) {
				// txvselecttable.setText(userInfo.getString("tablenum", ""));
				// }
				// adapter.setSelectItem(Integer.parseInt(userInfo.getString("num",
				// "")));
				// }
			}
			// adapter.setSelectItem(Integer.parseInt(userInfo.getString("num",
			// "")));
		}
		// if (userInfo.contains("tablenum") && userInfo.contains("num")) {
		// listView.setSelection(Integer.parseInt(userInfo
		// .getString("num", "")));
		// }

		// listView.setOnItemClickListener(new OnItemClickListener() {

		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// if (itemView == null) {
		//
		// itemView = arg1;
		//
		// }
		//
		// itemView.setBackgroundResource(R.color.transparent);
		//
		// // 将上次点击的listitem的背景色设置成透明
		//
		// arg1.setBackgroundResource(R.color.blue);
		//
		// // 设置当前点击的listitem的背景色
		//
		// itemView = arg1;
		// adapter.setSelectItem(arg2);
		// adapter.notifyDataSetInvalidated();
		// txvselecttable.setText(listItem.get(arg2).get("0").toString());

		// txvselecttable.setText(listItem.get(arg2).get("0").toString());

		// userInfo.edit()
		// .putString("tablenum",
		// listItem.get(arg2).get("1").toString())
		// .commit();
		// userInfo.edit().putString("num", String.valueOf(arg2)).commit();
		// }

		// arg1.setBackgroundResource(R.color.blue);

		// arg1.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// if(event.getAction() == MotionEvent.ACTION_DOWN){
		// v.setBackgroundColor(Color.BLUE);
		// }
		// return true;
		// }
		// });

		// });
		btnnextstepButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (studentnumEditText.getText().toString().equals("")) {
					CustomDialog customDialog = new CustomDialog(
							SeclectTableActivity.this, R.style.MyDialog,
							new DialogListener() {
								@Override
								public void refreshActivity(Object object) {

								}
							}, "请输入学生学号", false);
					customDialog.show();
					// } else if
					// (!studentnumEditText.getText().toString().equals("")&&selecttableflag==false
					// ) {
					// CustomDialog customDialog = new CustomDialog(
					// SeclectTableActivity.this, R.style.MyDialog,new
					// DialogListener() {
					// @Override
					// public void refreshActivity(Object object) {
					//
					// }
					// }, "请选择评分表", false);
					// customDialog.show();
				} else {
					userInfo.edit()
							.putString("studentnum",
									studentnumEditText.getText().toString())
							.commit();
					userInfo.edit()
							.putString("tablenum",
									listItem.get(0).get("1").toString())
							.commit();
					Intent intentrerurnIntent = getIntent();
					Intent intent = new Intent();
					intent.setClass(SeclectTableActivity.this,
							HandheldscaleActivity.class);
					if (intentrerurnIntent.getSerializableExtra("list") != null) {
						// ArrayList<HashMap<String, Object>>
						// listItem=(ArrayList<HashMap<String, Object>>)
						// intentrerurnIntent.getSerializableExtra("list");
						intent.putExtra(
								"returnlist",
								(ArrayList<HashMap<String, Object>>) intentrerurnIntent
										.getSerializableExtra("list"));
						intent.putExtra("jump", "ok");
					} else {
						intent.putExtra("returnlist",
								(ArrayList<HashMap<String, Object>>) tableItem);
						intent.putExtra("jump", "ok");
					}
					dialog = new CustomProgressDialog(
							SeclectTableActivity.this, "正在进入打分页面");
					dialog.setTitle("稍等片刻");// 设置标题
					dialog.setMessage("正在加载，请稍候");
					dialog.setIndeterminate(false);// 设置进度条是否为不明确
					dialog.setCancelable(false);// 设置进度条是否可以按退回键取消
					if (dialog != null) {
						dialog.show();
					}
					startActivity(intent);
					SeclectTableActivity.this.finish();
				}
			}
		});
		btnloginback = (Button) findViewById(R.id.btn_loginback);
		btnloginback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SeclectTableActivity.this, LoginActivity.class);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("login", "login");
				startActivity(intent);
				SeclectTableActivity.this.finish();
			}
		});
		setfont();
		settablelayout();
	}

	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			if (pDialog != null) {
				pDialog.dismiss();
			}

			setlayout();
			createFile(bitmap);
			imgstudent.setImageBitmap(bitmap);
			imgstudent.setVisibility(View.VISIBLE);
			if (!userInfo.contains("studenttruenum")) {
				studentnumEditText.setText(studentlist.get(0)
						.toString());
			} else {
				int a=Integer.parseInt(userInfo.getString("studenttruenum", ""));
				studentnumEditText.setText(studentlist.get(a)
						.toString());
			}
			//studentnumEditText.setText(studentlist.get(0).toString());
			// imgstudent.setImageResource(R.drawable.studentimg);
			txvshowtablename.setVisibility(View.VISIBLE);
			txvshowtableclass.setVisibility(View.VISIBLE);
			txvtablename.setText(studentnumEditText.getText().toString());
			txvtableclass.setText(userInfo.getString("O_Name", ""));
			showtableexamnum.setVisibility(View.VISIBLE);
			showtablestudentname.setVisibility(View.VISIBLE);
			tableexamnum.setText(userInfo.getString("U_StudentCode", ""));
			tablestudentname.setText(userInfo.getString("U_TrueName", ""));
			showtableexamname.setVisibility(View.VISIBLE);
			showtableexamclassname.setVisibility(View.VISIBLE);
			tableexamname.setText(userInfo.getString("examname", ""));
			tableexamclassname.setText(userInfo.getString("ES_NAME", ""));
			showtableroomnum.setVisibility(View.VISIBLE);
			tableroomnum.setText(userInfo.getString("Room_Name", ""));
			studentnumEditText.setFocusable(false);
			studentnumEditText.setFocusableInTouchMode(false);
		}
	};
	Handler handler4 = new Handler() {
		public void handleMessage(Message msg) {
			if (pDialog != null) {
				pDialog.dismiss();
			}
			CustomDialog customDialog = new CustomDialog(
					SeclectTableActivity.this, R.style.MyDialog,
					new DialogListener() {
						@Override
						public void refreshActivity(Object object) {
							Intent intent = new Intent();
							intent.setClass(SeclectTableActivity.this,
									LoginActivity.class);
							// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("login", "login");
							startActivity(intent);
							SeclectTableActivity.this.finish();
						}
					}, "数据获取失败", false);
			customDialog.show();

		}
	};
	Thread newThread1 = new Thread(new Runnable() {
		@Override
		public void run() {
			while (isTrue) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Exam_Student_Code",
						userInfo.getString("Exam_Student_Code", ""));
				// 服务器请求路径
				String urlPath = "http://192.168.4.30:8002/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentInfo";
				InputStream is = null;

				try {
					is = NetTool.getInputStreamByPost(urlPath, map, "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				byte[] data = null;

				try {
					data = NetTool.readStream(is);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String result = new String(data);
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(result.toString());
					userInfo.edit()
							.putString(
									"U_TrueName",
									URLDecoder.decode(jsonObject
											.getString("U_TrueName"))).commit();
					userInfo.edit()
							.putString("U_StudentCode",
									jsonObject.getString("U_StudentCode"))
							.commit();
					userInfo.edit()
							.putString(
									"O_Name",
									URLDecoder.decode(jsonObject
											.getString("O_Name"))).commit();
					bitmap = getHttpBitmap("http://192.168.4.30:8002/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentPhoto?Exam_Student_Code="
							+ userInfo.getString("Exam_Student_Code", ""));
					pd.dismiss();
					pd = null;

					Message message = new Message();
					message.what = 0;
					handler1.sendMessageDelayed(message, 0);
					isTrue = false;

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	});
	Thread newThread2 = new Thread(new Runnable() {
		@Override
		public void run() {
			while (isTrue1) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Exam_Student_Code",
						userInfo.getString("Exam_Student_Code", ""));
				// 服务器请求路径
				String urlPath = "http://192.168.4.30:8002/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentInfo";
				InputStream is = null;

				try {
					is = NetTool.getInputStreamByPost(urlPath, map, "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				byte[] data = null;

				try {
					data = NetTool.readStream(is);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String result = new String(data);
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(result.toString());
					userInfo.edit()
							.putString(
									"U_TrueName",
									URLDecoder.decode(jsonObject
											.getString("U_TrueName"))).commit();
					userInfo.edit()
							.putString("U_StudentCode",
									jsonObject.getString("U_StudentCode"))
							.commit();
					userInfo.edit()
							.putString(
									"O_Name",
									URLDecoder.decode(jsonObject
											.getString("O_Name"))).commit();
					bitmap = getHttpBitmap("http://192.168.4.30:8002/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentPhoto?Exam_Student_Code="
							+ userInfo.getString("Exam_Student_Code", ""));
					pd.dismiss();
					pd = null;

					Message message = new Message();
					message.what = 0;
					handler1.sendMessageDelayed(message, 0);
					isTrue1 = false;

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	});

	public static Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	private String createFile(Bitmap bitmap) {
		createSDCardDir();
		ByteArrayOutputStream baos = null;
		String _path = null;
		try {
			String sign_dir = Environment.getExternalStorageDirectory()
					.getPath() + "/qianmingImages/";
			_path = sign_dir + "img.png";
			baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] photoBytes = baos.toByteArray();
			if (photoBytes != null) {
				new FileOutputStream(new File(_path)).write(photoBytes);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return _path;
	}

	public void createSDCardDir() {
		File sdcardDir = Environment.getExternalStorageDirectory();
		// 得到一个路径，内容是sdcard的文件夹路径和名字
		String pathdir = sdcardDir.getPath() + "/qianmingImages/";
		File path1 = new File(pathdir);
		if (!path1.exists()) {
			// 若不存在，创建目录，可以在应用启动的时候创建
			path1.mkdirs();
			// setTitle("paht ok,path:"+pathdir);
		}
	}

	class TestThread extends Thread {
		public void run() {
			while (isTrue) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Exam_Student_Code",
						userInfo.getString("Exam_Student_Code", ""));
				// 服务器请求路径
				String urlPath = "http://"
						+ userInfo.getString("ipconfig", "")
						+ "/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentInfo";
				InputStream is = null;

				try {
					is = NetTool.getInputStreamByPost(urlPath, map, "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (is == null) {
					Message message = new Message();
					message.what = 0;
					handler5.sendMessageDelayed(message, 0);
					isTrue = false;
				} else {

					byte[] data = null;

					try {
						data = NetTool.readStream(is);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String result = new String(data);
					JSONObject jsonObject = null;
					try {
						jsonObject = new JSONObject(result.toString());
						userInfo.edit()
								.putString(
										"U_TrueName",
										URLDecoder.decode(jsonObject
												.getString("U_TrueName")))
								.commit();
						userInfo.edit()
								.putString("U_StudentCode",
										jsonObject.getString("U_StudentCode"))
								.commit();
						userInfo.edit()
								.putString(
										"O_Name",
										URLDecoder.decode(jsonObject
												.getString("O_Name"))).commit();
						bitmap = getHttpBitmap("http://"
								+ userInfo.getString("ipconfig", "")
								+ "/UI/HandScoreAndroid/HandScoreAndroid.aspx/SearchStudentPhoto?Exam_Student_Code="
								+ userInfo.getString("Exam_Student_Code", ""));

						Message message = new Message();
						message.what = 0;
						handler1.sendMessageDelayed(message, 0);
						isTrue = false;

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void setParams(LayoutParams lay) {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Rect rect = new Rect();
		View view = getWindow().getDecorView();
		view.getWindowVisibleDisplayFrame(rect);
		lay.height = dm.heightPixels - rect.top;
		lay.width = dm.widthPixels;
	}

	private void createCamera() {
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

		hasSurface = false;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("down");
			if (SeclectTableActivity.this.getCurrentFocus() != null) {
				if (SeclectTableActivity.this.getCurrentFocus()
						.getWindowToken() != null) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(SeclectTableActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					studentnumEditText.setFocusable(false);
					studentnumEditText.setFocusableInTouchMode(false);
					pDialog = new CustomProgressDialog(
							SeclectTableActivity.this, "正在获取考生信息");
					pDialog.setTitle("稍等片刻");// 设置标题
					pDialog.setMessage("正在加载，请稍候");
					pDialog.setIndeterminate(false);// 设置进度条是否为不明确
					pDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
					pDialog.show();
					userInfo.edit()
							.putString("Exam_Student_Code",
									studentnumEditText.getText().toString())
							.commit();

					if (newThread2 == null) {
						isTrue1 = true;
						newThread2.start();
					} else {
						if (isTrue == false) {
							isTrue = true;
							mTestThread = new TestThread();
							mTestThread.start();
						} else {
							mTestThread = new TestThread();
							mTestThread.start();
						}

					}
				}
			}
		}
		return super.onTouchEvent(event);
	}

	private void settablelayout() {
		Typeface face = Typeface.createFromAsset(getAssets(), "MSYH.zip");
		relativeLayoutall = (RelativeLayout) findViewById(R.id.relativeLayouttableshow);
		final LayoutInflater inflater = LayoutInflater
				.from(SeclectTableActivity.this);
		View view = inflater.inflate(R.layout.tablelayout1, null);
		TextView showtView = (TextView) view
				.findViewById(R.id.textshowtablename);
		showtView.setText(userInfo.getString("tablenum", ""));
		showtView.setTypeface(face);
		relativeLayoutall.addView(view);
		TableLayout firsTableLayout = (TableLayout) view
				.findViewById(R.id.tableadd);
		int i1 = tableItem.size() * 3;
		TableRow btn1[] = new TableRow[i1 / 3];
		TableLayout Btn[] = new TableLayout[i1];
		for (int i = 0; i < i1; i++) {
			if (i % 3 == 0) {
				List<String> tableitem = (List<String>) tableItem.get(i / 3)
						.get("2");
				tView1 = new TextView(SeclectTableActivity.this);
				tView1.setBackgroundResource(R.drawable.et_border);
				tView1.setText(tableItem.get(i / 3).get("1").toString());// 设置内容
				// tView1.setTextSize(18);//设置字体大小
				tView1.setTextColor(getResources().getColor(R.color.temple));// 设置字体颜色
				tView1.setWidth(250);
				TextPaint tp1 = tView1.getPaint();
				tp1.setFakeBoldText(true);
				tView1.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);
				tView1.setTypeface(face);
				tView1.setHeight(35 * tableitem.size());
				tView1.setPadding(5, 5, 5, 5);// 设置四周留白
				tView1.setGravity(Gravity.CENTER);
				// tableRow.addView(tView1);
				TableLayout tl = (TableLayout) view
						.findViewWithTag("tablelayout" + i);
				tl.addView(tView1);

				// Btn[i].addView(btn1[i]);

			} else if (i % 3 == 1) {
				// btn1[i-1].addView(Btn[i]);
				// TableRow tableRow
				// =(TableRow)view.findViewWithTag("tablerow"+i/3);
				// tableRow.setGravity(Gravity.LEFT);
				List<String> tableitem = (List<String>) tableItem.get(
						(i - 1) / 3).get("2");
				for (int j = 0; j < tableitem.size(); j++) {

					TableRow tr = new TableRow(SeclectTableActivity.this);
					tr.setBackgroundResource(R.drawable.et_border);
					TextView tView = new TextView(SeclectTableActivity.this);
					tView.setText(tableitem.get(j).toString());// 设置内容
					// tView.setTextSize(18);//设置字体大小
					tView.setTextColor(getResources().getColor(R.color.temple));// 设置字体颜色
					tView.setWidth(290);
					TextPaint tp1 = tView.getPaint();
					tp1.setFakeBoldText(true);
					tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);
					tView.setTypeface(face);
					tView.setPadding(5, 5, 5, 5);// 设置四周留白
					tView.setGravity(Gravity.LEFT);
					tr.addView(tView);
					TableLayout tl = (TableLayout) view
							.findViewWithTag("tablelayout" + i);
					tl.addView(tr);

				}

			} else if (i % 3 == 2) {
				List<String> tablescore = (List<String>) tableItem.get(
						(i - 2) / 3).get("3");
				for (int j = 0; j < tablescore.size(); j++) {

					TableRow tr = new TableRow(SeclectTableActivity.this);
					tr.setBackgroundResource(R.drawable.et_border);
					TextView tView = new TextView(SeclectTableActivity.this);
					tView.setText(tablescore.get(j).toString());// 设置内容
					// tView.setTextSize(18);//设置字体大小
					tView.setTextColor(getResources().getColor(R.color.temple));// 设置字体颜色
					tView.setTypeface(face);
					tView.setWidth(95);
					TextPaint tp1 = tView.getPaint();
					tp1.setFakeBoldText(true);
					tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);
					tView.setPadding(5, 5, 5, 5);// 设置四周留白
					tView.setGravity(Gravity.CENTER);
					tr.addView(tView);
					TableLayout tl = (TableLayout) view
							.findViewWithTag("tablelayout" + i);
					tl.addView(tr);
				}

				// btn1[i-2].addView(Btn[i]);
			}
		}

	}
}
