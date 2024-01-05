package ics.core.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ics.core.error.exception.CustomValidException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(annotations = RestController.class)
@RestController
@Slf4j
public class ErrorControllerAdvice {
	
	/**
	 * 400 - valid 에러
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
		//	e.toString()
		//	org.springframework.web.bind.MethodArgumentNotValidException:
		//	Validation failed for argument [0] in public org.springframework.http.ResponseEntity 
		//	ics.member.mypage.MemberMypageRestController.myInfoPwdModProc(ics.member.mypage.dto.MyInfoPwdModProcReqDTO) throws java.lang.Exception: [Field error in object 'myInfoPwdModProcReqDTO' on field 'memberPwd': rejected value []; codes [NotBlank.myInfoPwdModProcReqDTO.memberPwd,NotBlank.memberPwd,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [myInfoPwdModProcReqDTO.memberPwd,memberPwd]; arguments []; default message [memberPwd]]; default message [MEMBER_PWD_BLANK]]
		//List<FieldError> errList = e.getFieldErrors();

		//	0번째만 리턴 한다
		String code = e.getAllErrors().get(0).getDefaultMessage();
		ErrorCode errCode = ErrorCode.valueOf(code);
		ErrorResponse response = ErrorResponse.of(errCode);
		log.error(e.toString());
		log.error(response.toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 커스텀 발리드 에러
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ CustomValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleCustomExceptions(CustomValidException e) {
		
		ErrorResponse response = ErrorResponse.of(e.getErrorCode());
		log.error(e.toString());
		log.error(response.toString());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	
	/**
	 * 500 에러
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected ResponseEntity<?> exception(Exception e) {
		ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		response.setDetail(e.getMessage());
		log.error(e.toString());
		log.error(response.toString());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/*
	 * @ExceptionHandler(value = BindException.class)
	 * 
	 * @ResponseStatus(value = HttpStatus.BAD_REQUEST) protected ResponseEntity
	 * bindingExcption(BindException e) {
	 * 
	 * return new ResponseEntity("errrrrr", HttpStatus.INTERNAL_SERVER_ERROR); }
	 */
	
	///	204
	/*
	 * @ExceptionHandler(value = NoSuchElementException.class)
	 * 
	 * @ResponseStatus(value = HttpStatus.BAD_REQUEST) protected
	 * ResponseEntity<ErrorResponse> handleNoSuchElementException(Exception e) {
	 * ErrorResponse response = ErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND);
	 * response.setDetail(e.getMessage()); return new ResponseEntity<>(response,
	 * HttpStatus.BAD_REQUEST); }
	 */

	
	/* Custom Error Handler */
	/*
	java, ajax : responseJSON: {message: '이메일 중복입니다', code: 'M001', status: 400, detail: 'list is null', result: false}
	
	postman :       
					"message": "이메일 중복입니다",
				    "code": "M001",
				    "status": 400,
				    "detail": "list is null",
				    "result": false
	 */
//	@ExceptionHandler(value = CustomException.class)
//	protected ResponseEntity handleCustomException(CustomException e) {
//		ErrorResponse response = ErrorResponse.of(e.getErrorCode());
//		response.setDetail(e.getMessage());
//		log.error(response.toString());
//		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
//	}


	
    
	
    
	 
}