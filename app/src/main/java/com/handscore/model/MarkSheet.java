package com.handscore.model;

import java.util.List;

public class MarkSheet {
	public String result;
	public List<MarkData> mark_sheet_list;
	
	public static class MarkData
	{
		public String MS_ID;
		public String MS_Name;
		public List<Items> item_list;
		public String MS_Sum;	
	}
	public static class Items
	{
		public String MSI_ID;
		public String MSI_Item;
		public List<children_item> children_item_list;
		public String MSI_Score;
		//public String MSI_RealScore="-1";//initial value
	}
	public static class children_item
	{
		public String MSI_ID;
		public String MSI_Item;
		public String MSI_Score;
		public String MSI_RealScore="-1";//initial value
	}
}
