package ics.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity	
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${file.upload.path}")
	private String file_upload_path; 

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
    	return new CustomAuthenticationProvider();
    }
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(customAuthenticationProvider());
	}
	
	/**
	 * 로그인 실패
	 * @return
	 */
    @Bean
	public CustomLoginFailureHandler customFailureHandler() {
		return new CustomLoginFailureHandler();
	};
	
	/**
	 * 로그인 성공 
	 * @return
	 */
	@Bean
	public CustomLoginSuccessHandler customSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}

	/**
	 * 접근 권한 페이지 - 403
	 * @return
	 */
	//@Bean
	//public CustomAccessDeniedHandler customAccessDeniedHandler() {
	//	return new CustomAccessDeniedHandler();
	//}
	
	/**
	 * 로그아웃 핸들러
	 * @return
	 */
	@Bean
	public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}
	
	// 세션관리 : logout 후 login할 때 정상동작을 위함
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
    //	
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**",  "/favicon.ico", "/error/**", "/robots.txt");
	}

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().ignoringAntMatchers("/api/**")				//	API는 csrf 무효
			.and()
			.authorizeRequests()						//	authorizeRequests(): 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미
				.antMatchers(file_upload_path).permitAll()	//	업로드, 링크
				.antMatchers("/api/**").permitAll()
				.antMatchers("/common/**").permitAll()
				.antMatchers("/error/**").permitAll()
				.antMatchers("/auth/**").permitAll()		//	로그인 / 회원가입
				.antMatchers("/manager/**").hasRole("A")	//	관리사
				.antMatchers("/operator/**").hasRole("O")	//	운영사
				.antMatchers("/member/**").hasRole("M")		//	회원사
				.anyRequest().authenticated()				//	다른 요청은 로그인을 요구함
			.and() // 로그인 설정
				.formLogin()
				.loginPage("/auth/login/form")				//	로그인 페이지 호출
				//.loginProcessingUrl("/login/proc")	//	로그인 프로세스
				.successHandler(customSuccessHandler())	//	로그인 성공시 핸들러		
				.failureHandler(customFailureHandler())	//	로그인 실패시 핸들러
				//.defaultSuccessUrl("/") 				//	로그인이 성공했을 때 이동되는 페이지이며, 마찬가지로 컨트롤러에서 URL 매핑
			.and() // 로그아웃 설정
				.logout()
				//.logoutUrl("/logout/proc")			//	로그아웃 페이지로 이동
				.invalidateHttpSession(true)
				.logoutSuccessHandler(customLogoutSuccessHandler())
				.deleteCookies("JSESSIONID", "rememeber-me")//로그아웃 후 쿠키 삭제
			//.and()
				// 403 예외처리 핸들링
            //	.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
			.and()
				.rememberMe()							//	rememberMe기능 작동
				.key("icsWebAdmin")
				.rememberMeParameter("rememeber-me") 	//	기본 파라미터명은 remember-me
				.tokenValiditySeconds(1209600)			//	default는 14일
				.userDetailsService(userDetailsService);
				//.alwaysRemember(true)					//	remember me 기능이 활성화되지 않아도 항상 실행. default false
		
           // .and()
            	//.sessionManagement()
            	//.maximumSessions(1)						//	 한 사용자가 몇 개의 세션까지 사용가능할지 여부로, 1로 하면 동시 접속 불가 설정을 할 수 있다.
            	//.maxSessionsPreventsLogin(false)		//	 false일 경우 : 먼저 접속한 사용자 logout 처리
				//.expiredUrl("/error/duplicatedLogin")
				//.sessionRegistry(sessionRegistry());
	}
	
}
