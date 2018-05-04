package com.androidserver.www.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.androidserver.www.mapper.PeopleMapper;
import com.androidserver.www.vo.People;

/**
 * PeopleDAO
 */
@Repository
public class PeopleDAO implements PeopleMapper {
	
	@Autowired
	SqlSession sqlSession;
	
	//회원가입
	public int insertPeople(People people) {
		PeopleMapper mapper = sqlSession.getMapper(PeopleMapper.class);
		int result = 0;
		try {
			result = mapper.insertPeople(people);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//로그인
	@Override
	public People loginPeople(People people) {
		PeopleMapper mapper = sqlSession.getMapper(PeopleMapper.class);
		People result = null;
		try {
			result = mapper.loginPeople(people);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//로그아웃
	//컨트롤러에서 세션을 무효화한다.
	
	//회원탈퇴
	@Override
	public int deletePeople(People people) {
		PeopleMapper mapper = sqlSession.getMapper(PeopleMapper.class);
		int result = 0;
		try {
			result = mapper.deletePeople(people);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//토큰추가
	@Override
	public int insertToken(People people) {
		PeopleMapper mapper = sqlSession.getMapper(PeopleMapper.class);
		int result = 0;
		try {
			result = mapper.insertToken(people);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
