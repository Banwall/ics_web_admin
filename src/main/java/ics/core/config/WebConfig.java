package ics.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private SessionInterceptor sessionInterceptor;
		
	//	파일 사이즈
	private final int FILE_MAX_SIZE = 10 * 1024 * 1024;
	
	//	인코딩
	private final String DEFAULT_ENCODING = "UTF-8";
	
	@Value("${file.resource.path}")
	private String resourcePath;
	
	@Value("${file.upload.path}")
	private String uploadpath;
	
	////	메시지 - 다국어
    /**
     * 메세지 소스를 생성한다.
     * @return
     */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		// 메세지 프로퍼티파일의 위치와 이름을 지정한다.
		source.setBasename("classpath:/messages/message");
		// 기본 인코딩을 지정한다.
		source.setDefaultEncoding("UTF-8");
		// 프로퍼티 파일의 변경을 감지할 시간 간격을 지정한다.
		source.setCacheSeconds(60);
		// 없는 메세지일 경우 예외를 발생시키는 대신 코드를 기본 메세지로 한다.
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

    /**
     * 변경된 언어 정보를 기억할 로케일 리졸버를 생성한다.
     * 여기서는 세션에 저장하는 방식을 사용한다.
     * @return
     */
    @Bean
    public SessionLocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    /**
     * 언어 변경을 위한 인터셉터를 생성한다.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * 인터셉터를 등록한다.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//	다국어
        registry.addInterceptor(localeChangeInterceptor());
        
        //	인터셉터
        registry.addInterceptor(sessionInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/resources/**", "/error/**", "/auth/**", "/logout", "/api/**"); // 정적 리소스 파일은 제외. 제외하지 않으면 에러 발생
		
    }
    
    /**
     * 파일 업로드 설정
     */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding(DEFAULT_ENCODING); // 파일 인코딩 설정
	    multipartResolver.setMaxUploadSize(FILE_MAX_SIZE); // 10MB
	    multipartResolver.setMaxUploadSizePerFile(FILE_MAX_SIZE); // 10MB
		return multipartResolver;
	}
	
	/**
	 * 파일 리소스 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler(uploadpath)	//	/upload/** 파일을 불러올수 있다.
				.addResourceLocations(resourcePath);
				//.setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
	}
}
