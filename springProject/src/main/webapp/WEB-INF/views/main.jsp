<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 
		회원서비스 - 로그인(R), 로그아웃, 회원가입(C), 정보변경(U), 회원탈퇴(U), 아이디중복체크(R) 
		게시판서비스 - 게시판리스트페이지, 페이징처리, 게시판작성하기(C), 첨부파일업로드, 게시판 상세조회(R), 게시판수정(U), 게시판삭제(U), 댓글리스트(R), 댓글작성(C)
		
		1. jstl 라이브러리(4개) 직접 lib 폴더만들어서 추가해야한다.
		2. views 폴더안에 필요한 폴더들 (common, member, board)
		3. 웹문서에서 필요로하는 외부문서들을 관리하는 폴더인 webapp/resources 폴더안에
			필요한 폴더들(css, jsp, images, uploadFiles)
		4. 패키지 만들기, 클래스 만들기
		5. resources 폴더 안에 mybatis와 관련된 mapper, config, xml문서를 만들기
			>> DTD : xml문서의 구조 및 해당 문서에서 사용할 수 있는 적합한 요소와 속성들을 정의
				         이 문서가 유효한지 유효성 검사도 됨.
			
			>> DTD 설정해두기 
			
			[window - preferences - xml - xml catalog - add]
			-Config 
			 Location : http://mybatis.org/dtd/mybatis-3-config.dtd
			 key      : -//mybatis.org//DTD Config 3.0//EN
			 
			 -Mapper
			 Location : http://mybatis.org/dtd/mybatis-3-mapper.dtd
			 key      : -//mybatis.org//DTD Mapper 3.0//EN

	 -->
	 	 <jsp:include page="common/menubar.jsp" />
		 <div style="height:400px"></div>
		 <jsp:include page ="common/footer.jsp"/>
	 
</body>
</html>