package com.ssafy.revibek.radio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RadioRequestDto {
	
	// [FIX] 세션 생성 시 서비스에서 UUID를 주입해 DB에 함께 저장한다.
	private String id;
	private String userId;
	private String mood; // 외로운, 설레는, 그리운, 지친, 행복한 , 슬픈
	private String story;
	
	

}
