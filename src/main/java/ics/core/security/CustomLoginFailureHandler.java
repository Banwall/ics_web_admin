package ics.core.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 실패 핸들러
 * @author 
 *
 */
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler{
	
	/**
	 * 스프링부트에서는 messages.properties 값을 가져올 수 있다
	 */
    @Autowired
    private MessageSource messageSource;
  
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
	
		String defaultFailureUrl = "/auth/login/form";
		
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMsg = null;
    	
        if(exception instanceof BadCredentialsException) {										//	비밀 번호 혹은 존재 하지 않는 아이디 일때
     
//        	loginService.addFailureCount(username);
//        	
//        	int failureCount = loginService.getFailureCount(username);
//        	
//        	if(failureCount >= 5) {
//        		//	비밀번호가 5이상이면 잠금
//        		loginService.disabledUsername(username);
//        	}
        	errorMsg = messageSource.getMessage("error.BadCredentials", null, Locale.KOREA); 	
        } else if(exception instanceof InternalAuthenticationServiceException) {
        	//	시스템 문제로 내부 인증 관련 처리 요청을 할 수 없는 경우	InternalAuthenticationServiceException
        	//	userNotFoundPassword 예외처리가 안된다 -> InternalAuthenticationServiceException 변경하여 사용하였으므로
        	//	id가 존재 하지 않는 경우로 변경 >>>> 따라서 비밀번호 혹은 존재 하지 않는 아이디
        	errorMsg = messageSource.getMessage("error.BadCredentials", null, Locale.KOREA);
        } else if(exception instanceof DisabledException) {										//	인증거부 : 잠긴 계정일 경우
        	
        	errorMsg = messageSource.getMessage("error.Disaled", null, Locale.KOREA);
        } else if(exception instanceof CredentialsExpiredException) {							//	인증거부 : 비밀번호 유효 기간 만료
        	
        	errorMsg = messageSource.getMessage("error.CredentialsExpired", null, Locale.KOREA);
        } 
        //request.setAttribute("username", username);
        //request.setAttribute("password", password);
        request.setAttribute("errorMsg", errorMsg);
        //request.setAttribute("isError", isError);
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}

}
