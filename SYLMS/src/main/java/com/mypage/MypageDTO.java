package com.mypage;

public class MypageDTO {
	//개인정보 수정 
	private String UserId;
	private String name;
	private String pwd;
	private String tel;
	private String email;
	private String birth;
	private String departmentName;
	private String fileName;
	private String savefilename;
	
	
	
	//수강과목 
	private String  subjectNo; //과목번호
	private String studentCode; //학생학번 
	
	
	
	//올린 파일함
	private String fileNum; //파일번호
	private String originName; //원래이름
	private String saveName; //저장이름 
	private long fileSize; // 파일 사이즈

	
	//조교신청
	private String applicationTerm; //신청학기
	private String applicationdepartment; //신청과목
	private String applicationYear; //신청연도 
	private String applicationNum; //신청번호
	private String ENABLE; //승인여부
	
	//과목
	private String year; //과목연도
	private String semester; //학기
	private String department; //학과
	private String subject;//과목명
	private String professor;// 교수
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSavefilename() {
		return savefilename;
	}
	public void setSavefilename(String savefilename) {
		this.savefilename = savefilename;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getFileNum() {
		return fileNum;
	}
	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getApplicationTerm() {
		return applicationTerm;
	}
	public void setApplicationTerm(String applicationTerm) {
		this.applicationTerm = applicationTerm;
	}
	public String getApplicationdepartment() {
		return applicationdepartment;
	}
	public void setApplicationdepartment(String applicationdepartment) {
		this.applicationdepartment = applicationdepartment;
	}
	public String getApplicationYear() {
		return applicationYear;
	}
	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}
	public String getApplicationNum() {
		return applicationNum;
	}
	public void setApplicationNum(String applicationNum) {
		this.applicationNum = applicationNum;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getENABLE() {
		return ENABLE;
	}
	public void setENABLE(String eNABLE) {
		ENABLE = eNABLE;
	}
	
	

	
	
	
	
	
	
}
