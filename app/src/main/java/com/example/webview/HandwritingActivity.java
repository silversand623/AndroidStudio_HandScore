package com.example.webview;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.webview.tools.CustomDialog;
import com.example.webview.tools.DialogListener;
import com.example.webview.tools.WritePadDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HandwritingActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Bitmap mSignBitmap;
	private String signPath;
	private ImageView ivSign;
	private TextView tvSign;
	private Button btn_submit;
	private String sign_dir;
	private String newName ="image.jpg";
	private ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("欢迎使用数字签名");
        ivSign =(ImageView)findViewById(R.id.iv_sign);
        tvSign = (TextView)findViewById(R.id.tv_sign);      
        btn_submit=(Button)findViewById(R.id.btn_sumbit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(tvSign.getVisibility()==View.VISIBLE){
					Toast.makeText(getApplicationContext(), "请数字签名后在提交", 0).show();
				}else{
				uploadFile("");
				}
			}
		});
        ivSign.setOnClickListener(signListener);
        tvSign.setOnClickListener(signListener);
    }
    
	
	private OnClickListener signListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			WritePadDialog writeTabletDialog = new WritePadDialog(
					HandwritingActivity.this, new DialogListener() {
						@Override
						public void refreshActivity(Object object) {							
							
							mSignBitmap = (Bitmap) object;
//							BitmapFactory.Options options = new BitmapFactory.Options();
//							options.inSampleSize = 15;
//							options.inTempStorage = new byte[5 * 1024];
//							Bitmap zoombm = BitmapFactory.decodeFile(signPath, options);	
							//ivSign.setImageBitmap(zoombm);
							//Bitmap.createScaledBitmap(bitmap, width, height, false);
							Bitmap bitmap=Bitmap.createScaledBitmap(mSignBitmap, 200, 200, false);
							signPath = createFile();
							//Bitmap bitmap=Bitmap.createBitmap(mSignBitmap, 0, 0, 200, 200);
							//ivSign.setImageBitmap(mSignBitmap);
							ivSign.setImageBitmap(bitmap);
						
						
							ivSign.setVisibility(View.VISIBLE);
							ivSign.setClickable(false);
							tvSign.setClickable(false);
							tvSign.setVisibility(View.GONE);
						}
					});
			writeTabletDialog.show();
		}
	};
	
	/**
	 * 创建手写签名文件
	 * 
	 * @return
	 */
	private String createFile() {
		ByteArrayOutputStream baos = null;
		String _path = null;
		try {
			String sign_dir = Environment.getExternalStorageDirectory().getPath()+"/qianmingImages/";			
			_path = sign_dir + System.currentTimeMillis() + ".jpg";
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
	private void uploadFile(String actionUrl)
    {
      String end ="\r\n";
      String twoHyphens ="--";
      String boundary ="*****";
    
      try
      {
        URL url =new URL(actionUrl);
        HttpURLConnection con=(HttpURLConnection)url.openConnection();
        pd = new ProgressDialog(HandwritingActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		pd.setTitle("稍等片刻");// 设置标题
		pd.setMessage("正在加载，请稍候");
		pd.setIndeterminate(false);// 设置进度条是否为不明确
		pd.setCancelable(true);// 设置进度条是否可以按退回键取消
		pd.show();
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
                      "name=\"file1\";filename=\""+
                      newName +"\""+ end);
        ds.writeBytes(end);  
        /* 取得文件的FileInputStream */
        FileInputStream fStream =new FileInputStream(signPath);
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
        /* 取得Response内容 */
        InputStream is = con.getInputStream();
        int ch;
        StringBuffer b =new StringBuffer();
        while( ( ch = is.read() ) !=-1 )
        {
          b.append( (char)ch );
        }
        pd.dismiss();
        /* 将Response显示于Dialog */
        showDialog("上传成功"+b.toString().trim());
        /* 关闭DataOutputStream */
        ds.close();
      }
      catch(Exception e)
      {
      if(pd==null){
    	     CustomDialog customDialog=new CustomDialog(HandwritingActivity.this,R.style.MyDialog,new DialogListener() {
 				@Override
 				public void refreshActivity(Object object) {							
 				
 				
 				}
 			}, "上传失败",false);
 customDialog.show();
     //  showDialog("上传失败");
      }
      }
    }
    /* 显示Dialog的method */
    private void showDialog(String mess)
    {
      new AlertDialog.Builder(HandwritingActivity.this).setTitle("Message")
       .setMessage(mess)
       .setNegativeButton("确定",new DialogInterface.OnClickListener()
       {
         public void onClick(DialogInterface dialog, int which)
         {          
        	 Intent intent=new Intent();
        	 intent.setClass(HandwritingActivity.this, HandheldscaleActivity.class);
        	 startActivity(intent);
         }
       })
       .show();
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
}