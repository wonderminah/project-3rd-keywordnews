package com.androidserver.www.vo;

public class People {
	
	private String ppid;	//아이디
	private String pppw;	//비밀번호
	private String ppname;	//이름
	private int ppphnum;	//전화번호
	private String pptoken;	//토큰값 (for FCM. 로그인 액티비티에서 로그인 성공 시 생성)
	
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getPppw() {
		return pppw;
	}
	public void setPppw(String pppw) {
		this.pppw = pppw;
	}
	public String getPpname() {
		return ppname;
	}
	public void setPpname(String ppname) {
		this.ppname = ppname;
	}
	public int getPpphnum() {
		return ppphnum;
	}
	public void setPpphnum(int ppphnum) {
		this.ppphnum = ppphnum;
	}
	public String getPptoken() {
		return pptoken;
	}
	public void setPptoken(String pptoken) {
		this.pptoken = pptoken;
	}
	
	public People() {}
	public People(String ppid, String pppw, String ppname, int ppphnum, String pptoken) {
		super();
		this.ppid = ppid;
		this.pppw = pppw;
		this.ppname = ppname;
		this.ppphnum = ppphnum;
		this.pptoken = pptoken;
	}
	
	@Override
	public String toString() {
		return "People [ppid=" + ppid + ", pppw=" + pppw + ", ppname=" + ppname + ", ppphnum=" + ppphnum + ", pptoken=" + pptoken + "]";
	}
	
}
