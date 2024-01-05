package ics.core.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ics.core.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
		
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		
		if(!SessionUtil.hasSession()) {
			if("/".equals(uri)) {
				response.sendRedirect("/auth/login/form");
				return false;
			}
		}else {
			
			if("/auth/login/form".equals(uri)) {
				System.out.println("session");
			}
			
			
			String roleId = SessionUtil.getRoleId();
			Integer companyIdx = SessionUtil.getCompanyIdx();
			
			String operatorOther = "/operator/other";
			
			if("ROLE_O".equals(roleId)) {
					//	신규 등록한 운영사는 빈페이지로 이동
				if(companyIdx != 2) {
					if(!operatorOther.equals(uri)) {
						response.sendRedirect(operatorOther);
						return false;
					}
				}else {
					//	인천교통공사만 operator/main 등을 이용가능	
					//	/operator/other 으로 들어올경우 메인으로 이동
					if(operatorOther.equals(uri)) {
						response.sendRedirect("/operator/main");
						return false;
					}
				}
			}
		} 
		
		
		
		
		
				
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, 
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, 
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
