package com.ssafy.revibek.radio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.revibek.radio.dto.Mood;
import com.ssafy.revibek.radio.dto.MoodstatisticsDto;
import com.ssafy.revibek.radio.dto.RadioResponseDto;
import com.ssafy.revibek.radio.dto.RadioResponseDto.RadioSongDto;
import com.ssafy.revibek.radio.dto.ReccommendationBatchDto;

/**
 * 라디오 세션 및 추천곡 Mapper
 * MyBatis를 통한 DB 상호작용
 */
@Mapper
public interface RadioMapper {
	
	/**
	 * 라디오 세션 생성 (DJ 멘트, 위로 메시지 포함)
	 * @return 생성된 session ID
	 */
	String insertRadioSession(
		@Param("sessionId") String sessionId,
		@Param("userId") String userId,
		@Param("mood") Mood mood,
		@Param("story") String story,
		@Param("djMent") String djMent,
		@Param("comfortText") String comfortText,
		@Param("novelExcerpt") String novelExcerpt
	);
	
	/**
	 * 단일 라디오 세션 조회 (추천곡 포함)
	 */
	RadioResponseDto selectRadioSessionById(String sessionId);
	
	/**
	 * 유저별 라디오 세션 목록 조회 (페이징)
	 */
	List<RadioResponseDto> selectRadioSessionsByUserId(
		@Param("userId") String userId,
		@Param("limit") int limit,
		@Param("offset") int offset
	);
	
	/**
	 * 유저의 라디오 세션 총 개수 (페이징용)
	 */
	int countRadioSessionsByUserId(String userId);
	
	/**
	 * 세션별 추천곡 단일 삽입
	 */
	void insertRecommendation(
		@Param("sessionId") String sessionId,
		@Param("songId") String songId,
		@Param("orderNum") int orderNum,
		@Param("reason") String reason
	);
	
	/**
	 * 세션별 추천곡 배치 삽입 (여러 곡 한번에)
	 */
	void insertRecommendationBatch(List<ReccommendationBatchDto> recommendations);
	
	/**
	 * 세션별 추천곡 목록 조회 (순서대로)
	 */
	List<RadioSongDto> selectRecommendationsBySessionId(String sessionId);
	
	/**
	 * 기분별 추천곡 통계 (분석용)
	 */
	List<MoodstatisticsDto> selectMoodStatistics();
	
	/**
	 * 라디오 세션 삭제 (CASCADE로 추천곡도 삭제됨)
	 */
	void deleteRadioSession(String sessionId);
	
	/**
	 * 최근 기분 조회 (마지막 라디오 세션의 mood)
	 */
	Mood selectLastMoodByUserId(String userId);
	
	/**
	 * 유저의 모든 라디오 세션 삭제 (회원 탈퇴용)
	 */
	void deleteAllRadioSessionsByUserId(String userId);
}
