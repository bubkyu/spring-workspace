package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		// true리턴 : 기존의 흐름대로 해당 controller 구동시키겠다는 의미
		// false리턴 : 기존에 실행하려고 했던 controller 구동시키지 않음을 의미
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") == null) { // 로그인 전 상태
			
			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
			response.sendRedirect(request.getContextPath());
			
			return false;
			
		}else {	// 로그인 후 상태
			return true;
		}
		
		// 마이페이지, 자유게시판 리스트, 상세조회, 글쓰기 들은 회원만 이용가능한 서비스로 가정.
		
		// 권한체크용으로 인터셉터사용
		
		
		
	}

	
	
}
