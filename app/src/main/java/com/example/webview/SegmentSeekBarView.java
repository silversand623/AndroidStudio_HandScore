package com.example.webview;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.CompoundButton;

import com.handscore.model.MarkSheet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class SegmentSeekBarView extends LinearLayout{
	private TextView TextValue;
	private TextView segLeftText;
	private TextView segRightText;
	private int groupId;
	private int childId;
	private SeekBar sb;
	private TextView TextContent;
	private RatingBar rateBar;
	private Switch yesOrNo;
	MarkSheet.children_item mci;
	//步长参数
	private String progressStep;
	private String maxScore;
	private String MSI_RealScore;
	private int mflag;
	//标志初始化，如果为打分制初始化时，分数不赋值
	//private String modelValue;
	
	private onSegmentSeekBarViewClickListener listener;
	public SeekBar getSb() {
		return sb;
	}

	public void setSb(SeekBar sb) {
		this.sb = sb;
	}

	public TextView getTextValue() {
		return TextValue;
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

		//init();
		// TODO 自动生成的构造函数存根
	}

	public SegmentSeekBarView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		//init();
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
			sb.setLayoutParams(new LayoutParams(170, LayoutParams.WRAP_CONTENT, 1));
			sb.setProgressDrawable(this.getResources().getDrawable(R.drawable.progress_holo_light));
			sb.setThumb(this.getResources().getDrawable(R.drawable.detail_icon_schedule_ball));
			sb.setPadding(30, 0, 30, 0);
			//设置宽度和高度
			segLeftText.setWidth(35);
			segLeftText.setHeight(50);
			segRightText.setWidth(35);
			segRightText.setHeight(50);

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
						listener.onSegmentSeekBarViewClick(groupId,childId,segLeftText, TextValue.getText().toString());
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
						listener.onSegmentSeekBarViewClick(groupId,childId,segRightText,TextValue.getText().toString());
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
						listener.onSegmentSeekBarViewClick(groupId,childId,sb,  TextValue.getText().toString());
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
			rateBar = new RatingBar(getContext());
			rateBar.setNumStars(mci.item_detail_list.size());
			rateBar.setStepSize(1.0f);
			rateBar.setRating(0.0f);
			//if rating value is changed,
			//display the current rating value in the result (textview) automatically
			rateBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
											boolean fromUser) {
					try {

						int index = (int) rating - 1;
						if (mci != null && mci.item_detail_list != null) {
							if (mci.item_detail_list.size() > 0) {
								MarkSheet.detail_item item = (MarkSheet.detail_item) mci.item_detail_list.get(index);
								TextValue.setText(item.MSIRD_Score);
								TextContent.setText(URLDecoder.decode(item.MSIRD_Item, "UTF-8"));
							}
						}
						if (listener != null) {
							listener.onSegmentSeekBarViewClick(groupId, childId, segLeftText, TextValue.getText().toString());

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
			TextContent.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			TextContent.setGravity(Gravity.CENTER);

			this.removeAllViews();
			this.addView(TextValue);

			this.addView(rateBar);
			this.addView(TextContent);

			this.invalidate();
		}
		else if(mflag==2)
		{
			TextValue= new TextView(getContext());
			TextValue.setTextColor(getResources().getColor(R.color.blue));
			TextValue.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			TextValue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23);
			TextValue.setGravity(Gravity.CENTER);
			//
			rateBar = new RatingBar(getContext());
			rateBar.setNumStars(mci.item_detail_list.size());
			rateBar.setStepSize(1.0f);
			rateBar.setRating(0.0f);

			yesOrNo = new Switch(getContext());
			//yesOrNo.setChecked(false);

			yesOrNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						//mText.setText("开启");
					} else {
						//mText.setText("关闭");
					}
				}
			});

			TextContent= new TextView(getContext());
			TextContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
			TextContent.setTextColor(getResources().getColor(R.color.blue));
			TextContent.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
			TextContent.setGravity(Gravity.CENTER);

			this.removeAllViews();
			this.addView(TextValue);

			//this.addView(rateBar);
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
		       public void onSegmentSeekBarViewClick(int groupPosition, int childPosition,View v,String TextValueStr);  
	}  

}
