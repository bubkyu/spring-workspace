package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;
@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	
	// 메뉴바 클릭시 : list.bo					-> 기본적으로 1페이지 요청
	// 페이지 클릭시 : list.bo?currentPage=3
	@RequestMapping("list.bo")
	/*
	 * 기존사용방식
	 * public void selectList(int currentPage){// 요청시 다음과 같이 값이 넘어오지 않을 경우 null 이 주입이 되는데 -> null 이 기본자료형에 주입될 수 없다는 오류
	 */
	
	public String selectList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model) {
		
		
		//System.out.println(currentPage);
		int listCount = bService.selectListCount();
		// pageLimit : 10, boardLimit : 5
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		return "board/boardListView";
		
		
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile,HttpSession session, Model model) {
		//System.out.println("board:" + b);
		//System.out.println("upfile:" + upfile.getOriginalFilename());
		
		if(!upfile.getOriginalFilename().equals("")) {
			
			String changeName = saveFile(upfile,session); 
			
			if(changeName != null) {
				b.setOriginName(upfile.getOriginalFilename());
				b.setChangeName("resources/uploadFiles/"+ changeName);				
			}
		}
		System.out.println(b);
		
		int result = bService.insertBoard(b); 
		//*과제
		//dao 까지 insert 넘기시
		if(result>0) {//성공 시 alert 리시트페이지 재요청 
			session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다!");
			return "redirect:list.bo";
		}else { //실패 경우 -> 에러문구
			model.addAttribute("errorMsg", "게시글 등록 실패");
			return "common/errorPage"; 
		}
	}
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int bno, ModelAndView mv) {
		
		int result = bService.increaseCount(bno);
		
		if(result > 0) { // 유효한 게시글 -> 상세조회후 -> 상세보기페이지 포워딩
			
			Board b = bService.selectBoard(bno);
			//mv.addObject("b", b);
			//mv.setViewName("board/boardDetailView");
			//mv.addObject("b", b).setViewName("board/boardDetailView");
			
			mv.addObject("b", bService.selectBoard(bno)).setViewName("board/boardDetailView");
			
			
		}else {	// 유효한 게시글 아님 -> 에러페이지 포워딩
			//mv.addObject("errorMsg", "유효환 게시글이 아니거나 삭제된 게시글입니다.");
			//mv.setViewName("common/errorPage");
			
			mv.addObject("errorMsg", "유효한 게시글이 아니거나 삭제된 게시글입니다.").setViewName("common/errorPage");
		}
		
		return mv;
		
	}
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String fileName,HttpSession session,Model model) {
		
		int result = bService.deleteBoard(bno); 
		
		if(result>0) {
			
			// 기존의 첨부파일이 있었을 경우 => 서버에서 파일 삭제
			if(!fileName.equals("")) {
				
				//삭제할 파일의 물리적인 경로 
				String removeFilePath = session.getServletContext().getRealPath(fileName);
				new File(removeFilePath).delete(); 
			}
			
			session.setAttribute("alertMsg","삭제 성공 했습니다.");
			return "redirect:list.bo";
		}else {
			model.addAttribute("errorMsg","삭제 실패 하셨습니다.");
			return "common/errorPage"; 
		}
		
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {
		
		Board b = bService.selectBoard(bno);
		model.addAttribute("b", b);
		
		return "board/boardUpdateForm";
		
	}
	
	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupfile,HttpSession session, Model model) {
		//System.out.println(b.getBoardNo());
		
		//새로 넘어온 첨부파일이 있을 경우
		if(!reupfile.getOriginalFilename().contentEquals("")) {
			
			//기존의 첨부파일이 있었을 경우 => 서버에 업로드 된 기존 첨부파일 지울라고
			if(b.getOriginName() != null) {
				String removeFilePath = session.getServletContext().getRealPath(b.getChangeName());
				new File(removeFilePath).delete(); 
			}
			//그리고 새로 넘어온 파일 서버에 업로드 시켜야됨 --> 위에 만들어 놓은 saveFile메소드 호출(업로드시키고자하는 file, session); 
			String changeName = saveFile(reupfile, session);
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/"+ changeName);
			
		}
		int result = bService.updateBoard(b);
		
		if(result>0) {//성공
			session.setAttribute("alertMsg", "수정 성공했습니다");
			return "redirect:detail.bo?bno=" + b.getBoardNo();
		}else { //실패
			model.addAttribute("errorMsg","수정 실패했습니다.");
			return "common/errorPage";
		}
	}
	
	
	
	// 전달된 파일 업로드용 공통 메소드
	public String saveFile(MultipartFile upfile, HttpSession session) {
		
		String originName = upfile.getOriginalFilename(); 
		// 저장시킬 물리적인 경로 필요 
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		//uploadFiles/ 뒤에 / 해야 하는 이유는 저 파일 안에 사진이 들어 가기때문에
		
		//원본명("aaa.jpg") --> 실제서버에 업로될명(2020103014433024567.jpg)
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int ranNum = (int)(Math.random()*90000+10000); 
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return changeName; 
	}//e.saveFile

}






