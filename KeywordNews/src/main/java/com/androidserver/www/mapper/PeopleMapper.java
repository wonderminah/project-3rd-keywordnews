package com.androidserver.www.mapper;

import com.androidserver.www.vo.People;

public interface PeopleMapper {
	
	//회원가입
	public int insertPeople(People people);
	
	//로그인
	public People loginPeople(People people);
	
	//로그아웃
	//컨트롤러에서 세션을 무효화한다.
	
	//회원탈퇴
	public int deletePeople(People people);

	//토큰추가
	public int insertToken(People people);
}
