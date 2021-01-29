package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {

	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		
		// * maxPage : 제일 마지막 페이지(총 페이지)
		//				listCount, boardLimit
		int maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		// * startPage : 페이징바의 시작수
		//				currentPage, pageLimit
		int startPage = (currentPage-1) / pageLimit * pageLimit + 1;
		
		// * endPage : 페이징바의 마지막수
		//				startPage, pageLimit, maxPage
		
		int endPage = startPage + pageLimit -1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		return new PageInfo(listCount, currentPage, startPage, endPage, maxPage, pageLimit, boardLimit);
		
	}
		
}
