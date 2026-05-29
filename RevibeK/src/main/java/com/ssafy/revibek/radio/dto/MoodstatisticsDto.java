package com.ssafy.revibek.radio.dto;

import com.ssafy.revibek.radio.dto.Mood;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 기분별 라디오 세션 통계
 * select mood, Count(*) AS sessionCount, AVG(score) AS avgSongScore ... Group By mood
 * 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodstatisticsDto {
		private Mood mood;					// 감정상태
		private int sessionCount;			// 해당 감정의 세션 개수
		private double avbgSongScore;		// 평균 곡 점수
		private int totalRecommendations;	// 총 추천곡 수
	
	

}
