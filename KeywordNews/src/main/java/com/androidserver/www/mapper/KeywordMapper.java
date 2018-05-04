package com.androidserver.www.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.androidserver.www.vo.Keyword;

public interface KeywordMapper {

	//아이디별 키워드 불러오기
	public ArrayList<Keyword> selectKeyword(String ppid);
	
	//아이디별 키워드 추가
	public int insertKeyword(HashMap<String, String> map);
	
	//아이디별 키워드 삭제
	public int deleteKeyword(HashMap<String, String> map);

	//모든 키워드 불러오기
	public ArrayList<Keyword> selectKeywordAll();
}
