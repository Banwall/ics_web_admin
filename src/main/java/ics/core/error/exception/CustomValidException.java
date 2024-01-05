package ics.core.error.exception;

import ics.core.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomValidException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;

	/**
	 * 
	 * @param detail	
	 * @param errorCode	
	 */
	public CustomValidException(String detail, ErrorCode errorCode) {
		super(detail);
		this.errorCode = errorCode;
	}

	public CustomValidException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}

}
