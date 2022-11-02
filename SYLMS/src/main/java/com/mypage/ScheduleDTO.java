package com.mypage;

public class ScheduleDTO {
	//시간(시작시간 y, 요일 x), 장소(교실),  교수명,  과목명 , 과목번호

	private String dayWeek; // 요일
	private String schedule; //시간
	private String subjectNo; //과목번호 
	private String lecturePlace; //강의실 
    private String subjectName; //과목이름
    private String professorName; //교수명 
    private String studentcode; //학생코드 
    
    
    
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
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
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	public String getStudentcode() {
		return studentcode;
	}
	public void setStudentcode(String studentcode) {
		this.studentcode = studentcode;
	}
	public String getDayWeek() {
		return dayWeek;
	}
	public void setDayWeek(String dayWeek) {
		this.dayWeek = dayWeek;
	}
	
	
    
    
}
