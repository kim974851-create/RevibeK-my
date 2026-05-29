package com.ssafy.revibek.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDto {
	
	private String id;
	private String nickname;
	private String email;
	private String provider;
	
	
	
	
	

}
