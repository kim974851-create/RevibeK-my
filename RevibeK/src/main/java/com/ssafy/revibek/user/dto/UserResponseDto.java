package com.ssafy.revibek.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDto {
	
	private String id;
	private String nickname;
	private String email;
	private String provider;
	private String providerId;
	
	
	
	

}
