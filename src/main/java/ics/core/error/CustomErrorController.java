package ics.core.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CustomErrorController{
	
	private final String ERROR_DUPLICATED_LOGIN = "error/duplicatedLogin";	//	로그인 중복
	
	/**
	 * 중복 로그인	> 시큐리티에서 전달
	 * @return
	 */
	@RequestMapping(value = "/error/duplicatedLogin")
	public String duplicatedLogin() {
		return ERROR_DUPLICATED_LOGIN;
		
	};	
}

//package ics.core.error;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller 
//public class CustomErrorController extends AbstractErrorController { 
//
//	private final String ERROR_503_PAGE_PATH = "error/503";					//	접근 불가능한 페이지(롤)
//	private final String ERROR_DUPLICATED_LOGIN = "error/duplicatedLogin";	//	로그인 중복
//	private final String ERROR_404_PAGE_PATH = "error/404"; 				//	페이지 없는경우
//	private final String ERROR_500_PAGE_PATH = "error/500"; 				//	서버 에러
//	private final String ERROR_ETC_PAGE_PATH = "error/error"; 				//	기타 에러 페이지
//	
//	
//	/**
//	 * @param errorAttributes
//	 */
//	public CustomErrorController(ErrorAttributes errorAttributes) {
//		super(errorAttributes);
//	}
//	
//
//	@RequestMapping(value="/error", produces = MediaType.TEXT_HTML_VALUE) 
//	public String errorHtml(HttpServletRequest request, HttpServletResponse response, Model model) {
//		
//		// 에러 코드를 획득한다. 
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		//System.out.println(status.toString());
//		// 에러 코드에 대한 상태 정보 
//		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString())); 
//		if (status != null) { // HttpStatus와 비교해 페이지 분기를 나누기 위한 변수 
//			int statusCode = Integer.valueOf(status.toString()); 
//		
//			// 404 error 
//			if (statusCode == HttpStatus.NOT_FOUND.value()) { 
//				// 에러 페이지에 표시할 정보 
//				model.addAttribute("code", status.toString()); 
//				model.addAttribute("msg", httpStatus.getReasonPhrase()); 
//				model.addAttribute("timestamp", new Date()); 
//				return ERROR_404_PAGE_PATH; 
//			}
//			// 500 error
//			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//				return ERROR_500_PAGE_PATH;
//			}  
//		}
//		// 	정의한 에러 외 모든 에러는 error/error 페이지로 보낸다. }
//		return ERROR_ETC_PAGE_PATH;
//	}
//	
//	@RequestMapping(value = "/error/503")
//	public String accessDenied() {
//		return ERROR_503_PAGE_PATH;
//	}
//	
//	@RequestMapping(value = "/error/duplicatedLogin")
//	public String duplicatedLogin() {
//		return ERROR_DUPLICATED_LOGIN;
//		
//	};
//	
//	/**
//	 * 공동 Ajax 에러
//	 * @param request
//	 * @return
//	 */
//	/*
//	 * @RequestMapping(value="/error") public ResponseEntity<Map<String, Object>>
//	 * apiError(HttpServletRequest request) { // request에서 status를 가져옴 HttpStatus
//	 * status = getStatus(request); // 발생 시간, status code, message를 가져옴
//	 * //Map<String, Object> model = getErrorAttributes(request,
//	 * isIncludeStackTrace(request, MediaType.ALL));
//	 * 
//	 * return new ResponseEntity<>(new HashMap<>(), status); }
//	 */
//
//}

