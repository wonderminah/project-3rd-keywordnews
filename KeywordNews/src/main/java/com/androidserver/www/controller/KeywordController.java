package com.androidserver.www.controller;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.androidserver.www.dao.KeywordDAO;
import com.androidserver.www.vo.Keyword;

@Controller
public class KeywordController {

	@Autowired
	private KeywordDAO dao;
	
	//아이디별 키워드 불러오기
	@ResponseBody
	@RequestMapping(value = "selectKeyword", method = RequestMethod.POST)
	public ArrayList<Keyword> selectKeyword(HttpSession session, String ppid) {
		System.out.println("KeywordController > selectKeyword() > ppid: " + ppid); // TODO : LOG
		
		ArrayList<Keyword> list = null;
		try {
			list = dao.selectKeyword(ppid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("KeywordController > selectKeyword() > list: " + list.get(i).toString()); // TODO : LOG
		}
		
		return list;
	}
	
	//아이디별 키워드 추가
	@ResponseBody
	@RequestMapping(value = "insertKeyword", method = RequestMethod.POST)
	public String insertKeyword(String ppid, String keyword) {
		System.out.println("KeywordController > insertKeyword > ppid: " + ppid + " keyword: " + keyword); // TODO : LOG
		
		HashMap<String, String> map = new HashMap<>();
		map.put("ppid", ppid);
		map.put("keyword", keyword);
		
		int result = 0;
		try {
			result = dao.insertKeyword(map);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg = "";
		if (result == 1) {
			msg = "Add Keyword Success";
		}
		else {
			msg = "Add Keyword Failed";
		}
		return msg;
	}
	
	//아이디별 키워드 삭제
	@ResponseBody
	@RequestMapping(value = "deleteKeyword", method = RequestMethod.POST)
	public String deleteKeyword(String ppid, String keyword) {
		System.out.println("KeywordController > deleteKeyword > ppid: " + ppid + " keyword: " + keyword);
		
		HashMap<String, String> map = new HashMap<>();
		map.put("ppid", ppid);
		map.put("keyword", keyword);
		
		int result = 0;
		try {
			result = dao.deleteKeyword(map);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String msg = "";
		if (result == 1) {
			msg = "Delete Keyword Success";
		}
		else {
			msg = "Delete Keyword Failed";
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "selectKeywordAll", method = RequestMethod.POST)
	public ArrayList<Keyword> selectKeywordAll() {
		System.out.println("KeywordController > selectKeywordAll()"); // TODO : LOG
		
		ArrayList<Keyword> list = null;
		try {
			list = dao.selectKeywordAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString()); // TODO : LOG
		}
		return list;
	}
}
