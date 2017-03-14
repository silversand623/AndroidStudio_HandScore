package com.example.webview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.example.webview.tools.DialogListener;
import com.example.webview.tools.WritePadDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.handscore.model.LoginInfoType;
import com.handscore.model.MarkSheet;
import com.handscore.model.PublicPara;
import com.handscore.model.StudentInfo;
import com.handscore.model.MarkSheet.Items;
import com.handscore.model.MarkSheet.MarkData;
import com.handscore.model.MarkSheet.children_item;
import com.handscore.model.StudentInfo.Student;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreViewActivity extends Activity {
    private Bitmap mSignBitmap;
    private String signPath;
    private ImageView ivSign;
    private TextView tvSign;
    private TextView TVBack;
    private TextView TVsubmit;
    private TextView PingFenBiaoName;
    //加载列表信息
    private ExpandableListView expandableListView;
    private LoginInfoType loginItem;
    private ProgressHUD mProgressHUD;
    private MyExpandableListViewAdapter adapter;
    MarkSheet Infos;
    private float nSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_view);
        ivSign = (ImageView) findViewById(R.id.iv_sign);
        tvSign = (TextView) findViewById(R.id.tv_sign);
        TVBack = (TextView) findViewById(R.id.TVBack);
        TVsubmit = (TextView) findViewById(R.id.TVsubmit);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            nSum = bundle.getFloat("ActualScore");
        }

        GlobalSetting myApp = (GlobalSetting) getApplication();

        LoginInfoType info = myApp.gLoginItem;
        Infos = myApp.getMarkSheet();
        //定义评分表名称
        PingFenBiaoName = (TextView) findViewById(R.id.PingFenBiaoName);

        try {
            //给评分表赋值
            PingFenBiaoName.setText(URLDecoder.decode(Infos.mark_sheet_list.get(0).MS_Name, "UTF-8"));
            if (info != null) {
                TextView tvExamName = (TextView) findViewById(R.id.ExamName);
                tvExamName.setText(URLDecoder.decode(info.E_Name, "UTF-8"));
                TextView tvStationName = (TextView) findViewById(R.id.KaoZhan);
                tvStationName.setText(URLDecoder.decode(info.ES_Name, "UTF-8"));
                TextView RoomName = (TextView) findViewById(R.id.FangJian);
                RoomName.setText(URLDecoder.decode(info.Room_Name, "UTF-8"));
                TextView ScoreItems = (TextView) findViewById(R.id.PingFenBiaoCount);
                ScoreItems.setText(URLDecoder.decode(info.mark_sheet_count, "UTF-8"));
            }

            for (HashMap<String, Object> map : myApp.gStudnetArray) {
                String sUid = (String) map.get("U_ID");
                if (sUid == myApp.gStudentId) {
                    TextView labelExamNo = (TextView) findViewById(R.id.TextXueHao);
                    labelExamNo.setText(URLDecoder.decode((String) map.get("itemXuehao"), "UTF-8"));
                    TextView labelName = (TextView) findViewById(R.id.TextKaoHao);
                    labelName.setText(URLDecoder.decode((String) map.get("itemKaohao"), "UTF-8"));
                    //TextView labelStudentNo = (TextView)findViewById(R.id.TextXingMing);
                    //labelStudentNo.setText(URLDecoder.decode((String)map.get("itemName"), "UTF-8"));
                    TextView labelClassName = (TextView) findViewById(R.id.TextBanji);
                    labelClassName.setText(URLDecoder.decode((String) map.get("itemBanJi"), "UTF-8"));
                    TextView TextZongFen = (TextView) findViewById(R.id.TextZongFen);
                    TextZongFen.setText(String.valueOf(getSum(0)));
                    TextView TextDeFen = (TextView) findViewById(R.id.TextDeFen);
                    TextDeFen.setText(String.format("%.1f", nSum));
                    break;
                }
            }
        } catch (Exception e1) {

        }

        ivSign.setOnClickListener((android.view.View.OnClickListener) signListener);
        tvSign.setOnClickListener((android.view.View.OnClickListener) signListener);

        expandableListView = (ExpandableListView) findViewById(R.id.LVListView);
        //去掉前面图标
        expandableListView.setGroupIndicator(null);
        //不折叠树;
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO 自动生成的方法存根
                return true;
            }
        });
        adapter = new MyExpandableListViewAdapter(this);
        expandableListView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
        //返回按钮
        TVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回时关闭本界面
                ScoreViewActivity.this.finish();
            }
        });
        //提交按钮，关闭之前界面直接导航到MainActivity
        TVsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根

                GlobalSetting myApp = (GlobalSetting) getApplication();


                //modify student state
                if (signPath != null && PublicPara.bflag) {

                    for (HashMap<String, Object> map : myApp.gStudnetArray) {
                        String sUid = (String) map.get("U_ID");
                        if (sUid == myApp.gStudentId) {
                            map.put("itemZhuangtai", "已考");
                            break;
                        }
                    }

                    addScoreInfo();
                } else {
                    AlertMessage("请先签名，然后提交成绩。");
                }
            }
        });
    }

    //0 return Total score,else return actural score
    private float getSum(int nCase) {
        float nSum = 0.0f;
        float nTotalSum = 0.0f;
        if (Infos != null) {
            if (Infos.mark_sheet_list.size() > 0) {
                for (int i = 0; i < Infos.mark_sheet_list.get(0).item_list.size(); i++) {

                    for (int j = 0; j < Infos.mark_sheet_list.get(0).item_list.get(i).children_item_list.size(); j++) {
                        children_item ci = Infos.mark_sheet_list.get(0).item_list.get(i).children_item_list.get(j);
                        nSum += Float.parseFloat(ci.MSI_Score);
                        nTotalSum += Float.parseFloat(ci.Item_Score);
                    }
                }
            }
        }
        if (nCase == 0) {
            return nSum;
        } else {
            return nTotalSum;
        }
    }


    private void addScoreInfo() {
        GlobalSetting myApp = (GlobalSetting) getApplication();
        loginItem = myApp.gLoginItem;
        //正在上传成绩
        mProgressHUD = ProgressHUD.show(this, "正在上传成绩", true, true, null);

        //float nSum = getSum(1);
        Gson gson = new Gson();
        MarkData MkData = Infos.mark_sheet_list.get(0);
        for (int i = 0; i < MkData.item_list.size(); i++) {
            if (MkData.item_list.get(i).children_item_list.size() >= 1) {
                for (int j = 0; j < MkData.item_list.get(i).children_item_list.size(); j++) {
                    children_item childitem = MkData.item_list.get(i).children_item_list.get(j);
                    if (childitem.Score_Type != "0" && childitem.item_detail_list != null) {
                        for (int k = 0; k < childitem.item_detail_list.size(); k++) {
                            MarkSheet.detail_item detailitem = childitem.item_detail_list.get(k);
                            if (detailitem.MSIRD_Score == childitem.Item_Score) {
                                childitem.MSIRD_ID = detailitem.MSIRD_ID;
                                break;
                            }
                        }
                    }

                }
            }

        }
        //String sJson ="{\"MS_ID\": \"165\",\"item_list\": [{\"MSI_ID\": \"522\",\"children_item_list\": [{\"MSI_ID\": \"523\",\"MSI_Score\": \"3\"}],\"MSI_Item\": \"%E9%92%B1%E7%89%A9%E8%BD%BB%E5%BE%AE\"}],\"MS_Sum\": \"90\",\"MS_Name\": \"%E8%AF%B7%E9%97%AE%E4%BC%81%E9%B9%85\"}";
        String sJson = gson.toJson(MkData);

        SharedPreferences userInfo = getSharedPreferences("user_info", 0);
        if (!userInfo.contains("ipconfig")) {
            return;
        }
        String BaseUrl = userInfo.getString("ipconfig", null);
        String url = "http://";
        url = url + BaseUrl + "/AppDataInterface/HandScore.aspx/AddScoreInfo";
        Ion.with(this)
                .load(url)
                .setMultipartParameter("E_ID", loginItem.E_ID)
                .setMultipartParameter("ES_ID", loginItem.ES_ID)
                .setMultipartParameter("Room_ID", loginItem.Room_ID)
                .setMultipartParameter("Student_ID", myApp.gStudentId)
                .setMultipartParameter("Rater_ID", loginItem.U_ID)
                .setMultipartParameter("SI_Score", String.valueOf(nSum))
                .setMultipartParameter("SI_Item", Infos.mark_sheet_list.get(0).MS_Name)
                .setMultipartParameter("MS_ID", Infos.mark_sheet_list.get(0).MS_ID)
                .setMultipartParameter("EU_ID", loginItem.EU_ID)
                .setMultipartParameter("SI_Items", sJson)
                .setMultipartFile("image", new File(signPath))
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

                            JSONObject obj = new JSONObject(result.toString());
                            String res = obj.getString("result");
                            if (res.equals("1")) {
                                //save scores
                                //HashMap<String, Object> map = new HashMap<String, Object>();
                                GlobalSetting myApp = (GlobalSetting) getApplication();
                                //StudentInfo studentInfos=myApp.gStudents;
                                for (HashMap<String, Object> objStudent : myApp.gStudnetArray) {
                                    if (objStudent.get("U_ID").equals(myApp.gStudentId)) {
                                        objStudent.put("itemFenshu", String.format("%.2f", nSum));
                                        break;
                                    }
                                }
                                //map.put("id", myApp.getId());
                                //map.put("marksheet",Infos);
                                //map.put("image", signPath);
                                //ArrayList<HashMap<String, Object>> list = myApp.getScoresList();
                                //list.add(map);

                                Intent intent = new Intent();
                                intent.setClass(ScoreViewActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra("viewIntent", "ClosePage");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                //ScoreViewActivity.this.finish();
                                //startActivity(intent);
                            } else {
                                AlertMessage("成绩已经提交，请不要重复提交。");
                            }


                        } catch (Exception eJson) {
                            System.out.println(eJson);
                        }
                        /////

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.score_view, menu);
        return true;
    }

    private android.view.View.OnClickListener signListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            WritePadDialog writeTabletDialog = new WritePadDialog(ScoreViewActivity.this, new DialogListener() {
                @Override
                public void refreshActivity(Object object) {

                    mSignBitmap = (Bitmap) object;
                    signPath = createFile();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 15;
                    options.inTempStorage = new byte[5 * 1024];
                    Bitmap zoombm = BitmapFactory.decodeFile(signPath, options);
                    ivSign.setImageBitmap(mSignBitmap);
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
            //String sign_dir = Environment.getExternalStorageDirectory() + File.separator;   /storage/emulated/0/1432126663744.jpg  /data/data/com.example.webview/files/1432127137050.jpg
            String sign_dir = getApplicationContext().getFilesDir().getAbsolutePath() + File.separator;
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

    // 用过ListView的人一定很熟悉，只不过这里是 BaseExpandableListAdapter
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
        private Context context;

        public MyExpandableListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            int num = 0;
            if (Infos != null) {
                if (Infos.mark_sheet_list.size() > 0) {
                    num = Infos.mark_sheet_list.get(0).item_list.size();
                }
            }
            return num;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            int num = 0;
            if (Infos != null) {
                if (Infos.mark_sheet_list.size() > 0) {
                    num = Infos.mark_sheet_list.get(0).item_list.get(groupPosition).children_item_list.size();
                }
            }
            return num;
        }

        @Override
        public Object getGroup(int groupPosition) {
            Object obj = null;
            if (Infos != null) {
                if (Infos.mark_sheet_list.size() > 0) {
                    obj = Infos.mark_sheet_list.get(0).item_list.get(groupPosition);
                }
            }
            return obj;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            Object obj = null;
            if (Infos != null) {
                if (Infos.mark_sheet_list.size() > 0) {
                    //在子项为空时将父项添加到子项
                    if (Infos.mark_sheet_list.get(0).item_list.get(groupPosition).children_item_list == null) {

                        obj = Infos.mark_sheet_list.get(0).item_list.get(groupPosition);
                    } else {
                        obj = Infos.mark_sheet_list.get(0).item_list.get(groupPosition).children_item_list.get(childPosition);
                    }
                }
            }
            return obj;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                convertView = (View) getLayoutInflater().from(context).inflate(
                        R.layout.activity_scoreparent, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView) convertView.findViewById(R.id.itemtitle);
                groupHolder.txt.setTextColor(getResources().getColor(R.color.blue));
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            try {
                if (Infos != null) {
                    if (Infos.mark_sheet_list.size() > 0) {
                        groupHolder.txt.setText(URLDecoder.decode(Infos.mark_sheet_list.get(0).item_list.get(groupPosition).MSI_Item, "UTF-8"));
                    }
                }
            } catch (UnsupportedEncodingException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = (View) getLayoutInflater().from(context).inflate(
                        R.layout.activity_scorelistview, null);
                itemHolder = new ItemHolder();
                itemHolder.itemNeiRong = (TextView) convertView.findViewById(R.id.itemNeiRong);
                itemHolder.itemFenZhi = (TextView) convertView.findViewById(R.id.itemFenZhi);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            try {
                if (Infos != null) {
                    if (Infos.mark_sheet_list.size() > 0) {
                        if (groupPosition < Infos.mark_sheet_list.get(0).item_list.size()) {
                            children_item ci = Infos.mark_sheet_list.get(0).item_list.get(groupPosition).children_item_list.get(childPosition);
                            itemHolder.itemNeiRong.setText(URLDecoder.decode(ci.MSI_Item, "UTF-8"));
                            if (ci.Item_Score.equals("-1"))
                            {
                                ci.Item_Score = "0";
                            }
                            itemHolder.itemFenZhi.setText(ci.Item_Score);

                        }
                    }
                }
            } catch (NumberFormatException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    class GroupHolder {
        public TextView txt;
    }

    class ItemHolder {
        TextView itemNeiRong;
        TextView itemFenZhi;
    }

    private void AlertMessage(String strMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScoreViewActivity.this);
        builder.setTitle("提示").setMessage(strMsg)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                //按钮事件
                            }
                        })
                .show();
    }
}
