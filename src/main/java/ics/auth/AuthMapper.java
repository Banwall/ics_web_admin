package ics.auth;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
	
	/**
	 * 사용자 아이디로 회원조회
	 * @param username
	 * @return
	 */
	public UserVO findByLoginId(String username);

}
