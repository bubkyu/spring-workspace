package com.kh.spring.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller	// 어노테이션을 붙여줌으로써 해당 이 클래스 빈으로 등록됨.(빈스캐닝을 통해서)
public class MemberController {
	
	// Autowired 어노테이션을 붙여주면 직접 객체 생성할 필요없이 변수 선선만으로
	// 스프링이 관리하고 있는 해당 객체 자동으로 생성한 후 주입해준다.
	// 단, MemberServiceImpl 객체를 스프링이 관리하도록 빈으로 등록해야한다.
	
	@Autowired
	private MemberService mService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	
	
	
	// * 파라미터(요청시 전달값)를 전송하는 방법 == 요청시 전달되는 값들 처리방법
	/*
	 *  1. HttpServletRequest를 통해 전송받기(기존의 jsp/servlet 때의 방식)
	 *  	해당 메소드의 매개변수로 HttpServletRequest를 작성하면
	 *   	내부적으로 메소드 실행시 스프링컨터이너가 자동으로 해당 객체를 인자로 주입해줌.
	 */
	
	/*
	@RequestMapping("login.me")	// @ RequestMapping 어노테이션을 붙여줌으로써 HandlerMapping
	public void loginMember(HttpServletRequest request) {
		
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
	}
	*/
	
	/*
	 *  2. @RequestParam 어노테이션 방식
	 *  	스프링에서는 조금 더 간단하게 파라미터(요청시 전달값)를 받아올 수 있는 방법을 제공
	 *  	@RequestParam(value="키값") == request.getParameter("키값)
	 *  
	 */
	
	/*@RequestMapping("login.me")
	public void loginMember(@RequestParam(value="id", defaultValue="aaaa") String userId,
							@RequestParam("pwd")String userPwd) {
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
	}
	*/
	
	/*
	 * 	3. @RequestParam 어노테이션을 생략하는 방식
	 * 	위의 어노테이션을 생략해도 파라미터값을 가져와서 변수에 저장 가능하다.
	 *  단, 매개변수를 name값과 동일하게 해야 자동으로 값이 주입된다.
	 */
	/*
	@RequestMapping("login.me")
	public void loginMember(String userId, String userPwd) {
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		
	}
	*/
	
	/*
	 *  4. @ModelAttribute를 이용한 방식
	 *  요청시 전달되는 값들이 많을 경우 vo객체에 바로 담아내고자 할때 쓰이는 방식
	 *  --> 기본생성자와 setter를 이용한 주입 방식.
	 *  
	 *  커맨드 객체 방식이라고 한다.
	 *  스프링 컨테이너가 내부적으로 해당 객체 기본생성자로 생성한 후에 setter메소드를 통해서
	 *  요청시 전달값들을 각 필드에 주입하는 방식.
	 *  (주의 : 반드시 name속성값이 내가 바로 담고자 하는 vo 객체의 필드명과 동일해야한다.)
	 */
	/*
	@RequestMapping("login.me")
	public void loginMember(@ModelAttribute Member m) {
		System.out.println("ID : " + m.getUserId());
		System.out.println("PWD : " + m.getUserPwd());
	}
	*/
	
	/*
	 *  5. 위의 @ModelAttribute 어노테이션 생략하고 바로 커맨드객체에 담기
	 *  
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, HttpSession session) {
		
		
		System.out.println("ID : " + m.getUserId());
		System.out.println("PWD : " + m.getUserPwd());
		
		
		
		 * 내가 직접 new 키워드를 통해서 객체 생성(의존관계)하게 되면
		 * 해당 객체와의 결합도가 강해진다.
		 * 
		 * 
		 * 결합도가 높아서 발생하는 문제!
		 * 문제점 1. 생성하고 있는 클래스명이 바뀌었을 경우 -> 객체 생성하는 구문에 에러 발생 - > 코드를 수정애햐한다.
		 * 문제점 2. 매 요청떄마다 생성된 객체의 주소값이 달라진다. -> 즉, 계속이 새로 객체 생성
		 * 			-> 10000건 요청이 있다면 10000개의 객체가 생성된 후 사라지게 된다.
		 * 
		 * 결합도를 낮추기 위해 의존성 주입(DI)을 이용하자.
		 * 해결 1. 필요로 하는 객체의 클래스명이 바뀌었을때 코드를 수정할 필요가 없다.
		 * 해결 2. 매 요청마다 같은 객체의 주소값 동일하다.(즉, 한 개의 객체만 생성해놓고 재사용의 개념 == 싱글톤)
		 * 		-> 10000건의 요청이 있어도 1개의 객체만 관리하면 됨. -> 메모리 효율이 올라간다.
		 * 
		 
		
		System.out.println(mService);
		
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) {	// 로그인 실패 -> 에러문구 담아서 에러페이지 "포워딩"
			
			//request.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(request,response);
			return "common/errorPage"; 
			// 여기서 리턴한 문자열(view명)이
			// servlet-context.xml 문서에 ViewResolver 빈등록하는 구문에
			// 기술되어 있는 prefix값인 /WEB-INF/views/ 이러한 값이 앞에 붙고
			//			suffix값인 .jsp			/ 이러한 값이 뒤에 붙어서 포워딩
			
			// /WEB-INF/views/common/erroerPage.jsp 이렇게 붙음.
			
		}else {	// 로그인 성공 - > 세션영역에 loginUser 다음 후   - >메인페이지 "url 재요청"
			
			session.setAttribute("loginUser", loginUser);
			
			// url 재용하는 redirect 방식을 하고 싶다면
			return "redirect:/";
			
		}
		
	}
	*/
	
	//  * 요청 처리 후 응답페이지에 응답할 데이터가 있을 경우에 대한 방법
	/*
	 *  1. Model 이라는 객체를 사용하는 방법
	 *  	Model 이라는 객체를 사용하면 응답 뷰로 전달하고자하는 데이터를 key-value 로 담을 수 있다.
	 *  	scope는 request이다.
	 *  	단, setAttribute가 아닌 addAttribute
	 *  
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, HttpSession session, Model model) {
		
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패 -> 에러문구 담아서 에러페이지 포워딩
			
			model.addAttribute("errorMsg", "로그인 실패");	// scope 는 request 이다.
			return "common/errorPage";
	
		}else { // 로그인 성공 -> 세션영역에서 loginUser 담고 -> / url 재요청
			
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";
		}
		
	}
	*/
	
	/*
	 *  2. ModelAndView 객체를 사용하는 방법
	 *  
	 *  위에서 Model은 응답할 데이터를 맵 형식(key-value)로 담을 수 있는 공간이라면
	 *  View는 RequestDispatcher처럼 포워딩 할 뷰 페이지 정보를 담을 수 있는 공간
	 *  
	 *  ModelAndView는 이 두가지를 합쳐놓은 객체 == 응답데이터도 담을수 있고, 응답할 뷰 페이지도 담을 수 있다.
	 */
	
	//로그인
	/* 암호화 처리 전 로그인 처리
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) { // 로그인 실패
			mv.addObject("errorMsg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}else { // 로그인 성공
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("alertMsg", loginUser.getUserName() + "님 환영합니다.");
			mv.setViewName("redirect:/");
		}
		
		return mv;
	}
	*/
	// 암호화 처리 후 로그인 처리
	@RequestMapping("login.me")
	public String loginMember(Member m, HttpSession session, Model model) {
		// m -> 로그인 요청 시 입력했던 아이디, 비밀번호(평문)
		
		Member loginUser = mService.loginMember(m); // 아이디만 가지고 찾은 회원 객체
		
		// loginUser -> 모든 컬럼에 대한 값 + 비밀번호(암호문)
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { // 로그인 성공
			
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("alertMsg", loginUser.getUserName() + "님 환영합니다.");
			return "redirect:/";
			
		}else {	// 로그인 실패
			model.addAttribute("errorMsg", "로그인 실패");
			return "common/errorPage";
		}
		
	}
	
	//로그아웃
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	//회원가입
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		return "member/memberEnrollForm";
	
		
		// WEB-INF/Views/member/memberEnrollForm.jsp
	}
	// 요청처리
	@RequestMapping("insert.me")
	public String insertMember(Member m, HttpSession session, Model model) {
		
		//System.out.println(m);
		
		/*
		 *  1. 한글 부분이 깨짐.
		 *     post방식 요청은 인코딩 해야함.
		 *     해결 : -> 스프링에서 제공하고 있는 인코딩 필터를 추가하면 끝. -> web.xml에 인코딩 추가.
		 *     
		 *  2. 만약에 나이를 입력하지 않고 넘어오게 되면 "" 빈 문자열이 넘어오기 때문에
		 *  	int 필드인 age에 담으려고 할때  파시함
		 *  	근데 "" -> 파싱 -> NumberFormatException 발생!
		 *  	해결 : -> int형 -> String형 변환
		 *  
		 *  3. 비밀번호가 사용자가 입력한 그대로의 평문!
		 *  	해결 = 암호화 작업을 거쳐서 DB에 저장
		 *  		
		 *  		bcrypt방식(솔팅기법)	-> BCryptPasswordEncoder
		 *  		평문 + salt(랜덤값)	--> 암호문
		 *  		따라서 똑같은 평문을 입력하여도 매번 다른 암호문이 나온다.
		 *  
		 *  
		 */
		
		System.out.println("암호화 전(평문) : " + m.getUserPwd());
		
		//암호화 작업(암호문 만들기)
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		System.out.println("암호화 후:" + encPwd);
		
		m.setUserPwd(encPwd); // setter를 통해 암호문 담기 
		
		int result = mService.insertMember(m); 
		if(result>0) { //성공 => 메인 url요청
			session.setAttribute("alertMsg", "성공적으로 회원가입되었습니다!");
			return "redirect:/"; 
		}else {
			model.addAttribute("errorMsg","회원가입 실패");
			return "common/errorPage"; 
		}

	}
	
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
	}
	
	@RequestMapping("update.me")
	public String updateMember(Member m, HttpSession session, Model model) {
		
		int result = mService.updateMember(m);
		
		if(result >0 ) {	// 성공 -> 세션에 담긴 member객체 바꾸기 ->마이페이지 재요청
			
			session.setAttribute("loginUser", mService.loginMember(m));
			session.setAttribute("alertMsg", "성공적으로 정보 변경 되었습니다.");
			
			return "redirect:myPage.me";
			
		}else {	// 실패 -> 에러문구 담아서 에러포워딩
			
			model.addAttribute("errorMsg", "정보 변경 실패");
			return "common/errorPage";
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}














