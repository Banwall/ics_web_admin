package ics.core.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@ToString
public class ErrorResponse {

	private int status; 	// 에러 상태
	private String code; 	// 에러 코드
	private String message; // 에러 메시지 - 사용자 정의
	private String detail; // 상세 메시지
	
	
	private boolean result;

	public ErrorResponse(ErrorCode code) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.code = code.getCode();
		this.detail = code.getDetail();
		this.result = false;
	}

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code);
	}
}