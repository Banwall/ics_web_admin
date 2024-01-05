package ics.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import ics.auth.CustomUserDetails;
import ics.auth.CustomUserDetailsService;
import ics.core.util.StringUtil;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//	로그인 파라미터로 인증 된 값
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
		
		String pwd = StringUtil.encrypt(password);
		
		//	패스워드가 일치하지 않을 때
		if(! (pwd.equals(customUserDetails.getPassword())) ) {
			throw new BadCredentialsException(username);
		}
		
		//	계정 잠겨 있는경우 에러 
		if(!customUserDetails.isEnabled()) {
			throw new DisabledException(username);
		}
		//System.out.println(customUserDetails.toString());
		return new UsernamePasswordAuthenticationToken(customUserDetails, customUserDetails.getPassword(), customUserDetails.getAuthorities());
	}

	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
