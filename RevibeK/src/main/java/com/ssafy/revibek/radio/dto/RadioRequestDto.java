package com.ssafy.revibek.radio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ssafy.revibek.radio.dto.Mood;

/**
 * 라디오 세션 생성 요청 DTO
 * 사용자가 보내는 요청 데이터
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RadioRequestDto {
	
	@NotBlank(message = "userId는 필수입니다")
	private String userId;
	
	@NotNull(message = "mood는 필수입니다")
	private Mood mood;  // LONELY, EXCITED, NOSTALGIC, TIRED, HAPPY, SAD
	
	@NotBlank(message = "story는 필수입니다")
	private String story;  // 사용자의 사연/스토리
}
