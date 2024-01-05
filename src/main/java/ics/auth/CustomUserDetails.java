package ics.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomUserDetails extends User{

	private static final long serialVersionUID = 1L;
	private String auth_id;
	private Integer idx;
	private String email;
	private String tel;
	private String name;
	private String pwd;

	private Integer companyIdx;
	private String companyName;
	
	private boolean alarm;
	private String	uuid;
	private String	fcmToken;
	
	
    public CustomUserDetails(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
    	
        super(
            userVO.getId(),			//	username
            userVO.getPassword(),	//	password
            userVO.getStatus() == 1 ? true : false,	//	1승인, 2미승인
            true,					//	accountNonExpired
            true,					//	credentialsNonExpired;
            true,
            authorities
        );
        
        this.auth_id = userVO.getAuth_id();
        this.idx = userVO.getIdx();
        this.email = userVO.getEmail();
        this.tel = userVO.getTel();
        this.name = userVO.getName();
        this.pwd = userVO.getPassword();
        this.fcmToken = userVO.getFcmToken();
        this.uuid = userVO.getUuid();
        this.alarm = userVO.isAlarm();
        this.companyIdx = userVO.getCompanyIdx();
        this.companyName = userVO.getCompanyName();
        
    }
    
}
