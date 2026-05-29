package com.ssafy.revibek.radio.dto;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ssafy.revibek.radio.dto.Mood;

/**
 * 라디오 세션 응답 DTO
 * 생성된 라디오 세션의 모든 정보를 반환
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RadioResponseDto {
	
	@NotBlank
	private String id;  // 세션 ID
	
	@NotBlank
	private Mood mood;  // 감정 상태 (LONELY, EXCITED, NOSTALGIC, TIRED, HAPPY, SAD)
	
	private String story;  // 사용자 사연
	
	@NotBlank
	private String djMent;  // AI 생성 DJ 멘트
	
	@NotBlank
	private String comfortText;  // AI 생성 위로 메시지
	
	private String novelExcerpt;  // 활용된 소설 발췌
	
	private LocalDateTime createdAt;  // 생성 시간
	
	private List<RadioSongDto> songs;  // 추천 곡 목록
	
	/**
	 * 추천 곡 정보 내부 DTO
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class RadioSongDto {
		private String songId;      // 곡 ID
		private String title;       // 곡 제목
		private String artist;      // 아티스트명
		private int orderNum;       // 추천 순서 (1번부터)
		private String reason;      // 추천 이유
	}
}
