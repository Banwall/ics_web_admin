package ics.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
/**
 * 에러페이지 403
 * @author 
 *
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	/**
	 * 자동 맵핑
	 */
	private String errorPage = "/error/403";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if(accessDeniedException instanceof AccessDeniedException) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		}
		request.getRequestDispatcher(errorPage).forward(request, response);
	}
}
