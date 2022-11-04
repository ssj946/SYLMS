package com.qna;

public class QnaDTO {
		private String articleNo;  //글번호
		private String subjectNo;  //과목번호
		private String bbsCode;   //게시판코드
		private String userId;   // ID 
		private String title;   // 제목
		private String content;  // 내용
		private String reg_date; // 등록일
		private int hitCount; // 조회수
		
		
		private String saveFilename; //저장이름(과목게시판파일)
		private String originalFilename; // 초기이름
		private String fileSize;  //파일사이즈(커뮤니티게시판파일)
		private String fileNo;   //파일번호(과목게시판파일)
		
		private String[] saveFiles; //저장파일
		private String[] originalFiles; //초기파일
		private long gap; //
		
		private String name; //이름
		

		private String professorname; //교수이름
		private int semester; //학기(과목)
		private String subjectName; //과목명
		private int syear; // 년도
		private int credit; //취득학점
		


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
		public String getSaveFilename() {
			return saveFilename;
		}
		public void setSaveFilename(String saveFilename) {
			this.saveFilename = saveFilename;
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

