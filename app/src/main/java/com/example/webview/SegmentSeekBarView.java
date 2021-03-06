package com.example.webview;

import org.xmlpull.v1.XmlPullParser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import android.util.Xml;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.handscore.model.MarkSheet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class SegmentSeekBarView extends LinearLayout{
	private TextView TextValue;
	private TextView segLeftText;
	private TextView segRightText;
	private int groupId;
	private int childId;
	private SeekBar sb;
	private TextView TextContent;
	//private RatingBar rateBar;
	private SimpleRatingBar rateBar;
	private ToggleButton yesOrNo;
	MarkSheet.children_item mci;
	//步长参数
	private String progressStep;
	private String maxScore;
	private String MSI_RealScore;
	private int mflag;
	private boolean mScore;
	//标志初始化，如果为打分制初始化时，分数不赋值
	//private String modelValue;
	
	private onSegmentSeekBarViewClickListener listener;
	public SimpleRatingBar getRateBar() {
		return rateBar;
	}

	public SeekBar getSb() {
		return sb;
	}

	public ToggleButton getYesOrNo() {
		return yesOrNo;
	}

	public void setSb(SeekBar sb) {
		this.sb = sb;
	}

	public void setScore(boolean flag) {
		this.mScore=flag;
	}

	public TextView getTextValue() {
		return TextValue;
	}

	public TextView getDetailValue() {
		return TextContent;
	}

	public void setTextValue(TextView textValue) {
		TextValue = textValue;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

	public String getProgressStep() {
		return progressStep;
	}

	public void setProgressStep(String progressStep) {
		this.progressStep = progressStep;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public String getMSI_RealScore() {
		return MSI_RealScore;
	}

	public void setMSI_RealScore(String mSI_RealScore) {
		MSI_RealScore = mSI_RealScore;
	}
	

	public SegmentSeekBarView(Context context, AttributeSet attrs) {
		super(context,attrs);
		mScore = true;
		//init();
		// TODO 自动生成的构造函数存根
	}

	public SegmentSeekBarView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		//init();
		mScore = true;
	}

	public void setFlag(int flag) {
		this.mflag=flag;
	}

	public void setChildrenItem(MarkSheet.children_item item ) {
		this.mci = new MarkSheet.children_item();
		this.mci=item;
	}

	public void init() {
		 //填充数据，如果有已保存的数据

		if(mflag==0)
		{
			TextValue= new TextView(getContext());
			segLeftText = new TextView(getContext());
			segRightText = new TextView(getContext());
			sb=new SeekBar(getContext());
			TextValue.setTextColor(getResources().getColor(R.color.blue));
			TextValue.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			segLeftText.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			segRightText.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			sb.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3));
			sb.setProgressDrawable(this.getResources().getDrawable(R.drawable.progress_holo_light));
			sb.setThumb(this.getResources().getDrawable(R.drawable.detail_icon_schedule_ball));
			sb.setPadding(30, 0, 30, 0);
//			//设置宽度和高度
//			segLeftText.setWidth(35);
//			segLeftText.setHeight(50);
//			segRightText.setWidth(35);
//			segRightText.setHeight(50);

			segLeftText.setText("+");
			segRightText.setText("-");
			TextValue.setGravity(Gravity.CENTER);


			XmlPullParser xrp = getResources().getXml(R.drawable.seg_text_color_selectorseek);
			try {
				ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
				segLeftText.setTextColor(csl);
				segRightText.setTextColor(csl);
			} catch (Exception e) {
			}
			segLeftText.setGravity(Gravity.CENTER);
			segRightText.setGravity(Gravity.CENTER);
			setSegmentTextSize(23);
			segLeftText.setBackgroundResource(R.drawable.seg_leftseek);
			segRightText.setBackgroundResource(R.drawable.seg_rightseek);
			//segLeftText.setSelected(true);
			this.removeAllViews();
			this.addView(TextValue);
			this.addView(sb);
			this.addView(segLeftText);
			this.addView(segRightText);
			this.invalidate();

			segLeftText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					segLeftText.setSelected(true);
					segRightText.setSelected(false);
					if(sb.getProgress()<sb.getMax())
					{
						sb.incrementProgressBy(1);
					}
					setRealValue();

					if (listener != null) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("totalScore", TextValue.getText().toString());
						listener.onSegmentSeekBarViewClick(groupId,childId,segLeftText, map);
						//listener.onSegmentSeekBarViewClick(TextValue, 0);
					}
				}
			});

			segRightText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					segLeftText.setSelected(false);
					segRightText.setSelected(true);
					if(sb.getProgress()>0)
					{
						sb.setProgress(sb.getProgress()-1);
					}
					setRealValue();
					if (listener != null) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("totalScore", TextValue.getText().toString());
						listener.onSegmentSeekBarViewClick(groupId,childId,segLeftText, map);
						listener.onSegmentSeekBarViewClick(groupId,childId,segRightText,map);
						//listener.onSegmentSeekBarViewClick(TextValue, 0);
					}
				}
			});

			sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO 自动生成的方法存根
					setRealValue();
					if (listener != null) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("totalScore", TextValue.getText().toString());
						listener.onSegmentSeekBarViewClick(groupId,childId,segLeftText, map);
						listener.onSegmentSeekBarViewClick(groupId,childId,sb,  map);
					}
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO 自动生成的方法存根

				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
											  boolean fromUser) {
					// TODO 自动生成的方法存根
					//setRealValue(progress);
					//if (listener != null) {
					//listener.onSegmentSeekBarViewClick(groupId,childId,sb,  TextValue.getText().toString());
					//listener.onSegmentSeekBarViewClick(TextValue, 0);
					//}
				}
			});
		}
		else if(mflag==1)
		{
			TextValue= new TextView(getContext());
			TextValue.setTextColor(getResources().getColor(R.color.blue));
			TextValue.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			TextValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23);
			TextValue.setGravity(Gravity.CENTER);
			//
			XmlPullParser parser = getResources().getXml(R.layout.ratebar);
			AttributeSet attributes = Xml.asAttributeSet(parser);
			int type;
			try{
				while ((type = parser.next()) != XmlPullParser.START_TAG &&
						type != XmlPullParser.END_DOCUMENT) {
					// Empty
				}

				if (type != XmlPullParser.START_TAG) {
					//Log.e("","the xml file is error!\n");
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rateBar = new SimpleRatingBar(getContext(),attributes);
			rateBar.setNumberOfStars(mci.item_detail_list.size());

			LinearLayout.LayoutParams params=new LayoutParams(getContext(),attributes);

			//if rating value is changed,
			//display the current rating value in the result (textview) automatically
			rateBar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
				public void onRatingChanged(SimpleRatingBar ratingBar, float rating,
											boolean fromUser) {
					try {
						int index = (int) rating - 1;
						if (index < 0)
						{
							ratingBar.setRating(1.0f);
							return;
						}
						if (mci != null && mci.item_detail_list != null) {
							if (mci.item_detail_list.size() > 0) {
								MarkSheet.detail_item item = (MarkSheet.detail_item) mci.item_detail_list.get(index);
								TextValue.setText(item.MSIRD_Score);
								TextContent.setText(URLDecoder.decode(item.MSIRD_Item, "UTF-8"));
							}
						}
						if (listener != null) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("totalScore", TextValue.getText().toString());
							map.put("rating", String.valueOf(rating));
							map.put("detailContent", TextContent.getText().toString());
							System.out.println("totalScore is "+TextValue.getText().toString()+",rating is "+String.valueOf(index)+",detailContent is "+TextContent.getText().toString());
							listener.onSegmentSeekBarViewClick(groupId, childId, segLeftText, map);

						}
					}
					catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			});

			TextContent= new TextView(getContext());
			TextContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
			TextContent.setTextColor(getResources().getColor(R.color.blue));
			TextContent.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.2f));
			TextContent.setGravity(Gravity.CENTER);


			this.removeAllViews();
			this.addView(TextValue);

			this.addView(rateBar,params);
			this.addView(TextContent);

			this.invalidate();
		}
		else if(mflag==2)
		{
			TextValue= new TextView(getContext());
			TextValue.setTextColor(getResources().getColor(R.color.blue));
			//TextValue.setWidth(80);
			TextValue.setLayoutParams(new LinearLayout.LayoutParams(90, LayoutParams.WRAP_CONTENT, 1));
			TextValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23);
			TextValue.setGravity(Gravity.CENTER);
			//
			XmlPullParser parser = getResources().getXml(R.layout.checkbox);
			AttributeSet attributes = Xml.asAttributeSet(parser);
			int type;
			try{
				while ((type = parser.next()) != XmlPullParser.START_TAG &&
						type != XmlPullParser.END_DOCUMENT) {
					// Empty
				}

				if (type != XmlPullParser.START_TAG) {
					//Log.e("","the xml file is error!\n");
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			yesOrNo = new ToggleButton(getContext(),attributes);

			LinearLayout.LayoutParams params=new LayoutParams(getContext(),attributes);

			yesOrNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					try {
						int index = 0;
						if (isChecked) {
							index = 1;
						} else {
							index = 0;
						}
						if (mScore==false && index==0)
						{
							AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
							builder.setTitle("提示").setMessage("由于当前计分规则为选择No即视为不合格，用户得分将为0！")
									.setPositiveButton("确定",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialoginterface, int i) {
													//按钮事件
												}
											})
									.show();

						}
						if (mci != null && mci.item_detail_list != null) {
							if (mci.item_detail_list.size() > 0) {
								MarkSheet.detail_item item = (MarkSheet.detail_item) mci.item_detail_list.get(index);
								TextValue.setText(item.MSIRD_Score);
								TextContent.setText(URLDecoder.decode(item.MSIRD_Item, "UTF-8"));
							}
						}
						if (listener != null) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("totalScore", TextValue.getText().toString());
							map.put("yesorno", String.valueOf(index));
							map.put("detailContent", TextContent.getText().toString());
							listener.onSegmentSeekBarViewClick(groupId, childId, segLeftText, map);

						}

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			});

			TextContent= new TextView(getContext());
			TextContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
			TextContent.setTextColor(getResources().getColor(R.color.blue));
			TextContent.setLayoutParams(new LinearLayout.LayoutParams(110, LayoutParams.WRAP_CONTENT, 1.2f));
			TextContent.setGravity(Gravity.CENTER);

			this.removeAllViews();
			this.addView(TextValue);

			this.addView(yesOrNo,params);
			this.addView(TextContent);

			this.invalidate();
		}

	}
//根据步长设置正式分数
	private void setRealValue()
	{		
		 Float realScore=sb.getProgress()*Float.parseFloat(progressStep);		
			 if(realScore<Float.parseFloat(maxScore))
	         {
	      	   TextValue.setText(String.format("%.2f",realScore));
	         }
	         else
	         {
	      	   TextValue.setText(maxScore);
	         }			 
	}
	public void setSegmentTextSize(int dp) {  
		 TextValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
		 segLeftText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
		 segRightText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp); 
	}
		       
	 public void setOnSegmentViewClickListener(onSegmentSeekBarViewClickListener listener) {  
		  this.listener = listener;  
	}
	public void setSegmentText(CharSequence text,int position) {  
		if (position == 0) {  
			segLeftText.setText(text);  
		  }  
		  if (position == 1) {  
			  segRightText.setText(text);
		   }
		}  

	public static interface onSegmentSeekBarViewClickListener{  
		       /** 
		        *  
		        * <p>2014年7月18日</p> 
		        * @param v 
		       * @param position 0-左边 1-右边 
		        * @author RANDY.ZHANG 
		         */
		       public void onSegmentSeekBarViewClick(int groupPosition, int childPosition,View v,HashMap<String, Object> map);
	}  

}
