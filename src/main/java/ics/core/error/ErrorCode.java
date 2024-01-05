package ics.core.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
public enum ErrorCode{
	
	//	blank =	null, "", " " 허용 금지
	
	// COMMON
	//INVALID_CODE(400, "C001", "Invalid Code"), 
	//RESOURCE_NOT_FOUND(204, "C002", "Resource not found"),
	//EXPIRED_CODE(400, "C003", "Expired Code"),

	// AWS
	//AWS_ERROR(400, "A001", "aws client error")
	
	
	//	status, code, message		
	INTERNAL_SERVER_ERROR(500, "500", "서버처리중 오류가 발생하였습니다"),
	//BAD_REQUEST(400, "400",),

	//	Sensor
	SENSOR_DUPLICATE(400, "S001", "센서 아이디 및 타입이 중복입니다"),
	SENSOR_ID_BLANK(400, "S002", "센서 아이디를 작성해주시기 바랍니다"),
	SENSOR_LOCATION_DESCRIPTION_BLANK(400, "S003", "센서 위치를 작성해주시기 바랍니다"),
	SENSOR_TYPE_DUPLICATE(400, "S004", "센서 타입 코드가 중복입니다"),
	SENSOR_TYPE_CODE_BLANK(400, "S005", "센서 타입 코드를 작성해주시기 바랍니다"),
	SENSOR_TYPE_NAME_BLANK(400, "S006", "센서 타입 명을 작성해주시기 바랍니다"),
	SENSOR_THRESHOLD_NULL(400, "S007", "센서 임계치값을 작성해주시기 바랍니다"),
	
	//	Member
	MEMBER_ID_BLANK(400, "M001", "사용자 아이디를 작성해주시기 바랍니다"),	
	MEMBER_PWD_BLANK(400, "M002", "사용자 비밀번호를 작성해주시기 바랍니다"),
	MEMBER_NAME_BLANK(400, "M003", "사용자 이름을 작성해주시기 바랍니다"),
	MEMBER_EMAIL_REG(400, "M004", "이메일 형식에 맞게 작성해주시기 바랍니다"),
	MEMBER_ID_DUPLICATE(400, "M005", "이미 사용중인 아이디입니다"),
	
	//	Company
	COMPANY_NAME_BLANK(400, "C001", "회사 이름을 작성해주시기 바랍니다"),
	COMPANY_EMAIL_BLANK(400, "C002", "이메일을 작성해주시기 바랍니다"),
	COMPANY_EMAIL_REG(400, "C003", "이메일 형식에 맞게 작성해주시기 바랍니다"),
	COMPANY_ROLE_BLANK(400, "C004", "회원사 종류를 선택해주시기 바랍니다"),
	;// 

	private int status;
	private String code;
	private String message;
	private String detail;

	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
 
}