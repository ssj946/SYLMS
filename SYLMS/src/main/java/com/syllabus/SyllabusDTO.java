package com.syllabus;

public class SyllabusDTO {
	private String subjectNo;
	private String subjectName;
	private String lecturePlace;
	private String openDate;
	private int semester;
	private int assignmentRate;
	private int middleRate;
	private int finalRate;
	private int credit;
	private String id;
	private String name;
	
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getLecturePlace() {
		return lecturePlace;
	}
	public void setLecturePlace(String lecturePlace) {
		this.lecturePlace = lecturePlace;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getAssignmentRate() {
		return assignmentRate;
	}
	public void setAssignmentRate(int assignmentRate) {
		this.assignmentRate = assignmentRate;
	}
	public int getMiddleRate() {
		return middleRate;
	}
	public void setMiddleRate(int middleRate) {
		this.middleRate = middleRate;
	}
	public int getFinalRate() {
		return finalRate;
	}
	public void setFinalRate(int finalRate) {
		this.finalRate = finalRate;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}