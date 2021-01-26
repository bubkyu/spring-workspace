package com.kh.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller	// 어노테이션을 붙여줌으로써 해당 이 클래스 빈으로 등록됨.(빈스캐닝을 통해서)
public class MemberController {
	
	// * 파라미터(요청시 전달값)를 전송하는 방법 == 요청시 전달되는 값들 처리방법
	/*
	 *  1. HttpServletRequest를 통해 전송받기(기존의 jsp/servlet 때의 방식)
	 *  	해당 메소드의 매개변수로 HttpServletRequest를 작성하면
	 *   	내부적으로 메소드 실행시 스프링컨터이너가 자동으로 해당 객체를 인자로 주입해줌.
	 */
	
	@RequestMapping("login.me")	// @ RequestMapping 어노테이션을 붙여줌으로써 HandlerMapping
	public void loginMember() {
		
		System.out.println("실행되니?");
		
		
	}
	
	
	
}
