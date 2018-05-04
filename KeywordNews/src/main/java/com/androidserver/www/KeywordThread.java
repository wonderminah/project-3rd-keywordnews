package com.androidserver.www;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import com.androidserver.www.dao.KeywordDAO;
import com.androidserver.www.vo.Keyword;

public class KeywordThread extends Thread {

	@Autowired
	KeywordDAO dao;

	String ppid;
	ArrayList<Keyword> keywordList;
	
	public KeywordDAO getDao() {
		return dao;
	}
	public void setDao(KeywordDAO dao) {
		this.dao = dao;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public ArrayList<Keyword> getKeywordList() {
		return keywordList;
	}
	public void setKeywordList(ArrayList<Keyword> keywordList) {
		this.keywordList = keywordList;
	}
	
	public KeywordThread() {}
	public KeywordThread(String ppid) {
		super();
		this.ppid = ppid;
	}
	
	@Override
	public void run() {
		super.run();
		keywordList = dao.selectKeyword(ppid);
	}
}
