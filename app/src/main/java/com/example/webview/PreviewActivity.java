package com.example.webview;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.CustomProgressDialog;
import com.example.webview.tools.DialogListener;
import com.example.webview.tools.NetTool;
import com.example.webview.tools.SelectPreviewAdapter;
import com.example.webview.tools.WritePadDialog;

public class PreviewActivity extends Activity  {
	private TextView txvshowname,txvlogout,txvresulttable,txvresulttablename,txvstudentid,actv_selectid,txvpremessage,txvoperationmethod,txvoperationcontent,txvscoreoperation,txvtablename,txvpreallscore,txvshowtablename,showtableexamnum,showtablestudentname,tableexamnum,tablestudentname,showtableexamname,showtableexamclassname,tableexamname,tableexamclassname,txvshowtableclass, txvtableclass,txvprewritename,showtableroomnum,tableroomnum,txvprethiscontext,textView_showmessage;
	private ListView listView;
	private Button btnsmallfont,btnmiddlefont,btnlargefont,btnsubmit,btnback,btnpreimgok,btnpreimgcancel;
	private String name;
	private String resulttablename;
	private Bitmap mSignBitmap;
	private String signPath;
	private boolean writeflag=false;
	SelectPreviewAdapter adapter;
	private boolean isTrue=true;
private ImageView preimg;
private TextView textview_prenameimg;
ArrayList<HashMap<String, Object>> listItem;
SharedPreferences userInfo;
private String newName ="image.jpg";
private TestThread mTestThread;
private ProgressDialog pd;
CustomProgressDialog pDialog;
static final int BACKGROUND_COLOR = Color.WHITE;
PaintView mView;
LayoutParams p ;
static final int BRUSH_COLOR = Color.BLACK;
private RelativeLayout rLayout;
public static CustomProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		HandheldscaleActivity.dialog.dismiss();
		Intent intent =getIntent();
		userInfo = getSharedPreferences("user_info", 0);
		userInfo.edit().putString("writehand", "used").commit();
//		name=intent.getStringExtra("name");
//		resulttablename=intent.getStringExtra("num");
		txvshowname=(TextView)findViewById(R.id.txvshowprespname);
		txvlogout=(TextView)findViewById(R.id.txvprelogout);
//		txvresulttable=(TextView)findViewById(R.id.txvpreresulttable);
//		txvresulttablename=(TextView)findViewById(R.id.txvpreresulttablename);
//		txvstudentid=(TextView)findViewById(R.id.txvprestudentid);
//		actv_selectid=(TextView)findViewById(R.id.actv_selectid);
		//txvpremessage=(TextView)findViewById(R.id.txvpremessage);
		txvoperationmethod=(TextView)findViewById(R.id.txvpreoperationmethod);
		txvoperationcontent=(TextView)findViewById(R.id.txvpreoperationcontent);
		txvscoreoperation=(TextView)findViewById(R.id.txvprescoreoperation);
		txvpreallscore=(TextView)findViewById(R.id.txvpreallscore);
		txvprethiscontext=(TextView)findViewById(R.id.txvprethiscontext);
		textView_showmessage=(TextView)findViewById(R.id.textView_showmessage);
		txvshowtablename=(TextView)findViewById(R.id.textView_showpretablename);
		showtableexamnum=(TextView)findViewById(R.id.textView_showpretableexamnum);
		showtablestudentname=(TextView)findViewById(R.id.textView_showpretablestudentname);
		tableexamnum=(TextView)findViewById(R.id.textView_tablepreexamnum);
		tablestudentname=(TextView)findViewById(R.id.textView_tableprestudentname);
		showtableexamname=(TextView)findViewById(R.id.textView_showpretableexamname);
		showtableexamclassname=(TextView)findViewById(R.id.textView_showpretableexamclassnum);
		tableexamname=(TextView)findViewById(R.id.textView_tablepreexamname);
		tableexamclassname=(TextView)findViewById(R.id.textView_tablepreexamclassnum);
		txvshowtableclass=(TextView)findViewById(R.id.textView_showpretableclass);
		txvtableclass=(TextView)findViewById(R.id.textView_tablepreclass);
//		txvshowtableclass=(TextView)findViewById(R.id.textView_showpretableclass);
//		txvtableclass=(TextView)findViewById(R.id.textView_tablepreclass);
		txvtablename=(TextView)findViewById(R.id.textView_tableprename);
		showtableroomnum=(TextView)findViewById(R.id.textView_showpretableroomnum);
		tableroomnum=(TextView)findViewById(R.id.textView_tablepreroomnum);
	//txvprewritename=(TextView)findViewById(R.id.txvprewritename);
		preimg=(ImageView)findViewById(R.id.imageview_preimg);
		textview_prenameimg=(TextView)findViewById(R.id.textview_prenameimg);
		final SharedPreferences userInfo = getSharedPreferences("user_info", 0);
		//prenameimg.setEnabled(true);
		preimg.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/qianmingImages/img.png"));
		tableexamnum.setText(userInfo.getString("U_StudentCode", ""));
		tablestudentname.setText(userInfo.getString("U_TrueName", ""));
		txvtableclass.setText(userInfo.getString("O_Name", ""));
		tableexamclassname.setText(userInfo.getString("ES_NAME", ""));
		showtableroomnum.setVisibility(View.VISIBLE);
		tableroomnum.setText(userInfo.getString("Room_Name", ""));
		txvtableclass.setText(userInfo.getString("O_Name", ""));
		tableexamnum.setText(userInfo.getString("U_StudentCode", ""));
		tablestudentname.setText(userInfo.getString("U_TrueName", ""));
		txvtablename.setText(userInfo.getString("studentnum", "").toString());
		tableexamname.setText(userInfo.getString("examname", "").toString());
		tableexamclassname.setText(userInfo.getString("ES_NAME", ""));
	rLayout=(RelativeLayout)findViewById(R.id.relativeLayoutwrite);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
//		p=frameLayout.get
//		p.width=620;
//		p.height=530;
		mView = new PaintView(getApplicationContext());
		frameLayout.addView(mView);
		mView.requestFocus();
		
//		prenameimg.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				if(!userInfo.contains("writehand")){
//					Log.i("sumbit", "____________++++++++++++++++++++");
//					userInfo.edit().putString("writehand", "").commit();
//					createwrite();
//					writeflag=true;
//				}else{
//				if (userInfo.getString("writehand", "").equals("used")) {
//					userInfo.edit().putString("writehand", "").commit();
//					Log.i("sumbit", "++++++++++++++++++++");
//					createwrite();
//					writeflag=true;
//				}else{
//					Log.i("sumbit", "}}}}}}}}}}}}}}}}}}}++++++++++++++++++++");
//				   CustomDialog customDialog=new CustomDialog(PreviewActivity.this,R.style.MyDialog,new DialogListener() {
//						@Override
//						public void refreshActivity(Object object) {							
//							createwrite();
//							writeflag=true;
//						}
//					}, "是否重新进行签名",true);
//		customDialog.show();
//				}
//			}
//			}
//		});
//		txvprewritename.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				createwrite();
//			}
//		});
		//txvtablename.setText(userInfo.getString("studentnum", ""));
		txvshowname.setText("欢迎，"+userInfo.getString("truename", ""));
		TextPaint tp = txvshowname.getPaint(); 
		tp.setFakeBoldText(true);
		txvlogout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		//txvresulttablename.setText(userInfo.getString("tablenum", ""));
		txvlogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(PreviewActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		//actv_selectid.setText(userInfo.getString("studentnum", ""));
		listView=(ListView)findViewById(R.id.mylist2);
		listItem = (ArrayList<HashMap<String, Object>>) intent.getSerializableExtra("list");
		Log.i("OUIYIOO+++++++++++++++++++++++++", listItem.toString());
		int sum=0;int allsum=0;
		for(int i=0;i<listItem.size();i++){
			HashMap<String, Object> mapcontentlist=(HashMap<String, Object>) listItem.get(i).get("4");
			Log.i("OUIYIOO+++++++++++++++++++++++++",  mapcontentlist.toString());
			 List<String> score1=(List<String>) listItem.get(i).get("3");
			//	Log.i("OUIYIOO+++++++++++++++++++++++++1", mapcontentlist.get(String.valueOf(value)).toString());
			//	Log.i("OUIYIOO+++++++++++++++++++++++++2", score1.toString());
				for(int j=0;j<score1.size();j++){
			sum+=Integer.parseInt((String) mapcontentlist.get(String.valueOf(j)).toString());
			allsum+=Integer.parseInt((String) score1.get(j));
				}
		}
		
		String newMessageInfo = "当前考站总分为："+allsum+",考生得分为："+"<font color='blue'><b>" + sum
		+ "</b></font>";
		txvpreallscore.setText(Html.fromHtml(newMessageInfo));
		TextPaint tp1 = txvpreallscore.getPaint(); 
		tp.setFakeBoldText(true);
		
		Typeface face = Typeface.createFromAsset (getAssets() , "MSYH.zip" );
		adapter=new SelectPreviewAdapter(PreviewActivity.this, listItem,9,face);
		listView.setAdapter(adapter);
//		btnsmallfont=(Button)findViewById(R.id.btn_presmallfont);
//		btnmiddlefont=(Button)findViewById(R.id.btn_premiddlefont);
//		btnlargefont=(Button)findViewById(R.id.btn_prelargefont);
		btnback=(Button)findViewById(R.id.btn_preback);
		btnsubmit=(Button)findViewById(R.id.btn_presubmit);
//		btnsmallfont.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				setfont(14);
//				adapter=new SelectPreviewAdapter(PreviewActivity.this, listItem,14);
//				listView.setAdapter(adapter);
//			}
//		});
//		btnmiddlefont.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				setfont(20);
//				adapter=new SelectPreviewAdapter(PreviewActivity.this, listItem,20);
//				listView.setAdapter(adapter);
//			}
//		});
//		btnlargefont.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				setfont(26);
//				adapter=new SelectPreviewAdapter(PreviewActivity.this, listItem,26);
//				listView.setAdapter(adapter);
//			}
//		});
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				dialog=new CustomProgressDialog(PreviewActivity.this,"正在返回打分页面");
				dialog.setTitle("稍等片刻");// 设置标题
				dialog.setMessage("正在加载，请稍候");
				dialog.setIndeterminate(false);// 设置进度条是否为不明确
				dialog.setCancelable(false);// 设置进度条是否可以按退回键取消
				if(dialog!=null){
					dialog.show();
				}
				intent.setClass(PreviewActivity.this, HandheldscaleActivity.class);
				intent.putExtra("returnlist", listItem);
				userInfo.edit().putString("writehand", "used").commit();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		btnsubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(writeflag==false){
			       CustomDialog customDialog=new CustomDialog(PreviewActivity.this,R.style.MyDialog,new DialogListener() {
						@Override
						public void refreshActivity(Object object) {							
						
						}
					}, "请进行数字签名在提交",false);
		customDialog.show();
			}else{
//				pd = new ProgressDialog(PreviewActivity.this);
//				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
//				pd.setTitle("稍等片刻");// 设置标题
//				pd.setMessage("正在加载，请稍候");
//				pd.setIndeterminate(false);// 设置进度条是否为不明确
//				pd.setCancelable(true);// 设置进度条是否可以按退回键取消
//				pd.show();
			
				pDialog=new CustomProgressDialog(PreviewActivity.this,"正在提交");
				pDialog.setTitle("稍等片刻");// 设置标题
				pDialog.setMessage("正在加载，请稍候");
				pDialog.setIndeterminate(false);// 设置进度条是否为不明确
				pDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
				pDialog.show();
				 Timer timer = new Timer();  
		  		   timer.schedule(task, 1000);
				
			}
			}
		});
		btnpreimgok=(Button)findViewById(R.id.btn_preimgok);
		btnpreimgok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				createwrite();
				writeflag=true;
				rLayout.setBackgroundResource(R.drawable.et_border1);
				textView_showmessage.setText("签名成功");
			}
		});
		btnpreimgcancel=(Button)findViewById(R.id.btn_preimgcancel);
		btnpreimgcancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 mView.clear();
				 writeflag=false;
				 textview_prenameimg.setVisibility(View.VISIBLE);
					rLayout.setBackgroundResource(R.drawable.et_border);
					textView_showmessage.setText("");
			}
		});
		setfont();
	}
	@Override
	public void onBackPressed() {
		Intent intent=new Intent();
		Intent intentrerurnIntent=getIntent();
//		if(intentrerurnIntent.getStringExtra("jump")!=null){
//			
//		}else{
		dialog=new CustomProgressDialog(PreviewActivity.this,"正在返回打分页面");
		dialog.setTitle("稍等片刻");// 设置标题
		dialog.setMessage("正在加载，请稍候");
		dialog.setIndeterminate(false);// 设置进度条是否为不明确
		dialog.setCancelable(false);// 设置进度条是否可以按退回键取消
		if(dialog!=null){
			dialog.show();
		}
		intent.setClass(PreviewActivity.this, HandheldscaleActivity.class);
		intent.putExtra("returnlist", listItem);
		startActivity(intent);
		finish();
		super.onBackPressed();
		//}
	}
	private void setfont(float a){
		//txvresulttable.setTextSize(a);
		//txvresulttablename.setTextSize(a);
		//txvstudentid.setTextSize(a);
		txvoperationmethod.setTextSize(a);
		txvoperationcontent.setTextSize(a);
		txvscoreoperation.setTextSize(a);
//		actv_selectid.setTextSize(a);
	}
	/**
	 * 创建手写签名文件
	 * 
	 * @return
	 */
	private String createFile() {
		createSDCardDir();
		ByteArrayOutputStream baos = null;
		String _path = null;
		try {
			String sign_dir = Environment.getExternalStorageDirectory().getPath()+"/qianmingImages/";			
			_path = sign_dir +  "write.jpg";
			baos = new ByteArrayOutputStream();
			mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
	 public void createSDCardDir(){
		 File sdcardDir =Environment.getExternalStorageDirectory();
        //得到一个路径，内容是sdcard的文件夹路径和名字
          String pathdir=sdcardDir.getPath()+"/qianmingImages/";
          File path1 = new File(pathdir);
         if (!path1.exists()) {
          //若不存在，创建目录，可以在应用启动的时候创建
          path1.mkdirs();
//          setTitle("paht ok,path:"+pathdir);
        }
	    }
	 public void createwrite(){
									
							
							mSignBitmap = mView.getCachebBitmap();
//							BitmapFactory.Options options = new BitmapFactory.Options();
//							options.inSampleSize = 15;
//							options.inTempStorage = new byte[5 * 1024];
							signPath = createFile();
							Bitmap zoombm = BitmapFactory.decodeFile(signPath, null);
						        
						        //获取这个图片的宽和高
						        int width = zoombm.getWidth();
						        int height = zoombm.getHeight();
						        
						        //定义预转换成的图片的宽度和高度
						        int newWidth = 440;
						        int newHeight = 240;
						        
						        //计算缩放率，新尺寸除原始尺寸
						        float scaleWidth = ((float) newWidth) / width;
						        float scaleHeight = ((float) newHeight) / height;
						        
						        // 创建操作图片用的matrix对象
						        Matrix matrix = new Matrix();
						        
						        // 缩放图片动作
						        matrix.postScale(scaleWidth, scaleHeight);
						        
						        //旋转图片 动作
//						        matrix.postRotate(45);
						        
						        // 创建新的图片
						        Bitmap resizedBitmap = Bitmap.createBitmap(zoombm, 0, 0,
						        width, height, matrix, true);
						        
						        //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
						        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
							//ivSign.setImageBitmap(zoombm);
							//Bitmap.createScaledBitmap(bitmap, width, height, false);
							//Bitmap bitmap=Bitmap.createScaledBitmap(mSignBitmap, 200, 200, false);
							
							//Bitmap bitmap=Bitmap.createBitmap(mSignBitmap, 0, 0, 200, 200);
							//ivSign.setImageBitmap(mSignBitmap);
							//prenameimg.setImageDrawable(bmd);
						//prenameimg.setEnabled(true);
							//txvprewritename.setVisibility(View.GONE);
				
			
	    }
	 private void setfont(){
			Typeface face = Typeface.createFromAsset (getAssets() , "MSYH.zip" );
			txvshowname.setTypeface(face);
			txvlogout.setTypeface(face);
			txvoperationmethod.setTypeface(face);
			txvoperationcontent.setTypeface(face);
			txvscoreoperation.setTypeface(face);
			txvpreallscore.setTypeface(face);
			txvshowtablename.setTypeface(face);
			showtableexamnum.setTypeface(face);
			showtablestudentname.setTypeface(face);
			tableexamnum.setTypeface(face);
			tablestudentname.setTypeface(face);
			showtableexamname.setTypeface(face);
			showtableexamclassname.setTypeface(face);
			tableexamname.setTypeface(face);
			tableexamclassname.setTypeface(face);
			txvshowtableclass.setTypeface(face);
			txvtableclass.setTypeface(face);
			txvtablename.setTypeface(face);
			btnsubmit.setTypeface(face);
			btnback.setTypeface(face);
			//txvpremessage.setTypeface(face);
			showtableroomnum.setTypeface(face);
			tableroomnum.setTypeface(face);
			btnpreimgok.setTypeface(face);
			btnpreimgcancel.setTypeface(face);
			txvprethiscontext.setTypeface(face);
			textView_showmessage.setTypeface(face);
			TextPaint tp = txvoperationmethod.getPaint(); 
			tp.setFakeBoldText(true);
			TextPaint tp1 = txvoperationcontent.getPaint(); 
			tp1.setFakeBoldText(true);
			TextPaint tp2 = txvscoreoperation.getPaint(); 
			tp2.setFakeBoldText(true);
			TextPaint tp3 = txvpreallscore.getPaint(); 
			tp3.setFakeBoldText(true);
			TextPaint tp4 = txvshowtablename.getPaint(); 
			tp4.setFakeBoldText(true);
			TextPaint tp5 = showtableexamnum.getPaint(); 
			tp5.setFakeBoldText(true);
			TextPaint tp6 = showtablestudentname.getPaint(); 
			tp6.setFakeBoldText(true);
			TextPaint tp7 = tableexamnum.getPaint(); 
			tp7.setFakeBoldText(true);
			TextPaint tp8 = tablestudentname.getPaint(); 
			tp8.setFakeBoldText(true);
			TextPaint tp9 = showtableexamname.getPaint(); 
			tp9.setFakeBoldText(true);
			TextPaint tp10 = showtableexamclassname.getPaint(); 
			tp10.setFakeBoldText(true);
			TextPaint tp11 = tableexamname.getPaint(); 
			tp11.setFakeBoldText(true);
			TextPaint tp12 = tableexamclassname.getPaint(); 
			tp12.setFakeBoldText(true);
			TextPaint tp13 = txvshowtableclass.getPaint(); 
			tp13.setFakeBoldText(true);
			TextPaint tp14 = txvtableclass.getPaint(); 
			tp14.setFakeBoldText(true);
			TextPaint tp15 = txvtablename.getPaint(); 
			tp15.setFakeBoldText(true);
//			TextPaint tp16 = txvpremessage.getPaint(); 
//			tp16.setFakeBoldText(true);
			TextPaint tp17 = showtableroomnum.getPaint(); 
			tp17.setFakeBoldText(true);
			TextPaint tp18 = tableroomnum.getPaint(); 
			tp18.setFakeBoldText(true);
			TextPaint tp19 = txvprethiscontext.getPaint(); 
			tp19.setFakeBoldText(true);
			TextPaint tp20 = textView_showmessage.getPaint(); 
			tp20.setFakeBoldText(true);
		}
	 /** 
	     * 递归删除文件和文件夹 
	     *  
	     * @param file 
	     *            要删除的根目录 
	     */  
	    public void DeleteFile(File file) {  
	        if (file.exists() == false) {  
	            return;  
	        } else {  
	            if (file.isFile()) {  
	                file.delete();  
	                return;  
	            }  
	            if (file.isDirectory()) {  
	                File[] childFile = file.listFiles();  
	                if (childFile == null || childFile.length == 0) {  
	                    file.delete();  
	                    return;  
	                }  
	                for (File f : childFile) {  
	                    DeleteFile(f);  
	                }  
	                file.delete();  
	            }  
	        }  
	    }  
	     private void uploadFile(String posturl)
	      {
	        String end ="\r\n";
	        String twoHyphens ="--";
	        String boundary ="*****";
	        try
	        {
	          URL url =new URL(posturl);
	          HttpURLConnection con=(HttpURLConnection)url.openConnection();
	          /* 允许Input、Output，不使用Cache */
	          con.setDoInput(true);
	          con.setDoOutput(true);
	          con.setUseCaches(false);
	          /* 设置传送的method=POST */
	          con.setRequestMethod("POST");
	          /* setRequestProperty */
	          con.setRequestProperty("Connection", "Keep-Alive");
	          con.setRequestProperty("Charset", "UTF-8");
	          con.setRequestProperty("Content-Type",
	                             "multipart/form-data;boundary="+boundary);
	          /* 设置DataOutputStream */
	          DataOutputStream ds =
	            new DataOutputStream(con.getOutputStream());
	          ds.writeBytes(twoHyphens + boundary + end);
	          ds.writeBytes("Content-Disposition: form-data; "+
	                        "name=\"image\";filename=\""+
	                        newName +"\""+ end);
	          ds.writeBytes(end);  
	          /* 取得文件的FileInputStream */
	          FileInputStream fStream =new FileInputStream(Environment.getExternalStorageDirectory().getPath()+"/qianmingImages/write.jpg");
	          /* 设置每次写入1024bytes */
	          int bufferSize =1024;
	          byte[] buffer =new byte[bufferSize];
	          int length =-1;
	          /* 从文件读取数据至缓冲区 */
	          while((length = fStream.read(buffer)) !=-1)
	          {
	            /* 将资料写入DataOutputStream中 */
	            ds.write(buffer, 0, length);
	          }
	          ds.writeBytes(end);
	          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	          /* close streams */
	          fStream.close();
	          ds.flush();
	          pDialog.dismiss();
	          /* 取得Response内容 */
	          InputStream is = con.getInputStream();
	          int ch;
	          StringBuffer b =new StringBuffer();
	          while( ( ch = is.read() ) !=-1 )
	          {
	            b.append( (char)ch );
	          }
	      
	          JSONObject jsonObject = null;
					jsonObject = new JSONObject(b.toString());
					String resultString=jsonObject.getString("result");
					if(resultString.equals("1")){
					
					
						
					}else{
						
					}
	          
	          /* 将Response显示于Dialog */
	         // showDialog("上传成功"+b.toString().trim());
	          /* 关闭DataOutputStream */
	          ds.close();
	        }
	        
	        catch(Exception e)
	        {
	    //      showDialog("上传失败"+e);
	        }
	      }
	      /* 显示Dialog的method */
	      private void showDialog(String mess)
	      {
	        new AlertDialog.Builder(PreviewActivity.this).setTitle("Message")
	         .setMessage(mess)
	         .setNegativeButton("确定",new DialogInterface.OnClickListener()
	         {
	           public void onClick(DialogInterface dialog, int which)
	           {          
	           }
	         })
	         .show();
	      }
	      class TestThread extends Thread{
              public void run(){
                          while(isTrue){
                        	  uploadFile("http://"+userInfo.getString("ipconfig", "")+"/UI/HandScoreAndroid/HandScoreAndroid.aspx/SaveExamResult");
                        	  Map<String,String> map = new HashMap<String,String>();  
                        	  //listItem
          			        //map.put("U_Name", usernameEditText.getText().toString());  
          			        //map.put("U_PWD", passwordEditText.getText().toString());  
          			        //服务器请求路径  
          			        String urlPath = "http://"+userInfo.getString("ipconfig", "")+"/UI/HandScoreAndroid/HandScoreAndroid.aspx/UserLogin";  
          			        InputStream is = null;
          					
          						try {
          							is = NetTool.getInputStreamByPost(urlPath, map, "UTF-8");
          			       if(is==null){
          			    	   Message message = new Message();
          						message.what = 0;
          						handler3.sendMessageDelayed(message, 0);
          						 isTrue=false;
          			       }else{
          			    	 byte[] data = null;
 							data = NetTool.readStream(is);
 						String result=new String(data);
 						JSONObject jsonObject = null;
						jsonObject = new JSONObject(result.toString());
							if( "1".equals(jsonObject.get("result").toString())){
								Message message = new Message();
        						message.what = 0;
        						handler1.sendMessageDelayed(message, 0);
        						isTrue=false;
							}
							
          			    		
          			       }
                          
          						}catch(Exception e){
          							e.printStackTrace();
          						}
              }
   }
	      }
	      Handler handler3 = new Handler() {
				public void handleMessage(Message msg) {
					 pDialog.dismiss();
					   CustomDialog customDialog=new CustomDialog(PreviewActivity.this,R.style.MyDialog,new DialogListener() {
							@Override
							public void refreshActivity(Object object) {							
							     
							}
						}, "网络连接错误",false);
			customDialog.show();
//					pDialog.dismiss();
//					Show(3);
					//relativeLayout.setVisibility(View.VISIBLE);
				}
			};
	      Handler handler1 = new Handler() {
	  		public void handleMessage(Message msg) {
	  			
	  			
	  		  CustomDialog customDialog=new CustomDialog(PreviewActivity.this,R.style.MyDialog,new DialogListener() {
					@Override
					public void refreshActivity(Object object) {							
						Intent intent=new Intent();
						intent.setClass(PreviewActivity.this, SeclectTableActivity.class);
						intent.putExtra("clear", "clear");
						userInfo.edit().putString("writehand", "used").commit();
						if(!userInfo.contains("studenttruenum")){
							userInfo.edit().putString("studenttruenum", "1").commit();
						}else{
							int a=Integer.parseInt(userInfo.getString("studenttruenum", ""));
							userInfo.edit().putString("studenttruenum", String.valueOf(a+1)).commit();
						}
						  File file = new File(Environment.getExternalStorageDirectory().getPath()+"/qianmingImages");  
				            DeleteFile(file);  
				            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}
				}, "数据已提交",false);
	customDialog.show();
	  			
	  			//relativeLayout.setVisibility(View.VISIBLE);
	  		}
	  	};
	   TimerTask task = new TimerTask(){    
	  	    public void run(){    
	  	 	 if(isTrue==false){
        		   isTrue=true;
        		   mTestThread=new TestThread();
        		   mTestThread.start();
        	   }else{
        	
        		   mTestThread=new TestThread();
        		   mTestThread.start();
        	   }
	  		     }    
	  		 };    
	  		class PaintView extends View {
	  			private Paint paint;
	  			private Canvas cacheCanvas;
	  			private Bitmap cachebBitmap;
	  			private Path path;

	  			public Bitmap getCachebBitmap() {
	  				return cachebBitmap;
	  			}

	  			public PaintView(Context context) {
	  				super(context);					
	  				init();			
	  			}

	  			private void init(){
	  				paint = new Paint();
	  				paint.setAntiAlias(true);
	  				paint.setStrokeWidth(3);
	  				paint.setStyle(Paint.Style.STROKE);
	  				paint.setColor(Color.BLACK);					
	  				path = new Path();
	  				cachebBitmap = Bitmap.createBitmap(595, 540, Config.ARGB_8888);			
	  				cacheCanvas = new Canvas(cachebBitmap);
	  				cacheCanvas.drawColor(Color.WHITE);
	  			}
	  			public void clear() {
	  				if (cacheCanvas != null) {
	  					
	  					paint.setColor(BACKGROUND_COLOR);
	  					cacheCanvas.drawPaint(paint);
	  					paint.setColor(Color.BLACK);
	  					cacheCanvas.drawColor(Color.WHITE);
	  					
	  					invalidate();			
	  				}
	  			}

	  			
	  			
	  			@Override
	  			protected void onDraw(Canvas canvas) {
	  				// canvas.drawColor(BRUSH_COLOR);
	  				canvas.drawBitmap(cachebBitmap, 0,0, null);
	  				canvas.drawPath(path, paint);
	  				
	  			}

	  			@Override
	  			protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	  				
	  				int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
	  				int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
	  				if (curW >= w && curH >= h) {
	  					return;
	  				}

	  				if (curW < w)
	  					curW = w;
	  				if (curH < h)
	  					curH = h;

	  				Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
	  				Canvas newCanvas = new Canvas();
	  				newCanvas.setBitmap(newBitmap);
	  				if (cachebBitmap != null) {
	  					newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
	  				}
	  				cachebBitmap = newBitmap;
	  				cacheCanvas = newCanvas;
	  			}

	  			private float cur_x, cur_y;

	  			@Override
	  			public boolean onTouchEvent(MotionEvent event) {
	  				
	  				float x = event.getX();
	  				float y = event.getY();

	  				switch (event.getAction()) {
	  				case MotionEvent.ACTION_DOWN: {
	  					cur_x = x;
	  					cur_y = y;
	  					textview_prenameimg.setVisibility(INVISIBLE);
	  					path.moveTo(cur_x, cur_y);
	  					break;
	  				}

	  				case MotionEvent.ACTION_MOVE: {
	  					path.quadTo(cur_x, cur_y, x, y);
	  					cur_x = x;
	  					cur_y = y;
	  					break;
	  				}

	  				case MotionEvent.ACTION_UP: {
	  					cacheCanvas.drawPath(path, paint);
	  					path.reset();
	  					break;
	  				}
	  				}

	  				invalidate();

	  				return true;
	  			}
	  		}
	  		@Override
	  		public boolean onTouchEvent(MotionEvent event) {
	  			  if  (event.getAction() == MotionEvent.ACTION_DOWN) {  
	  		            System.out.println("down" );  
	  		            if  (PreviewActivity. this .getCurrentFocus() !=  null ) {  
	  		                if  (PreviewActivity. this .getCurrentFocus().getWindowToken() !=  null ) {  
	  		                	InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	  		                    imm.hideSoftInputFromWindow(PreviewActivity.this .getCurrentFocus().getWindowToken(),  
	  		                            InputMethodManager.HIDE_NOT_ALWAYS);  
	  		                }  
	  		            }  
	  		        }  
	  		        return   super .onTouchEvent(event);  
	  		}
}
