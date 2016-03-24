package com.example.webview.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.webview.LoginActivity;
import com.example.webview.R;

import android.R.integer;
import android.R.raw;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SelectPreviewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HashMap<String, Object>> videoItems;
	private TextView showpreoperationmethod,showpreoperationcontent,showpreallotedscore,txvshowpreallotedcontext;
    LayoutInflater inflater;
    int size;
    Typeface font;
    public SelectPreviewAdapter(Context _context,ArrayList<HashMap<String, Object>> _items,int a,Typeface face){
    	context=_context;
    	videoItems=_items;
    	inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	size=a;
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

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View preViewRow=inflater.inflate(R.layout.itempreview1, null);
		int i1= 4;
		for(int i=0;i<i1;i++){
			if(i%4==0){
				List<String> tableitem=(List<String>) videoItems.get(arg0).get("2");
				TextView tView1=new TextView(context);
				//tView1.setBackgroundResource(R.drawable.et_border);
				 tView1.setText(videoItems.get(arg0).get("1").toString());//设置内容 
				 tView1.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
				 tView1.setTextColor(context.getResources().getColor(R.color.temple));//设置字体颜色 
				 tView1.setWidth(120);
				 tView1.setHeight(52);
				 TextPaint tp1 =  tView1.getPaint(); 
					tp1.setFakeBoldText(true);
					 tView1.setTypeface(font);
						if(tableitem.size()>4){
							 tView1.setHeight(59*tableitem.size());
						}else if(tableitem.size()>2&&tableitem.size()<4){
							 tView1.setHeight(59*tableitem.size());
						}else{
							 tView1.setHeight(59*tableitem.size());
						}
				 tView1.setPadding(5,0,5,5);//设置四周留白 
				 tView1.setGravity(Gravity.CENTER); 
				 tView1.setBackgroundResource(R.drawable.et_border);
//				tableRow.addView(tView1);
				TableLayout tl=(TableLayout)preViewRow.findViewWithTag("tablelayout"+i);
				tl.addView(tView1);
			
//				Btn[i].addView(btn1[i]);
				
			
			}else if(i%9==1){
//				btn1[i-1].addView(Btn[i]);
				// TableRow tableRow =(TableRow)view.findViewWithTag("tablerow"+i/3);
			//	 tableRow.setGravity(Gravity.LEFT);
				List<String> tableitem=(List<String>) videoItems.get(arg0).get("2");
				for(int j=0;j<tableitem.size();j++){
				
					TableRow tr=new TableRow(context);
					tr.setPadding(5, 2, 5, 5);
					//tr.setBackgroundResource(R.drawable.et_border);
					TextView tView=new TextView(context);
					 tView.setText(tableitem.get(j).toString());//设置内容 
					 tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
					 tView.setTextColor(context.getResources().getColor(R.color.temple));//设置字体颜色 
					 tView.setWidth(220);
					 tView.setHeight(52);
					 tView.setTypeface(font);
					 TextPaint tp1 =  tView.getPaint(); 
						tp1.setFakeBoldText(true);
					 tView.setPadding(5,5,5,5);//设置四周留白 
					 tView.setGravity(Gravity.LEFT); 
					tr.addView(tView);
					TableLayout tl=(TableLayout)preViewRow.findViewWithTag("tablelayout"+i);
					tl.addView(tr);
			
				}
				
			}else if(i%9==2){
				HashMap<String, Object> mapcontentlist=(HashMap<String, Object>) videoItems.get(arg0).get("4");
				List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
for(int j=0;j<tablescore.size();j++){
					
					TableRow tr=new TableRow(context);
					tr.setPadding(5, 2, 5, 5);
					//tr.setBackgroundResource(R.drawable.et_border);
					TextView tView=new TextView(context);
					tView.setText(mapcontentlist.get(String.valueOf(j)).toString());//设置内容 
					 tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
					 tView.setTextColor(context.getResources().getColor(R.color.temple));//设置字体颜色 
					 tView.setWidth(100);
					 tView.setHeight(52);
					 tView.setTypeface(font);
					 TextPaint tp1 =  tView.getPaint(); 
						tp1.setFakeBoldText(true);
					 tView.setPadding(5,5,5,5);//设置四周留白 
					 tView.setGravity(Gravity.CENTER); 
					tr.addView(tView);
					TableLayout tl=(TableLayout)preViewRow.findViewWithTag("tablelayout"+i);
					tl.addView(tr);
			}
			
			//	btn1[i-2].addView(Btn[i]);
				}
			else if(i%9==3){
				List<String> tablescore=(List<String>) videoItems.get(arg0).get("3");
for(int j=0;j<tablescore.size();j++){
					
					TableRow tr=new TableRow(context);
					tr.setPadding(5, 2, 5, 5);
					//tr.setBackgroundResource(R.drawable.et_border);
					TextView tView=new TextView(context);
					if( !videoItems.get(arg0).containsKey("8")){
						tView.setText("");
					}else{
						HashMap<String,Object> tabletext= (HashMap<String, Object>) videoItems.get(arg0).get("8");
						if(tabletext.containsKey(String.valueOf(j))){
							tView.setText(tabletext.get(String.valueOf(j)).toString());
						}
					
					}
					 tView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);//设置字体大小 
					 tView.setTextColor(context.getResources().getColor(R.color.temple));//设置字体颜色 
					 tView.setWidth(160);
					 tView.setHeight(52);
					 tView.setTypeface(font);
					 TextPaint tp1 =  tView.getPaint(); 
						tp1.setFakeBoldText(true);
					 tView.setPadding(5,5,5,5);//设置四周留白 
					 tView.setGravity(Gravity.LEFT); 
					tr.addView(tView);
					TableLayout tl=(TableLayout)preViewRow.findViewWithTag("tablelayout"+i);
					tl.addView(tr);
			}
			
			
			//	btn1[i-2].addView(Btn[i]);
				}
			
		}
//	 showpreoperationmethod=(TextView)preViewRow.findViewById(R.id.txvshowpreoperationmethod);
//	 showpreoperationcontent=(TextView)preViewRow.findViewById(R.id.txvshowpreoperationcontent);
//	 showpreallotedscore=(TextView)preViewRow.findViewById(R.id.txvshowpreallotedscore);
//	 txvshowpreallotedcontext=(TextView)preViewRow.findViewById(R.id.txvshowpreallotedcontext);
//	 showpreoperationmethod.setText(String.valueOf(arg0+1));
//	 showpreoperationcontent.setText(videoItems.get(arg0).get("0").toString());
//	 showpreallotedscore.setText(videoItems.get(arg0).get("5").toString());
//	 if(videoItems.get(arg0).containsKey("2")==false){
//		 txvshowpreallotedcontext.setText("");
//	 }else{
//	 txvshowpreallotedcontext.setText(videoItems.get(arg0).get("9").toString());
//	 }
//	 showpreoperationmethod.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//	 showpreoperationcontent.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//	 showpreallotedscore.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//	 txvshowpreallotedcontext.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
//	 
//	 txvshowpreallotedcontext.setTypeface(font);
//	 showpreoperationmethod.setTypeface(font);
//	 showpreoperationcontent.setTypeface(font);
//	 showpreallotedscore.setTypeface(font);
//	 TextPaint tp1 = txvshowpreallotedcontext.getPaint(); 
//		tp1.setFakeBoldText(true);
//		 TextPaint tp2 =  showpreoperationmethod.getPaint(); 
//			tp2.setFakeBoldText(true);
//			 TextPaint tp3 =   showpreoperationcontent.getPaint(); 
//				tp3.setFakeBoldText(true);
//				 TextPaint tp4 =  showpreallotedscore.getPaint(); 
//					tp4.setFakeBoldText(true);
		return preViewRow;
	}
//public void small() {
//	showpreoperationmethod.setTextSize(14);
//	 showpreoperationcontent.setTextSize(14);
//	 showpreallotedscore.setTextSize(14);
//}
//public void middle() {
//	showpreoperationmethod.setTextSize(20);
//	showpreoperationcontent.setTextSize(20);
//	 showpreallotedscore.setTextSize(20);
//}
//public void large() {
//	showpreoperationmethod.setTextSize(26);
//	showpreoperationcontent.setTextSize(26);
//	showpreallotedscore.setTextSize(26);
//}
}
