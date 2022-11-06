package com.exam;

public class ExamDTO {
	private String examCode;
	private int score;
	private String examType;
	private String s_time;
	private String e_time;
	private String gradeCode;
	
	private int grade;
	private String studentCode;

	private String subjectNo;
	private String[] studentCodes;
	private String[] gradeCodes;
	private String[] examTypes;
	private int[] scores;
	
	private String professorname;
	private int semester;
	private String subjectName;
	private int syear;
	private int credit;
	
	public String getProfessorname() {
		return professorname;
	}
	public void setProfessorname(String professorname) {
		this.professorname = professorname;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getSyear() {
		return syear;
	}
	public void setSyear(int syear) {
		this.syear = syear;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getExamCode() {
		return examCode;
	}
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getS_time() {
		return s_time;
	}
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}
	public String getE_time() {
		return e_time;
	}
	public void setE_time(String e_time) {
		this.e_time = e_time;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String[] getGradeCodes() {
		return gradeCodes;
	}
	public void setGradeCodes(String[] gradeCodes) {
		this.gradeCodes = gradeCodes;
	}
	public String[] getExamTypes() {
		return examTypes;
	}
	public void setExamTypes(String[] examTypes) {
		this.examTypes = examTypes;
	}
	public int[] getScores() {
		return scores;
	}
	public void setScores(int[] scores) {
		this.scores = scores;
	}
	public String[] getStudentCodes() {
		return studentCodes;
	}
	public void setStudentCodes(String[] studentCodes) {
		this.studentCodes = studentCodes;
	}
}
