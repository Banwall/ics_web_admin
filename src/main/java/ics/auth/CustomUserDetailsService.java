package ics.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AuthMapper authMapper;
	
	/* DB에서 유저정보를 불러온다.
	 * Custom한 Userdetails 클래스를 리턴 해주면 된다.
	 * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserVO userVO = authMapper.findByLoginId(username);		//	필요한 데이터 넣어주기
	
		//	1. UserDetailsService -> 
		//	2. AuthenticationFailureHandler ->
		//	오류처리를 하지 않으면 서버 에러 
		if(userVO == null) {
			//	userNotFoundPassword 예외처리가 안된다 -> InternalAuthenticationServiceException 변경하여 사용함
			//	로그인 ID가 없는경우
			throw new InternalAuthenticationServiceException(username);
		}
		
		//	권한 넣어주기
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userVO.getAuth_id()));
		
		CustomUserDetails customUserDetails = new CustomUserDetails(userVO, authorities);
		return customUserDetails;
	}
	
}
