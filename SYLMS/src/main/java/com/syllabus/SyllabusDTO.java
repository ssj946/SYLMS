package com.syllabus;

public class SyllabusDTO {
	private String subjectNo;
	private String openDate;
	private int semester;
	private String lecturePlace;
	private int assignmentRate;
	private int middleRate;
	private int finalRate;

	public String getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
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

	public String getLecturePlace() {
		return lecturePlace;
	}

	public void setLecturePlace(String lecturePlace) {
		this.lecturePlace = lecturePlace;
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
}
