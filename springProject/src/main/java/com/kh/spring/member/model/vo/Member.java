package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/*
 *  * Lombok(롬복) - 필수는 아니지만, 프로젝트 진행시 사용하기위해서는 모두가 사용해야한다.
 *  
 *  롬복은 자바에서 vo클래스를 만들 때 필드에 맞춰서
 *  getter/setter, toString, 생성자 등등 반듯이 필요하는 코드들을
 *  어노테이션을 통해 자동으로 내부적으로 만들어지게끔 하는 라이브러리
 *  -> 필드를 수정하거나 뒤늦게 추가한다거나 했을 때 setter/getter, constructor, toString 다 수정할 필요없음.
 *  
 *   	1. Maven을 이용해서 라이브러리 추가
 *		2. 롬복은 라이브러리 추가만으로는 안되고 설치해야한다.
 *			.jar 파을 찾아서 더블클릭해서 Install
 *		3. 이클릅스 또는 STS 재부팅 (restart)
 *		4. 어노테이션 기술			
 */

@NoArgsConstructor			// 기본생성자
@AllArgsConstructor			// 매개변수 생성자
@Setter
@Getter
@ToString
public class Member {
	
	/*
	 * private String pName; 	${pName} -> getpName() 이렇게 찾아야하는데
	 * 							롬복을 사용할경우 네이밍 규칙이 조금 다르기에
	 * 							getPName() 으로 나옴. 그래서 꼭 사용하기 위해선
	 * 	private String paName;	String psName 이런식으로 소문자 2개는 만들어야한다.
	 * 							또는 이엘테그 {PName} 이런식으로 사용해야한다.
	 * 							즉, 롬목 사용하시 왠만하면 앞에 소문자 사용하지 말아라.
	 */
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	
	
	
	/*	
	public Member() {}

	public Member(String userId, String userPwd, String userName, String email, String gender, String age, String phone,
			String address, Date enrollDate, Date modifyDate, String status) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.phone = phone;
		this.address = address;
		this.enrollDate = enrollDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Member [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + ", email=" + email
				+ ", gender=" + gender + ", age=" + age + ", phone=" + phone + ", address=" + address + ", enrollDate="
				+ enrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}
	
	*/
	
}
