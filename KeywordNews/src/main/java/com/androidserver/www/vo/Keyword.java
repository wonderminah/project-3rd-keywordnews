package com.androidserver.www.vo;

public class Keyword {
	
	private String ppid;
	private String keyword;
	
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public Keyword() {}
	public Keyword(String ppid, String keyword) {
		super();
		this.ppid = ppid;
		this.keyword = keyword;
	}
	
	@Override
	public String toString() {
		return "Keyword [ppid=" + ppid + ", keyword=" + keyword + "]";
	}
}
