package com.example.webview;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

import com.handscore.model.LoginInfoType;
import com.handscore.model.MarkSheet;
import com.handscore.model.StudentInfo;

public class GlobalSetting extends Application {
	public LoginInfoType gLoginItem;
	public StudentInfo gStudents;
	public MarkSheet gMarkSheet;
	public ArrayList<HashMap<String, Object>> gStudnetArray;
	public ArrayList<HashMap<String, Object>> gScoreArray;
	public int gSegSelectedIndex;
	public String gStudentId;
	
	public LoginInfoType getLoginItem() {  
        return gLoginItem;  
    }  
  
    public void setLoginItem(LoginInfoType item) {  
        this.gLoginItem = item;  
    }
    
    public void setMarkSheet(MarkSheet item){
    	this.gMarkSheet = item;
    }
    
    public MarkSheet getMarkSheet() {
    	return gMarkSheet;
    }
    
    public void setStudentList(ArrayList<HashMap<String, Object>> list){
    	this.gStudnetArray = list;
    }
    
    public ArrayList<HashMap<String, Object>> getStudentList(){
    	return this.gStudnetArray;
    }
    
    public void setScoresList(ArrayList<HashMap<String, Object>> list){
    	this.gScoreArray = list;
    }
    
    public ArrayList<HashMap<String, Object>> getScoresList(){
    	return this.gScoreArray;
    }
    
    public void setId(String id){
    	this.gStudentId = id;
    }
    
    public String getId(){
    	return this.gStudentId;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    
}
