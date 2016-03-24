package com.example.webview.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.webview.HandheldscaleActivity;
import com.example.webview.R;
import com.example.webview.tools.TableResizeLayout.OnResizeListener;

public class SelectScoreAdapter extends BaseAdapter implements AbstractSpinerAdapter.IOnItemSelectListener,OnResizeListener {
	private Context context;
	private ArrayList<HashMap<String, Object>> videoItems;
	public TextView showoperationmethod,showoperationcontent,showallotedscore;
	private EditText contentEditText;
    LayoutInflater inflater;
    private Button btndui;
    private Button btncuo;
 int num;
 int row;
 int editrow;
    int size;
    int width,height;
    int ssize;
    private TextView mTView;
    public static EditText editText1;
	private ImageButton mBtnDropDown;
	 ArrayList<String> allcountries;
	  private SpinerPopWindow mSpinerPopWindow;
	  Typeface font;
	 boolean imga=false;
	  boolean imgb=false;
	  HandheldscaleActivity activity;
	  private TestThread mTestThread;
    public SelectScoreAdapter(Context _context,ArrayList<HashMap<String, Object>> _items,int a,Typeface face,HandheldscaleActivity activity){
    	this.activity = activity;
    	context=_context;
    	videoItems=_items;
    	inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	size=a;
    	font=face;
//    	width=w;height=h;
//    	ssize=spinnersize;
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

	@Override
	public View getView( final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final int a=arg0;
		final View scoreRow=inflater.inflate(R.layout.itemscore1, null);
			mSpinerPopWindow = new SpinerPopWindow(context);
			mSpinerPopWindow.setItemListener(this);
//			if(inpuThread==null){
//			inpuThread.start();
//			}
			if(mTestThread==null){
				Log.i("PPPPPPPPPPPPPPPPPPPP","HAHAHAHHAHAHHA");
				   mTestThread=new TestThread();
					mTestThread.start();
			}
			TableResizeLayout relativeLayout = (TableResizeLayout)scoreRow.findViewById(R.id.table1);
			relativeLayout.setOnResizeListener(this);
		   int i1= 9;
			for(int i=0;i<i1;i++){
				if(i%9==0){
					List<String> tableitem=(List<String>) videoItems.get(arg0).get("2");
					TextView tView1=new TextView(context);
					//tView1.setBackgroundResource(R.drawable.et_border);
					 tView1.setText(videoItems.get(arg0).get("1").toString());//设置内容 
					 tView1.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
					 tView1.setTextColor(context.getResources().getColor(R.color.temple));//设置字体颜色 
					 tView1.setWidth(180);
					 tView1.setTypeface(font);
					 tView1.setBackgroundResource(R.drawable.et_border);
					 TextPaint tp1 =  tView1.getPaint(); 
						tp1.setFakeBoldText(true);
						if(tableitem.size()>4){
							 tView1.setHeight(44*tableitem.size());
						}else if(tableitem.size()>2&&tableitem.size()<4){
							 tView1.setHeight(43*tableitem.size());
						}else{
							 tView1.setHeight(40*tableitem.size());
						}
					
					 tView1.setPadding(5,5,5,5);//设置四周留白 
					 tView1.setGravity(Gravity.CENTER); 
//					tableRow.addView(tView1);
					TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
					tl.addView(tView1);
				
//					Btn[i].addView(btn1[i]);
					
				
				}else if(i%9==1){
//					btn1[i-1].addView(Btn[i]);
					// TableRow tableRow =(TableRow)view.findViewWithTag("tablerow"+i/3);
				//	 tableRow.setGravity(Gravity.LEFT);
					List<String> tableitem=(List<String>) videoItems.get(arg0).get("2");
					for(int j=0;j<tableitem.size();j++){
					
						TableRow tr=new TableRow(context);
						tr.setPadding(5, 2, 5, 5);
						//tr.setBackgroundResource(R.drawable.et_border);
						TextView tView=new TextView(context);
						 tView.setText(tableitem.get(j).toString());//设置内容 
						 tView.setTypeface(font);
						 tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
						 tView.setTextColor(context.getResources().getColor(R.color.temple));//设置字体颜色 
						 tView.setWidth(350);
						 TextPaint tp1 =  tView.getPaint(); 
							tp1.setFakeBoldText(true);
						 tView.setPadding(5,5,5,5);//设置四周留白 
						 tView.setGravity(Gravity.LEFT); 
						tr.addView(tView);
						TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
						tl.addView(tr);
				
					}
					
				}else if(i%9==2){
					List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
for(int j=0;j<tablescore.size();j++){
						
						TableRow tr=new TableRow(context);
						tr.setPadding(5, 2, 5, 5);
						//tr.setBackgroundResource(R.drawable.et_border);
						TextView tView=new TextView(context);
						tView.setText(tablescore.get(j).toString());//设置内容 
						 tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
						 tView.setTextColor(context.getResources().getColor(R.color.temple));//设置字体颜色 
						 tView.setWidth(120);
						 tView.setTypeface(font);
						 TextPaint tp1 =  tView.getPaint(); 
							tp1.setFakeBoldText(true);
						 tView.setPadding(5,5,5,5);//设置四周留白 
						 tView.setGravity(Gravity.CENTER); 
						tr.addView(tView);
						TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
						tl.addView(tr);
				}
				
				//	btn1[i-2].addView(Btn[i]);
					}
				else if(i%9==3){
					List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
for(int j=0;j<tablescore.size();j++){
						
						TableRow tr=new TableRow(context);
						tr.setMinimumHeight(30);
						tr.setPadding(5, 2, 5, 5);
						//tr.setBackgroundResource(R.drawable.et_border);
						TextView tView=new TextView(context);
						tView.setWidth(110);
						tView.setHeight(37);
						
						 TextPaint tp1 =  tView.getPaint(); 
							tp1.setFakeBoldText(true);
							tView.setTag("getscore"+j);
							final HashMap<String, Object> mapcontentlist = (HashMap<String, Object>) videoItems.get(arg0).get("4");
							if(mapcontentlist!=null){
							if(mapcontentlist.containsKey(String.valueOf(j))){
								
								tView.setText(mapcontentlist.get(String.valueOf(j)).toString());
							}else{
								//button.setBackgroundResource(R.drawable.btndui);
							}
							}
						
						tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
						 tView.setTextColor(context.getResources().getColor(R.color.temple));
						 tView.setBackgroundResource(R.drawable.tv_border);//设置字体颜色 
						 tView.setPadding(5,10,5,5);//设置四周留白 
						 tView.setGravity(Gravity.CENTER); 
						ImageButton im=new ImageButton(context);
						im.setBackgroundResource(R.drawable.down_arrow);
						im.setTag("down"+j);
						im.setMaxWidth(38);
						im.setMaxHeight(25);
							 tView.setPadding(0,5,0,5);//设置四周留白 
						tr.addView(tView);
						tr.addView(im);
						TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
						tl.addView(tr);
				}
				
				//	btn1[i-2].addView(Btn[i]);
					}
				else if(i%9==4){
					List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
					for(int j=0;j<tablescore.size();j++){
											
											TableRow tr=new TableRow(context);
											tr.setPadding(5, 5, 5, 5);
											tr.setMinimumHeight(39);
											//tr.setBackgroundResource(R.drawable.et_border);
											Button button=new Button(context);
											button.setTag("add"+j);
											button.setWidth(42);
											button.setHeight(32);
											button.setBackgroundResource(R.drawable.scoreadd);
											button.setPadding(30,15,0,5);
											tr.addView(button);
											TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
											tl.addView(tr);
									}
				//	btn1[i-2].addView(Btn[i]);
					}
				else if(i%9==5){
					List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
					for(int j=0;j<tablescore.size();j++){
											
											TableRow tr=new TableRow(context);
											tr.setPadding(5, 5, 5, 5);
											tr.setMinimumHeight(39);
											//tr.setBackgroundResource(R.drawable.et_border);
											Button button=new Button(context);
											button.setTag("reduce"+j);
											button.setBackgroundResource(R.drawable.scorereduce);
											button.setWidth(42);
											button.setHeight(32);
											button.setPadding(30,5,0,5);
											tr.addView(button);
											TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
											tl.addView(tr);
									}
//				
				//	btn1[i-2].addView(Btn[i]);
					}
				else if(i%9==6){
					List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
					for(int j=0;j<tablescore.size();j++){
											
											TableRow tr=new TableRow(context);
											tr.setPadding(5, 6, 5, 5);
											tr.setMinimumHeight(39);
											//tr.setBackgroundResource(R.drawable.et_border);
											Button button=new Button(context);
											button.setTag("dui"+j);
											final HashMap<String, Object> maprightlist= (HashMap<String, Object>) videoItems.get(arg0).get("5");
											if(maprightlist!=null){
								
												if(maprightlist.containsKey(String.valueOf(j))){
												if(maprightlist.get(String.valueOf(j)).equals("true")){
													button.setBackgroundResource(R.drawable.btnduis);
												}else{
													button.setBackgroundResource(R.drawable.btndui);
												}
												
											}else{
												button.setBackgroundResource(R.drawable.btndui);
											}
											}else{
												button.setBackgroundResource(R.drawable.btndui);
											}
										button.setWidth(33);
										button.setHeight(33);
											button.setPadding(30,5,0,5);
											tr.addView(button);
											TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
											tl.addView(tr);
									}
//				
				//	btn1[i-2].addView(Btn[i]);
					}
				else if(i%9==7){
					List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
					for(int j=0;j<tablescore.size();j++){
											
											TableRow tr=new TableRow(context);
											tr.setPadding(5, 6, 5, 5);
											tr.setMinimumHeight(39);
											//tr.setBackgroundResource(R.drawable.et_border);
											Button button=new Button(context);
											button.setTag("cuo"+j);
											final HashMap<String, Object> maprightlist= (HashMap<String, Object>) videoItems.get(arg0).get("5");
											if(maprightlist!=null){
											if(maprightlist.containsKey(String.valueOf(j))){
												if(maprightlist.get(String.valueOf(j)).equals("false")){
													button.setBackgroundResource(R.drawable.btncuos);
												}else{
													button.setBackgroundResource(R.drawable.btncuo);
												}
												
											}else{
												button.setBackgroundResource(R.drawable.btncuo);
											}
											}else{
												button.setBackgroundResource(R.drawable.btncuo);
											}
											button.setWidth(33);
											button.setHeight(33);
											button.setPadding(30,5,0,5);
											tr.addView(button);
											TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
											tl.addView(tr);
									}
//				
//				}
				
				//	btn1[i-2].addView(Btn[i]);
					}
				else if(i%9==8){
					List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
					for(int j=0;j<tablescore.size();j++){
											
											TableRow tr=new TableRow(context);
											tr.setPadding(5, 7, 5, 5);
											tr.setMinimumHeight(39);
											//tr.setBackgroundResource(R.drawable.et_border);
											EditText button=new EditText(context);
											button.setBackgroundResource(R.drawable.et_border);
											button.setTag("commment"+j);
										button.setWidth(150);
										button.setHeight(32);
										 button.setTypeface(font);
										 TextPaint tp1 =  button.getPaint(); 
											tp1.setFakeBoldText(true);
											button.setHint("评论内容");
											
											if(videoItems.get(arg0).containsKey("8")){
												final HashMap<String, Object> mapeditlist=(HashMap<String, Object>) videoItems.get(arg0).get("8");
												if(mapeditlist.containsKey(String.valueOf(j))){
													button.setText(mapeditlist.get(String.valueOf(j)).toString());
													
												}else{
													//button.setBackgroundResource(R.drawable.btncuo);
												}
												
												
											}else{
//												button.setText("");
											}
											button.setGravity(Gravity.LEFT);
											//button.setFocusable(false);
										//button.setFocusableInTouchMode(false);
											button.setTextSize(TypedValue.COMPLEX_UNIT_PT, size-1);//设置字体大小 
											button.setTextColor(context.getResources().getColor(R.color.temple));
											button.setPadding(5,0,0,5);
											button.setLines(1);
											tr.addView(button);
											TableLayout tl=(TableLayout)scoreRow.findViewWithTag("tablelayout"+i);
											tl.addView(tr);
									}
//				
				
				//	btn1[i-2].addView(Btn[i]);
					}
				
			}
			final List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
			final List<String> tablesitemcore1=new ArrayList<String>();
			final List<String> tablesitemcore2=new ArrayList<String>();
		final HashMap<String, Object> mapcontentlist ;
			final HashMap<String, Object> maprightlist;
			final HashMap<String, Object> mapeditlist;
			final List<String> tablesitemcore5=new ArrayList<String>();
			final List<String> tablesitemcore6=new ArrayList<String>();
			final List<String> tablesitemcore7=new ArrayList<String>();
			final List<String> tablesitemcore8=new ArrayList<String>();
			if(videoItems.get(arg0).containsKey("4")){
				mapcontentlist=(HashMap<String, Object>) videoItems.get(arg0).get("4");
			}else{
				mapcontentlist = new HashMap<String, Object>();
			}
			if(videoItems.get(arg0).containsKey("5")){
				maprightlist=(HashMap<String, Object>) videoItems.get(arg0).get("5");
			}else{
				maprightlist = new HashMap<String, Object>();
			}
			if(videoItems.get(arg0).containsKey("8")){
				mapeditlist=(HashMap<String, Object>) videoItems.get(arg0).get("8");
			}else{
				mapeditlist = new HashMap<String, Object>();
			}
			for(int j=0;j<tablescore.size();j++){
				final int f=j;
				Button button=(Button)scoreRow.findViewWithTag("add"+j);
				button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String aString=(String) v.getTag();
						String[]b=aString.split("add");
						int c=Integer.parseInt(b[1]);
						List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
						
						TextView tView=(TextView)scoreRow.findViewWithTag("getscore"+c);
//						final List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
						if(videoItems.get(a).containsKey("4")){
							//final List<String> tablescore1=(List<String>) videoItems.get(arg0).get("4");
							if(tView.getText().equals("")){
								tView.setText(tablescore.get(c).toString());
							//	tablesitemcore1.add(f, tablescore.get(f).toString());
								mapcontentlist.put(String.valueOf(c),tablescore.get(c).toString());
								videoItems.get(a).put("4", mapcontentlist);
//								tablesitemcore2.add(f, "");
								maprightlist.put(String.valueOf(f),"");
							}else{
								
								if(Integer.parseInt(tView.getText().toString())==Integer.parseInt(tablescore.get(c).toString())){
									mapcontentlist.put(String.valueOf(c),tablescore.get(c).toString());
									videoItems.get(a).put("4", mapcontentlist);
								}else{
									int num=Integer.parseInt(tView.getText().toString());
									tView.setText(String.valueOf(num+1));
								
								//	tablesitemcore1.add(f, String.valueOf(num+1));
									mapcontentlist.put(String.valueOf(c),String.valueOf(num+1));
									videoItems.get(a).put("4", mapcontentlist);
								}
							}
							//tView.setText(tablescore1.get(f));
						}else{
							if(tView.getText().equals("")){
								tView.setText(tablescore.get(c).toString());
							//	tablesitemcore1.add(f, tablescore.get(f).toString());
								mapcontentlist.put(String.valueOf(f),tablescore.get(c).toString());
								videoItems.get(a).put("4", mapcontentlist);
								maprightlist.put(String.valueOf(c),"");
							}else{
								
								if(Integer.parseInt(tView.getText().toString())==Integer.parseInt(tablescore.get(c).toString())){
									mapcontentlist.put(String.valueOf(c),tablescore.get(c).toString());
									videoItems.get(a).put("4", mapcontentlist);
								}else{
									int num=Integer.parseInt(tView.getText().toString());
									tView.setText(String.valueOf(num+1));
								
								//	tablesitemcore1.add(f, String.valueOf(num+1));
									mapcontentlist.put(String.valueOf(c),String.valueOf(num+1));
									videoItems.get(a).put("4", mapcontentlist);
								}
							}
					}
					}
				});
				Button button1=(Button)scoreRow.findViewWithTag("reduce"+j);
				button1.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String aString=(String) v.getTag();
						String[]b=aString.split("reduce");
						int c=Integer.parseInt(b[1]);
						TextView tView=(TextView)scoreRow.findViewWithTag("getscore"+f);
						if(tView.getText().equals("")||tView.getText().equals("0")){
							mapcontentlist.put(String.valueOf(c),"0");
							videoItems.get(a).put("4", mapcontentlist);
						}else{
							int num=Integer.parseInt(tView.getText().toString());
							tView.setText(String.valueOf(num-1));
							//tablesitemcore1.add(f, String.valueOf(num-1));
							mapcontentlist.put(String.valueOf(c),String.valueOf(num-1));
							videoItems.get(a).put("4", mapcontentlist);
						}
					}
				});
				final Button button2=(Button)scoreRow.findViewWithTag("dui"+j);
				button2.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String aString=(String) v.getTag();
						String[]b=aString.split("dui");
						int c=Integer.parseInt(b[1]);
						TextView tView=(TextView)scoreRow.findViewWithTag("getscore"+c);
						button2.setBackgroundResource(R.drawable.btnduis);
						Button button1=(Button)scoreRow.findViewWithTag("cuo"+c);
						button1.setBackgroundResource(R.drawable.btncuo);
						List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
						tView.setText(tablescore.get(c));
						//tablesitemcore1.add(f, tablescore.get(f));
						mapcontentlist.put(String.valueOf(c),tablescore.get(c));
						//tablesitemcore2.add(f, "true");
						maprightlist.put(String.valueOf(c),"true");
						//tablesitemcore6.add(f, "false");
						videoItems.get(a).put("4", mapcontentlist);
						videoItems.get(a).put("5", maprightlist);
					}
				});
				final Button button3=(Button)scoreRow.findViewWithTag("cuo"+j);
				button3.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String aString=(String) v.getTag();
						String[]b=aString.split("cuo");
						int c=Integer.parseInt(b[1]);
						TextView tView=(TextView)scoreRow.findViewWithTag("getscore"+c);
						Button button1=(Button)scoreRow.findViewWithTag("dui"+c);
						button1.setBackgroundResource(R.drawable.btndui);
						button3.setBackgroundResource(R.drawable.btncuos);
						tView.setText("0");
						//tablesitemcore1.add(f, "0");
						mapcontentlist.put(String.valueOf(c),"0");
						//tablesitemcore2.add(f, "false");
						maprightlist.put(String.valueOf(c),"false");
						//tablesitemcore8.add(f, "false");
						videoItems.get(a).put("4", mapcontentlist);
						videoItems.get(a).put("5", maprightlist);
//						videoItems.get(a).put("6", tablesitemcore8);
					}
				});
				final EditText editText=(EditText)scoreRow.findViewWithTag("commment"+j);
//				editText.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						editText.setFocusable(true);
//						editText.setFocusableInTouchMode(true);
//						 editText.requestFocus();
//						
//					}
//				});
				editText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

				
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (actionId == EditorInfo.IME_MASK_ACTION) {
//							 View view = ((Activity) context).getWindow().peekDecorView();
								Log.i("OOOOOOOOOOOOOOO", String.valueOf(actionId));
//							  InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
//								Log.i("OOOOOOOOOOOOOOO", String.valueOf(actionId));
//					            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); 
//					        	Log.i("OOOOOOOOOOOOOOO", String.valueOf(actionId));
//					            HashMap<String, Object> map=(HashMap<String, Object>) videoItems.get(arg0).get("8");
//					        	Log.i("OOOOOOOOOOOOOOO", String.valueOf(actionId));
//					            Log.i("QQQQQQQQQQQQQ", map.get(String.valueOf(editrow)).toString());
//					            v.setText(map.get(String.valueOf(editrow)).toString());
//					            v.clearFocus();
					           
						}else if(actionId == EditorInfo.IME_NULL){
							 View view = ((Activity) context).getWindow().peekDecorView();
							  InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
							   v.clearFocus();
							   HashMap<String, Object> map=(HashMap<String, Object>) videoItems.get(arg0).get("8");
					            v.setText(map.get(String.valueOf(editrow)).toString());
					         
					            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); 
					          
						}
						return false;
					}
				});
				editText.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
		  				case MotionEvent.ACTION_DOWN: {
//		  					EditText editText=(EditText)scoreRow.findViewWithTag("commment"+editrow);
//		  					editText.setFocusable(true); 
//		  					editText.setFocusableInTouchMode(true);
//		  					editText1=editText;
		  				
		  				editText1=editText;
//		  				 Handler x = new Handler();
//					        x.postDelayed(new splashhandler(), 1000); //延时2
//		  					 onFocusChange(editText.isFocused()); 

//		  					 final InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//							 imm.toggleSoftInputFromWindow(editText.getWindowToken(), 0,0);
//		  					editText.setFocusable(true);
//							editText.setFocusableInTouchMode(true);
							  //editText.requestFocus();
								// editText1=editText;
//								 Handler x = new Handler();
//							        x.postDelayed(new splashhandler(), 1000); //延时2
//							 InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
//							 imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);   
//							 final InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//							 imm.toggleSoftInputFromWindow(editText.getWindowToken(), 0,0);
		  					break;
		  				}

		  				case MotionEvent.ACTION_MOVE: {
		  					break;
		  				}

		  				case MotionEvent.ACTION_UP: {
		  					String aString=(String) v.getTag();
							String[]b=aString.split("commment");
							editrow=Integer.parseInt(b[1]);

		  					break;
		  				}
		  				}
						 
						 Handler x = new Handler();
					        x.postDelayed(new splashhandler(), 100); 
					        //editText.requestFocus();
						return false;
					}
				});
//				editText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
//				    @Override  
//				    public void onFocusChange(View v, boolean hasFocus) {  
//				        if(hasFocus) {
//				        	editText1=editText;
//							 Handler x = new Handler();
//						        x.postDelayed(new splashhandler(), 1000); //延时2
//				} else {
//				// 此处为失去焦点时的处理内容
//				}
//				    }
//				});
				editText.addTextChangedListener(new TextWatcher(){  
					  
			            @Override  
			            public void afterTextChanged(Editable s) {  
			                Log.d("shizheshizheshizheshizheshizhe", "afterTextChanged");  
			                //editText1.clearFocus();
			            }  
			  
			            @Override  
			            public void beforeTextChanged(CharSequence s, int start, int count,  
			                    int after) {  
//			                Log.d(TAG, "beforeTextChanged:" + s + "-" + start + "-" + count + "-" + after);  
			            
			            }  
			  
			            @Override  
			            public void onTextChanged(CharSequence s, int start, int before,  
			                    int count) {  
//			                Log.d(TAG, "onTextChanged:" + s + "-" + "-" + start + "-" + before + "-" + count);  
//			                mTextView.setText(s);
			            	 //tablesitemcore5.add(f, editText.getText().toString());
			            	Log.i("111111111111111", String.valueOf(editrow));
			            	
			            	 mapeditlist.put(String.valueOf(editrow), s.toString());
			            	 Log.i("22222222222222", mapeditlist.toString());
			            	videoItems.get(arg0).put("8", mapeditlist);
			            	//editText1.clearFocus();
			            }  
			              
			        });  
			
				ImageButton imageButton=(ImageButton)scoreRow.findViewWithTag("down"+j);
				imageButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String aString=(String) v.getTag();
						String[]b=aString.split("down");
						int c=Integer.parseInt(b[1]);
						  allcountries = new ArrayList<String>();
						 List<String>list=(List<String>) videoItems.get(arg0).get("3");
						  for(int i=0;i<=Integer.parseInt(list.get(c));i++){
						   allcountries.add(i, String.valueOf(i));
						  }
							TextView tView=(TextView)scoreRow.findViewWithTag("getscore"+c);
						mSpinerPopWindow.refreshData(allcountries, allcountries.size()-1);
						mSpinerPopWindow.setWidth(tView.getWidth());
						mSpinerPopWindow.showAsDropDown(tView);
						num=c;
						row=arg0;
						mTView=tView;
					}
				});
			}
//			for(int j=0;j<tablescore.size();j++){
//				TextView tView=(TextView)scoreRow.findViewWithTag("getscore"+j);
//				if(videoItems.get(arg0).containsKey("4")){
//					final List<String> tablesitemcore3=(List<String>) videoItems.get(arg0).get("4");
//					tView.setText(tablesitemcore3.get(j));
//				}
//				Button button1=(Button)scoreRow.findViewWithTag("dui"+j);
//				Button button2=(Button)scoreRow.findViewWithTag("cuo"+j);
//				if(videoItems.get(arg0).containsKey("5")){
//					final List<String> tablesitemcore4=(List<String>) videoItems.get(arg0).get("5");
//					if(tablesitemcore4.get(j).equals("true")){
//						button1.setBackgroundResource(R.drawable.btnduis);
//						button2.setBackgroundResource(R.drawable.btncuo);
//					}else if(tablesitemcore4.get(j).equals("false")){
//						button1.setBackgroundResource(R.drawable.btndui);
//						button2.setBackgroundResource(R.drawable.btncuos);
//					}
//				}
//			}
//	 showoperationmethod=(TextView)scoreRow.findViewById(R.id.txvshowoperationmethod);
//	 showoperationcontent=(TextView)scoreRow.findViewById(R.id.txvshowoperationcontent);
//	 showallotedscore=(TextView)scoreRow.findViewById(R.id.txvshowallotedscore);
//	 contentEditText=(EditText)scoreRow.findViewById(R.id.editcontent);
//	// showoperationmethod.setTextSize(size);
//	 showoperationmethod.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//		showoperationcontent.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//		showallotedscore.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//		 contentEditText.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
////		final Spinner  spinner = (Spinner)scoreRow.findViewById(R.id.Spinner02);  
//		mTView = (TextView)scoreRow.findViewById(R.id.tv_scorevalue);
//		mBtnDropDown = (ImageButton)scoreRow.findViewById(R.id.bt_scoredropdown);
//		mSpinerPopWindow = new SpinerPopWindow(context);
//		mTView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//		//Typeface face = Typeface.createFromAsset (context.getAssets() , "MSYH.zip" );
//		 showoperationmethod.setTypeface(font);
//			showoperationcontent.setTypeface(font);
//			showallotedscore.setTypeface(font);
//			mTView.setTypeface(font);
//			 contentEditText.setTypeface(font);
//			 TextPaint tp1 = showoperationmethod.getPaint(); 
//				tp1.setFakeBoldText(true);
//				 TextPaint tp2 =  showoperationcontent.getPaint(); 
//					tp2.setFakeBoldText(true);
//					 TextPaint tp3 =  showallotedscore.getPaint(); 
//						tp3.setFakeBoldText(true);
//						 TextPaint tp4 =  mTView.getPaint(); 
//							tp4.setFakeBoldText(true);
//							 TextPaint tp5 =   contentEditText.getPaint(); 
//								tp5.setFakeBoldText(true);
//								if(videoItems.get(arg0).containsKey("9")){
//								contentEditText.setText(videoItems.get(arg0).get("9").toString());
//								}else{
//									
//								}
//			 contentEditText.addTextChangedListener(new TextWatcher(){  
//				  
//		            @Override  
//		            public void afterTextChanged(Editable s) {  
////		                Log.d(TAG, "afterTextChanged");  
//		            }  
//		  
//		            @Override  
//		            public void beforeTextChanged(CharSequence s, int start, int count,  
//		                    int after) {  
////		                Log.d(TAG, "beforeTextChanged:" + s + "-" + start + "-" + count + "-" + after);  
//		                  
//		            }  
//		  
//		            @Override  
//		            public void onTextChanged(CharSequence s, int start, int before,  
//		                    int count) {  
////		                Log.d(TAG, "onTextChanged:" + s + "-" + "-" + start + "-" + before + "-" + count);  
////		                mTextView.setText(s);  
//		            	videoItems.get(arg0).put("9", s);
//		            }  
//		              
//		        });  
////			 contentEditText
////				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
////
////					@Override
////					public boolean onEditorAction(TextView v, int actionId,
////							KeyEvent event) {
////						if (actionId == EditorInfo.IME_ACTION_DONE) {
////							videoItems.get(arg0).put("2", contentEditText.getText().toString());
//////							
////		            	   
////		            	  
////		            
////							contentEditText.setFocusable(false);
////							contentEditText.setFocusableInTouchMode(false);
////							//showmessage();
////						} else if (actionId == EditorInfo.IME_ACTION_NEXT) {
////							videoItems.get(arg0).put("2", contentEditText.getText().toString());
//////							
////							contentEditText.setFocusable(false);
////							contentEditText.setFocusableInTouchMode(false);
////						}
////						return false;
////					}
////				});
//		mBtnDropDown.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				
//				 ArrayList<String> allcountries = new ArrayList<String>();
//				  for(int i=0;i<=Integer.parseInt(videoItems.get(arg0).get("1").toString());i++){
//				   allcountries.add(i, String.valueOf(i));
//				  }
//				  mTView = (TextView)scoreRow.findViewById(R.id.tv_scorevalue);
//				mSpinerPopWindow.refreshData(allcountries, allcountries.size()-1);
//				mSpinerPopWindow.setWidth(mTView.getWidth());
//				mSpinerPopWindow.showAsDropDown(mTView);
//				num=arg0;
//			}
//		});
//		
//		Button addButton=(Button)scoreRow.findViewById(R.id.btn_scoreadd);
//		Button reduceButton=(Button)scoreRow.findViewById(R.id.btn_scorereduce);
//		   btndui=(Button)scoreRow.findViewById(R.id.btn_scoredui);
//			if(videoItems.get(arg0).get("6").equals("false")){
//				btndui.setBackgroundResource(R.drawable.btndui);
//			}else{
//				btndui.setBackgroundResource(R.drawable.btnduis);
//			}
//			  btncuo=(Button)scoreRow.findViewById(R.id.btn_scorecuo);
//			  if(videoItems.get(arg0).get("7").equals("false")){
//				  btncuo.setBackgroundResource(R.drawable.btncuo);
//				}else{
//					btncuo.setBackgroundResource(R.drawable.btncuos);
//				}
//		showoperationmethod.setText(String.valueOf(arg0+1));
//		showoperationcontent.setText(videoItems.get(arg0).get("0").toString());
//		showallotedscore.setText(videoItems.get(arg0).get("1").toString());
//		allcountries = new ArrayList<String>();
//		  for(int i=0;i<=Integer.parseInt(videoItems.get(arg0).get("1").toString());i++){
//		   allcountries.add(i, String.valueOf(i));
//		  }
////			mSpinerPopWindow = new SpinerPopWindow(context);
////			mSpinerPopWindow.refreshData(allcountries, allcountries.size()-1);
//			mSpinerPopWindow.setItemListener(this);
//	//mTView.setText(allcountries.get(allcountries.size()-1));
//		  SpinnerAdapter adapter = new SpinnerAdapter(context,
//		            android.R.layout.simple_spinner_item, allcountries,ssize);
////		  ArrayAdapter<String> adapter= new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,allcountries);
////		  RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams (width,height);  
////	        btParams.setMargins(850, 0, 0, 0);
////	        spinner.setLayoutParams(btParams);
////		  spinner.setAdapter(adapter);
//		  if(videoItems.get(arg0).containsKey("5")){
//			  mTView.setText(allcountries.get(Integer.parseInt(videoItems.get(arg0).get("5").toString())));
//		  }else{
//		  mTView.setText("");
//		  videoItems.get(arg0).put("4","");
//		  
//		 // videoItems.get(arg0).put("5","");
//		  }
////	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
////
////		@Override
////		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
////				long arg3) {
////			 videoItems.get(a).put("5",arg0.getItemAtPosition(arg2).toString());
////			
////		}
////
////		@Override
////		public void onNothingSelected(AdapterView<?> arg0) {
////			// TODO Auto-generated method stub
////			
////		}
////	});
//		  
//		  addButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				int score;
//				 if(videoItems.get(arg0).containsKey("5")){
//					score=Integer.parseInt((String) videoItems.get(arg0).get("5"));
//				 }else {
//				 score=Integer.parseInt((String) videoItems.get(arg0).get("1"));
//				 }
//				String bb=videoItems.get(arg0).get("4").toString();
//				if(videoItems.get(arg0).get("4").equals("")){
//					videoItems.get(arg0).put("4",videoItems.get(arg0).get("1"));
//					videoItems.get(arg0).put("5",videoItems.get(arg0).get("1"));
//					notifyDataSetChanged();
//				}
//				else if(score==Integer.parseInt(bb)){
////					  CustomDialog customDialog=new CustomDialog(context,new DialogListener() {
////							@Override
////							public void refreshActivity(Object object) {							
////							
////							}
////						}, "已到最高分",false);
////			customDialog.show();
//				}else{
//		//	mTView.setText(String.valueOf(score+1));
//					videoItems.get(arg0).put("4",String.valueOf(score+1));
//					videoItems.get(arg0).put("5",String.valueOf(score+1));
//					notifyDataSetChanged();
//					
//				
//				}
//			}
//		});
//		  reduceButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				int score;
//				 if(videoItems.get(arg0).containsKey("5")){
//			score=Integer.parseInt((String) videoItems.get(arg0).get("5"));
//				 }else{
//					 score=Integer.parseInt((String) videoItems.get(arg0).get("1"));
//				 }
//				String bb=allcountries.get(0);
//				if(videoItems.get(arg0).get("4").equals("")){
//					
//				}
//				else if(score==Integer.parseInt(bb)){
////					  CustomDialog customDialog=new CustomDialog(context,new DialogListener() {
////							@Override
////							public void refreshActivity(Object object) {							
////							
////							}
////						}, "已到最低分",false);
////			customDialog.show();
//				}else{
//					//handler.sendEmptyMessage(score-1);
////					mTView.setText(String.valueOf(score-1));
//					videoItems.get(arg0).put("4",String.valueOf(score-1));
//					videoItems.get(arg0).put("5",String.valueOf(score-1));
//					notifyDataSetChanged();
//					
//			
//				}
//			}
//		});
//		
//		  btndui.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View view) {
//	  videoItems.get(arg0).put("6", "true");
//	  videoItems.get(arg0).put("7", "false");
//	      videoItems.get(arg0).put("4",videoItems.get(arg0).get("1"));
//			videoItems.get(arg0).put("5",videoItems.get(arg0).get("1"));
//			notifyDataSetChanged();
//		
//		
//				}
//			});
//		  btncuo.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				 videoItems.get(arg0).put("6", "false");
//				  videoItems.get(arg0).put("7", "true");
//				 videoItems.get(arg0).put("4","0");
//					videoItems.get(arg0).put("5","0");
//					notifyDataSetChanged();
//				
//			}
//		});
		return scoreRow;
	}
//public void small() {
//	showoperationmethod.setTextSize(14);
//	showoperationcontent.setTextSize(14);
//	showallotedscore.setTextSize(14);
////	scoreRow=inflater.inflate(R.layout.itemscore2, null);
////	scoreRow.requestLayout();
//	//scoreRow.invalidate();
////	notifyDataSetInvalidated();
//}
//public void middle() {
//	showoperationmethod.setTextSize(20);
//	showoperationcontent.setTextSize(20);
//	showallotedscore.setTextSize(20);
////	scoreRow=inflater.inflate(R.layout.itemscore1, null);
////	scoreRow.requestLayout();
//	//scoreRow.invalidate();
////	notifyDataSetInvalidated();
//}
//public void large() {
//	showoperationmethod.setTextSize(26);
//	showoperationcontent.setTextSize(26);
//	showallotedscore.setTextSize(26);
////	scoreRow=inflater.inflate(R.layout.itemscore, null);
////	scoreRow.requestLayout();
//	//scoreRow.invalidate();
////	notifyDataSetInvalidated();
//}
//
//
//
//
private void setHero(int pos){
if (pos >= 0 && pos <= allcountries.size()){
	String value = allcountries.get(pos);
	mTView.setText(value);
}
}
//
//
//
//private void showSpinWindow(){
//mSpinerPopWindow.setWidth(mTView.getWidth());
//mSpinerPopWindow.showAsDropDown(mTView);
//}
//
@Override
public void onItemClick(int pos) {
setHero(pos);
Log.i("PPPPPPPPPPPPPPPYYYYY", String.valueOf(row));
final HashMap<String, Object> mapcontentlist;
if(videoItems.get(row).containsKey("4")){
mapcontentlist = (HashMap<String, Object>) videoItems.get(row).get("4");
}else{
	 mapcontentlist = new HashMap<String, Object>();
}
Log.i("PPPPPPPPPPPPPPP", String.valueOf(pos));
Log.i("TTTTTTTTTTTTT", String.valueOf(num));
mapcontentlist.put(String.valueOf(num), String.valueOf(pos));
videoItems.get(row).put("4",mapcontentlist);
}
class splashhandler implements Runnable{

    public void run() {
    	
//    	activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    	InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); 
    	// 接受软键盘输入的编辑文本或其它视图 
    	inputMethodManager.showSoftInput(editText1,InputMethodManager.SHOW_FORCED); 
    }
    
}
private void onFocusChange(boolean hasFocus) 
{ 
final boolean isFocus = hasFocus; 
(new Handler()).postDelayed(new Runnable() { 
public void run() { 
InputMethodManager imm = (InputMethodManager) 
editText1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
if(isFocus) 
{ 
imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
} 
else 
{ 
imm.hideSoftInputFromWindow(editText1.getWindowToken(),0); 
} 
} 
}, 1000); 
}
@Override
public void OnResize(int w, int h, int oldw, int oldh) {
	if (h < oldh) {
		if(editText1!=null){
			Log.i("PPPPPPPPPPPPPPPPPPPP","HAHAHAHHAHAHHA");
		editText1.setFocusable(false);
		editText1.setFocusableInTouchMode(false);
		}
//		passwordEditText.setFocusable(false);
//		passwordEditText.setFocusableInTouchMode(false);
	}
	
}  
class TestThread extends Thread{
    public void run(){
    	 InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		 boolean isOpen=imm.isActive(); 
		 Log.i("PPPPPPPPPPPPPPPPPPPP","HAHAHAHHAHAHHA");
		 Log.i("PPPPPPPPPPPPPPPPPPPP",String.valueOf(isOpen));
		 if(isOpen==false){
		 if(editText1!=null){
				Log.i("PPPPPPPPPPPPPPPPPPPP","HAHAHAHHAHAHHA");
			editText1.setFocusable(false);
			editText1.setFocusableInTouchMode(false);
			editText1.clearFocus();
			}
    }
}
}
//Thread inpuThread = new Thread(new Runnable() {
//	 @Override
//	 public void run() {
//		 Log.i("PPPPPPPPPPPPPPPPPPPP","HAHAHAHHAHAHHA");
//		 InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
//		 boolean isOpen=imm.isActive(); 
//		 Log.i("PPPPPPPPPPPPPPPPPPPP","HAHAHAHHAHAHHA");
//		 Log.i("PPPPPPPPPPPPPPPPPPPP",String.valueOf(isOpen));
//		 if(isOpen==false){
//		 if(editText1!=null){
//				Log.i("PPPPPPPPPPPPPPPPPPPP","HAHAHAHHAHAHHA");
//			editText1.setFocusable(false);
//			editText1.setFocusableInTouchMode(false);
//			editText1.clearFocus();
//			}
//		 }

//	 }
//});
}
