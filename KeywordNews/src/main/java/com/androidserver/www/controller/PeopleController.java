
package com.androidserver.www.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.androidserver.www.FCMNotification;
import com.androidserver.www.dao.PeopleDAO;
import com.androidserver.www.vo.People;
import com.google.gson.Gson;

/**
 * PeopleController
 */
@Controller
public class PeopleController {
	
	private static final Logger logger = LoggerFactory.getLogger(PeopleController.class);
	
	@Autowired
	private PeopleDAO dao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		System.out.println("loginPeople: " + session.getAttribute("loginPeople")); //로그
		
		return "home";
	}
	
	//회원가입
	@ResponseBody
	@RequestMapping(value = "insertPeople", method = RequestMethod.POST)
	public String insertPeople(People people) {
		//회원가입 시도
		int result = 0;
		try {
			result = dao.insertPeople(people);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//시도결과 알림
		String msg = "";
		if (result == 1) {
			msg = "Join Success";
		}
		else {
			msg = "Join Fail";
		}
		return msg;
	}
	
	//로그인
	@ResponseBody
	@RequestMapping(value = "loginPeople", method = RequestMethod.POST)
	public String loginPeople(People people) {
		System.out.println(people.getPptoken()); // TODO : LOG
		
		//로그인 시도
		People loginPeople = null;
		int result = 0;
		try {
			loginPeople = dao.loginPeople(people);
			result = dao.insertToken(people);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//시도결과 알림
		String msg = "";
		if (loginPeople != null && result == 1) {
			msg = loginPeople.getPpname();
			try {
				System.out.println("여기");
				//FCMNotification.pushFCMNotification(people.getPpid(), people.getPptoken());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			msg = "Login Failed";
		}
		return msg;
	}
	
	//로그아웃
	@ResponseBody
	@RequestMapping(value = "logoutPeople", method = RequestMethod.POST)
	public String logoutPeople(HttpSession session) {
		//로그아웃 시도 및 결과 알림
		String msg = "";
		try {
			session.invalidate();
			msg = "Logout Success";
		}
		catch (Exception e) {
			msg = "Logout Failed";
		}
		return msg;
	}
	
	//회원탈퇴
	@ResponseBody
	@RequestMapping(value = "deletePeople", method = RequestMethod.POST)
	public String deletePeople(People people) {
		//회원탈퇴 시도
		int result = 0;
		try {
			result = dao.deletePeople(people);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//시도결과 알림
		String msg = "";
		if (result == 1) {
			msg = "Delete Success";
		}
		else {
			msg = "Delete Failed";
		}
		return msg;
	}
}
