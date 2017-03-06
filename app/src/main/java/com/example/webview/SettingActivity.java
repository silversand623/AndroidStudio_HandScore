package com.example.webview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    EditText timeInterval;
    private ProgressHUD mProgressHUD;

    private TextView TVBack;
    private TextView TVsubmit;
    int modelValueStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        segmentSet = (SegmentSet) findViewById(R.id.SegmentSetBar);
        eTextInterval = (EditText) findViewById(R.id.eTextInterval);
        timeInterval = (EditText) findViewById(R.id.timeInterval);

        TVBack = (TextView) findViewById(R.id.TVBack);
        TVsubmit = (TextView) findViewById(R.id.TVsubmit);

        SharedPreferences userInfo = getSharedPreferences("user_info", 0);
        //填充数据，如果有已保存的数据
        if (userInfo.contains("timeinterval")) {
            timeInterval.setText(userInfo.getString("timeinterval", null));
        }
        if (userInfo.contains("progressStep")) {
            eTextInterval.setText(userInfo.getString("progressStep", null));
        }
        if (userInfo.contains("modelValue")) {
            String tempValue = userInfo.getString("modelValue", null);
            modelValueStr = Integer.parseInt(tempValue);
            if (tempValue.equals("0")) {
                segmentSet.getSegLeftText().setSelected(true);
                segmentSet.getSegRightText().setSelected(false);
            } else {
                segmentSet.getSegLeftText().setSelected(false);
                segmentSet.getSegRightText().setSelected(true);
            }

        }
        //设置显示文字
        segmentSet.setSegmentText("打分制", 0);
        segmentSet.setSegmentText("扣分制", 1);
        segmentSet.setOnSegmentViewClickListener(new SegmentSet.onSegmentViewClickListener() {

            @Override
            public void onSegmentViewClick(View v, int position) {
                // TODO 自动生成的方法存根
                //在此可以获得用户点击的数据
                modelValueStr = position;

            }
        });
        eTextInterval.addTextChangedListener(textWatcher);


        //返回按钮
        TVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回时关闭本界面
                SettingActivity.this.finish();
            }
        });
        //保存设置数据
        TVsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                try {
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

                    if (timeInterval.getText().toString().equals(""))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setTitle("输入范围错误").setMessage("请输入正确的值")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                //按钮事件
                                            }
                                        })
                                .show();
                        return;
                    }
                    GlobalSetting myApp = (GlobalSetting) getApplication();
                    Date dateStart = new Date();
                    Date dateEnd = new Date();
                    String sStartTime = "";
                    String sEndTime = "";
                    SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
                    for (HashMap<String, Object> map : myApp.gStudnetArray) {
                        sStartTime = map.get("itemTime").toString();
                        sEndTime = map.get("itemEndTime").toString();
                        break;
                    }
                    dateStart = format.parse(sStartTime);
                    dateEnd = format.parse(sEndTime);
                    long lInterval1 = dateEnd.getTime() - dateStart.getTime();
                    long lTime = lInterval1/(1000*60);

                    long lTemp = Long.parseLong(timeInterval.getText().toString());
                    if (lTemp > lTime)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setTitle("输入范围错误").setMessage("输入时间超出考试时间范围")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                //按钮事件
                                            }
                                        })
                                .show();
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

                    if (userInfo.contains("timeinterval")) {
                        String tempValue=userInfo.getString("timeinterval",null);
                        String sTime = timeInterval.getText().toString();
                        if(!tempValue.equals(sTime))
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
                                        .putString("timeinterval", timeInterval.getText().toString())
                                        .commit();

                                SettingActivity.this.finish();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }else
                    {
                        SettingActivity.this.finish();
                    }

                }catch (ParseException e1)
                {

                }
            }
        });

    }


    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String AllNumber = s.toString();
            if (AllNumber.length() > 0) {
                if (AllNumber.indexOf(".") != -1) {
                    String dotNumber = AllNumber.substring(AllNumber.indexOf("."));
                    if (dotNumber.length() > 2) {
                        DialogAlert("只能精确到一位小数。");
                        //截取小数点后一位赋值给文本框
                        eTextInterval.setText(AllNumber.substring(0, AllNumber.indexOf(".") + 2));
                    }
                }

            }

        }
    };

    public void DialogAlert(String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("提示").setMessage(Message)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
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
