package com.ssafy.revibek.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.revibek.user.service.GoogleOAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class OauthController {

	private  GoogleOAuthService googleOAuthService;
	
	//로그인 URL 반환
	@GetMapping("/google/login")
	public ResponseEntity<String> googleLogin(){
		return ResponseEntity.ok(googleOAuthService.getGoogleLoginUrl());
	}
	
	//Google 콜백 처리
	@GetMapping("/google/callback")
	public ResponseEntity<String> googleCallback(@RequestParam String code){
		String jwt = googleOAuthService.processGoogleLogin(code);
		return ResponseEntity.ok(jwt);
	}
	
	
	
}
