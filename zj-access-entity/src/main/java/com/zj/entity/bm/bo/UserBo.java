package com.zj.entity.bm.bo;

import java.io.Serializable;
import com.zj.entity.bm.dto.UserDto;

public class UserBo implements Serializable{
	
	private static final long serialVersionUID = 5296372251661240609L;
	private String returnJson;
	private UserDto userDto;
	public String getReturnJson() {
		return returnJson;
	}
	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	
	
	

	
}
