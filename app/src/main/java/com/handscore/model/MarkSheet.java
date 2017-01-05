package com.handscore.model;

import java.util.List;

public class MarkSheet {
	public String result;
	public List<MarkData> mark_sheet_list;
	
	public static class MarkData
	{
		public String MS_ID;
		public String MS_Name;
		public String MarkSheetNumber1;
		public String MarkSheetNumber2;
		public List<Items> item_list;
		public String MS_Sum;	
	}
	public static class Items
	{
		public String MSI_ID;
		public String MSI_Item;
		public List<children_item> children_item_list;
		public String MSI_Score;
	}
	public static class children_item
	{
		public String MSI_ID;
		public String MSI_Item;
		public String MSI_Score;
		public String Score_Type;
		public List<detail_item> item_detail_list;
		public String Item_Score="-1";//initial value
		public String MSIRD_ID="";
		public String rating="-1";//星星值
		public String yesorno="-1";//0代表no，1代表yes
		public String detail="";
	}
	public static class detail_item
	{
		public String MSIRD_ID;
		public String MSIRD_Item;
		public String MSIRD_Score;
	}
}

