package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

//@Component  	//@Component 는 단순한 빈으로 등록하기 위한 어노테이션

@Service		//@Service는 보다 구체화된 즉, Service의 의미를 가지는 클래스라는 빈으로 등록 
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDao mDao;
	
	
	@Override
	public Member loginMember(Member m) {
		
		//Member loginUser = mDao.loginMember(sqlSession, m);
		//return loginUser;
		
		return mDao.loginMember(sqlSession, m);
		
		
	}

	@Override
	public int insertMember(Member m) {
		
		return 0;
	}

	@Override
	public int updateMember(Member m) {
		
		return 0;
	}

	@Override
	public int deleteMember(String userId) {
		
		return 0;
	}

	@Override
	public int idCheck(String userId) {
		
		return 0;
	}

}
