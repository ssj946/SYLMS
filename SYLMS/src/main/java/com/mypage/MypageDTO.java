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
	
	
	
	//수강과목 
	private String  subjectNo; //과목번호
	private String studentCode; //학생학번 
	
	//올린 파일함
	private String fileNum; //파일번호
	private String originName; //원래이름
	private String saveName; //저장이름 

	
	//조교신청
	private String applicationTerm; //신청학기
	private String  applicationMajor; //신청학과
	private String applicationYear; //신청연도 
	private String applicationNum; //신청번호
	
	
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
	public String getApplicationTerm() {
		return applicationTerm;
	}
	public void setApplicationTerm(String applicationTerm) {
		this.applicationTerm = applicationTerm;
	}
	public String getApplicationMajor() {
		return applicationMajor;
	}
	public void setApplicationMajor(String applicationMajor) {
		this.applicationMajor = applicationMajor;
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
	
	
	
	
	
	
	
}
