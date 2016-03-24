package com.handscore.model;

import java.util.List;

public class StudentInfo {
	
	public String result;
	
	public String page_index;
	
	public List<Student> student_list;
	
	public static class Student
	{
		public String U_Name;
		public String U_ID;
		public String O_Name;
		public String Exam_StartTime;
		public String Exam_EndTime;
		public String U_TrueName;
		public String student_state;
		public String student_score;
		public String EStu_ExamNumber;
	}
}
