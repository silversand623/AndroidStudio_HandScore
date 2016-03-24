package com.handscore.model;

import java.util.List;

public class MarkSheetScoreSee {
	public String result;
	public String SI_ID;
	public String SI_CreateTime;
	public String SI_Score;
	public String EStu_ExamNumber;
	public String U_TrueName;
	public String U_Name;
	public String O_Name;
	public String MS_ID;
	public String MS_Name;
	public String MS_Sum;	
	public List<item_score> item_score_list;
	
	public static class item_score
	{
		public String MSI_Item;		
		public List<children_item> children_item_list;
		public String MSI_Score;
		public String Item_Score;
	}	
	public static class children_item
	{
		public String MSI_Item;		
		public String MSI_Score;
		public String Item_Score;
	}
}
