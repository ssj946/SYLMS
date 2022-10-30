package com.notice;

public class NoticeDTO {
	private String articleNo;
	private String subjectNo;
	private String bbsCode;
	private String userId;
	private String title;
	private String content;
	private String reg_date;
	private int hitCount;
	
	
	private String savefileName;
	private String originalFilename;
	private String fileSize;
	private String fileNo;
	
	private String[] saveFiles;
	private String[] originalFiles;
	private long gap;
	
	private String name;
	

	private String professorname;
	private int semester;
	private String subjectName;
	private int syear;
	private int credit;
	


	public String getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getBbsCode() {
		return bbsCode;
	}
	public void setBbsCode(String bbsCode) {
		this.bbsCode = bbsCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public String getSavefileName() {
		return savefileName;
	}
	public void setSavefileName(String savefileName) {
		this.savefileName = savefileName;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalfileName(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public long getGap() {
		return gap;
	}
	public void setGap(long gap) {
		this.gap = gap;
	}
	
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String[] getSaveFiles() {
		return saveFiles;
	}
	public void setSaveFiles(String[] saveFiles) {
		this.saveFiles = saveFiles;
	}
	public String[] getOriginalFiles() {
		return originalFiles;
	}
	public void setOriginalFiles(String[] originalFiles) {
		this.originalFiles = originalFiles;
	}
	
	
	
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
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	
}
