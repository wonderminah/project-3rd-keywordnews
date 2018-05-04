package com.androidserver.www.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.androidserver.www.mapper.KeywordMapper;
import com.androidserver.www.vo.Keyword;

@Repository
public class KeywordDAO implements KeywordMapper {
	
	@Autowired
	SqlSession sqlSession;

	//아이디별 키워드 불러오기
	@Override
	public ArrayList<Keyword> selectKeyword(String ppid) {
		KeywordMapper mapper = sqlSession.getMapper(KeywordMapper.class);
		ArrayList<Keyword> result = null;
		try {
			result = mapper.selectKeyword(ppid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//아이디별 키워드 추가
	@Override
	public int insertKeyword(HashMap<String, String> map) {
		System.out.println("KeywordDAO > insertKeyword > map: " + map.toString());
		KeywordMapper mapper = sqlSession.getMapper(KeywordMapper.class);
		int result = 0;
		try {
			result = mapper.insertKeyword(map);
		}
		catch (Exception e) {
			e.printStackTrace();
	}
	return result;
}

	//아이디별 키워드 삭제
	@Override
	public int deleteKeyword(HashMap<String, String> map) {
		KeywordMapper mapper = sqlSession.getMapper(KeywordMapper.class);
		int result = 0;
		try {
			result = mapper.deleteKeyword(map);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//모든 키워드 불러오기
	@Override
	public ArrayList<Keyword> selectKeywordAll() {
		KeywordMapper mapper = sqlSession.getMapper(KeywordMapper.class);
		
		ArrayList<Keyword> result = null;
		try {
			result = mapper.selectKeywordAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
