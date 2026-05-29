package com.ssafy.revibek.radio.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.revibek.radio.dto.RadioRequestDto;
import com.ssafy.revibek.radio.dto.RadioResponseDto;
import com.ssafy.revibek.radio.mapper.RadioMapper;

import lombok.RequiredArgsConstructor;

/**
 * 라디오 세션 Service
 * Claude API, TTS API, RecommendService 연동 담당
 */
@Service
@RequiredArgsConstructor
public class RadioService {

	@Autowired
	private final RadioMapper radioMapper;
	// TODO: private final ClaudeApiClient claudeClient;  // Claude API 클라이언트
	// TODO: private final RecommendService recommendService;  // A 팀원이 만든 추천 서비스
	// TODO: private final TtsApiClient ttsClient;  // TTS API 클라이언트
	
	/**
	 * 라디오 세션 생성
	 * @param dto 요청 DTO (userId, mood, story)
	 * @return 생성된 라디오 세션
	 */
	public RadioResponseDto createSession(RadioRequestDto dto) {
		// TODO: 1. Claude API 호출 → DJ 멘트, 위로 메시지, 소설 구절 생성
		// String djMent = claudeClient.generateDjMent(dto.getStory());
		// String comfortText = claudeClient.generateComfortText(dto.getMood().getDisplayName());
		// String novelExcerpt = claudeClient.generateNovelExcerpt(dto.getMood().getDisplayName());
		
		// TODO: 2. A 팀원의 RecommendService로 추천곡 조회
		// List<Song> recommendedSongs = recommendService.getSongsByMood(dto.getMood(), 10);
		
		// TODO: 3. TTS API로 DJ 멘트 음성화
		// String djMentAudio = ttsClient.synthesize(djMent);
		
		// UUID 생성 (Service에서 생성)
		String sessionId = UUID.randomUUID().toString();
		
		// 4. 세션 저장 (현재는 TODO 항목이 완료될 때까지 스킵)
		// radioMapper.insertRadioSession(
		//     sessionId, dto.getUserId(), dto.getMood(), dto.getStory(),
		//     djMent, comfortText, novelExcerpt
		// );
		
		// 5. 추천곡 배치 저장
		// List<RecommendationBatchDto> recommendations = new ArrayList<>();
		// for (int i = 0; i < recommendedSongs.size(); i++) {
		//     recommendations.add(RecommendationBatchDto.builder()
		//         .sessionId(sessionId)
		//         .songId(recommendedSongs.get(i).getId())
		//         .orderNum(i + 1)
		//         .reason("AI 추천")
		//         .build());
		// }
		// radioMapper.insertRecommendationBatch(recommendations);
		
		// 6. 전체 세션 조회 후 반환
		// return radioMapper.selectRadioSessionById(sessionId);
		
		return null;  // TODO: 구현 완료 후 실제 객체 반환
	}
	
	/**
	 * 세션 단건 조회 (추천곡 포함)
	 * @param sessionId 조회할 세션 ID
	 * @return 라디오 세션 정보 및 추천곡 목록
	 */
	public RadioResponseDto getSession(String sessionId) {
		// radioMapper.selectRadioSessionById()는 LEFT JOIN으로 이미 추천곡을 포함하므로
		// 별도로 selectRecommendationsBySessionId()를 호출할 필요 없음
		RadioResponseDto session = radioMapper.selectRadioSessionById(sessionId);
		
		if (session == null) {
			throw new RuntimeException("존재하지 않는 세션입니다.");
		}
		
		return session;
	}
	
	/**
	 * 유저별 세션 목록 조회 (페이징)
	 * @param userId 유저 ID
	 * @param page 페이지 번호 (0부터 시작)
	 * @param size 페이지 사이즈
	 * @return 라디오 세션 목록
	 */
	public List<RadioResponseDto> getSessionsByUser(String userId, int page, int size) {
		int offset = page * size;
		return radioMapper.selectRadioSessionsByUserId(userId, size, offset);
	}
	
	/**
	 * 유저별 세션 총 개수 (페이징용)
	 * @param userId 유저 ID
	 * @return 세션 총 개수
	 */
	public int countSessionsByUser(String userId) {
		return radioMapper.countRadioSessionsByUserId(userId);
	}
	
	/**
	 * 세션 삭제
	 * @param sessionId 삭제할 세션 ID
	 */
	public void deleteSession(String sessionId) {
		radioMapper.deleteRadioSession(sessionId);
	}
	
	/**
	 * 최근 기분 조회
	 * @param userId 유저 ID
	 * @return 가장 최근 감정 상태
	 */
	public String getLastMood(String userId) {
		return radioMapper.selectLastMoodByUserId(userId).getDisplayName();
	}
}
