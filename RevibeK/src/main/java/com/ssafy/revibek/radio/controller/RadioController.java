package com.ssafy.revibek.radio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.revibek.radio.dto.RadioRequestDto;
import com.ssafy.revibek.radio.dto.RadioResponseDto;
import com.ssafy.revibek.radio.service.RadioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/radio")
@RequiredArgsConstructor
public class RadioController {

	@Autowired
    private final RadioService radioService;  // ✅ final 추가 + 생성자 주입

    /**
     * 라디오 세션 생성
     */
    @PostMapping
    public ResponseEntity<RadioResponseDto> createSession(@RequestBody RadioRequestDto dto) {
        RadioResponseDto result = radioService.createSession(dto);  // ✅ 반환값 받기
        return ResponseEntity.ok(result);  // ✅ DTO 반환
    }

    /**
     * 세션 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<RadioResponseDto> getSession(@PathVariable String id) {
        return ResponseEntity.ok(radioService.getSession(id));
    }

    /**
     * 유저 세션 목록 조회 (페이징)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RadioResponseDto>> getSessionsByUser(
        @PathVariable String userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(radioService.getSessionsByUser(userId, page, size));  // ✅ 페이징
    }

    /**
     * 유저 세션 총 개수 조회 (페이징용)
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Integer> countSessionsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(radioService.countSessionsByUser(userId));
    }

    /**
     * 세션 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable String id) {
        radioService.deleteSession(id);
        return ResponseEntity.ok("세션 삭제 완료");
    }

    /**
     * 유저의 최근 기분 조회
     */
    @GetMapping("/user/{userId}/last-mood")
    public ResponseEntity<String> getLastMood(@PathVariable String userId) {
        return ResponseEntity.ok(radioService.getLastMood(userId));
    }
}