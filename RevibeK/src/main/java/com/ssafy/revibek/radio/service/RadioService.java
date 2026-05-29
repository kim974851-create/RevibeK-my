package com.ssafy.revibek.radio.service;

import org.springframework.stereotype.Service;

import com.ssafy.revibek.radio.mapper.RadioMapper;
import com.ssafy.revibek.radio.dto.RadioRequestDto;
import com.ssafy.revibek.radio.dto.RadioResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RadioService {

	private final RadioMapper radioMapper;
	
	//라디오 세션 생성
	public String createSession(RadioRequestDto dto) {
		// TODO: Claude API 호출 → DJ 멘트, 위로 메시지, 소설 구절 생성
        // TODO: 곡 추천 로직
		// [FIX] 세션을 실제로 저장할 수 있도록 UUID를 먼저 생성해 주입한다.
		String sessionId = UUID.randomUUID().toString();
		dto.setId(sessionId);
		// [FIX] Mapper insert 호출을 추가해 POST 요청이 DB에 반영되도록 수정.
		radioMapper.insertRadioSession(dto);
        // TODO: radioMapper.insertRecommendation()
		
		//Claude API 연동 후 추천곡 저장 로직 채우기
		return sessionId;
	}
	
	
	//세션 단건 조회
	public RadioResponseDto getSession(String id) {
		// TODO: radioMapper.selectSessionById()
        // TODO: radioMapper.selectRecommendationsBySessionId()
		RadioResponseDto session = radioMapper.selectRadioSessionById(id);
		if(session == null) {
			throw new RuntimeException("존재하지 않는 세션입니다.");
		}
		List<RadioResponseDto.RadioSongDto> songs = 
				radioMapper.selectRecommendationBySessionId(id);
		session.setSongs(songs);
		return session; 
	}
	
	//유저 세션 목록 조회
	public List<RadioResponseDto> getSessionByUser(String userId){
		List<RadioResponseDto> sessions = radioMapper.selectRadioSessionByUserId(userId);
		// [FIX] 목록 조회에서도 각 세션의 추천곡(songs)을 포함하도록 보강.
		for (RadioResponseDto session : sessions) {
			List<RadioResponseDto.RadioSongDto> songs =
					radioMapper.selectRecommendationBySessionId(session.getId());
			session.setSongs(songs);
		}
		return sessions;
	}
}
