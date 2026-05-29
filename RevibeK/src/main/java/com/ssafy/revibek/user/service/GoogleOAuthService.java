package com.ssafy.revibek.user.service;

import com.ssafy.revibek.user.dto.GoogleTokenResponse;
import com.ssafy.revibek.user.dto.GoogleUserInfo;
import com.ssafy.revibek.user.dto.UserResponseDto;
import com.ssafy.revibek.user.mapper.UserMapper;
import com.ssafy.revibek.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    @Value("${google.oauth.client-id}")
    private String clientId;

    @Value("${google.oauth.client-secret}")
    private String clientSecret;

    @Value("${google.oauth.redirect-uri}")
    private String redirectUri;

    private  UserMapper userMapper;
    private  JwtUtil jwtUtil;
    private  RestTemplate restTemplate = new RestTemplate();

    // 4단계: 로그인 URL 생성
    public String getGoogleLoginUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=email%20profile";
    }

    // 6단계: code → access token
    public String getAccessToken(String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        GoogleTokenResponse response = restTemplate.postForObject(
                tokenUrl, request, GoogleTokenResponse.class
        );
        return response.getAccessToken();
    }

    // 7단계: access token → 사용자 정보
    public GoogleUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserInfo> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET, request, GoogleUserInfo.class
        );
        return response.getBody();
    }

    // 8~10단계: 조회 → 가입/로그인 → JWT 발급
    public String processGoogleLogin(String code) {
        String accessToken = getAccessToken(code);
        GoogleUserInfo userInfo = getUserInfo(accessToken);

        // 8단계: DB 조회
        UserResponseDto user = userMapper.findByEmail(userInfo.getEmail());

        // 9단계: 없으면 회원가입
        if (user == null) {
            user = UserResponseDto.builder()
                    .nickname(userInfo.getName())
                    .email(userInfo.getEmail())
                    .provider("google")
                    .providerId(userInfo.getId())
                    .build();
            userMapper.insertSocialUser(user);
        }

        // 10단계: JWT 발급
        return jwtUtil.generateToken(user.getEmail());
    }
}
