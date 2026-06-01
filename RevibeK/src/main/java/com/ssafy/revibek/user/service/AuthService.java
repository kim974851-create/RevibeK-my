package com.ssafy.revibek.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.revibek.auth.JwtTokenProvider;
import com.ssafy.revibek.auth.dto.AuthTokenResponseDto;
import com.ssafy.revibek.user.dto.UserAuthDto;
import com.ssafy.revibek.user.dto.UserLoginRequestDto;
import com.ssafy.revibek.user.dto.UserRegisterRequestDto;
import com.ssafy.revibek.user.dto.UserResponseDto;
import com.ssafy.revibek.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(UserRegisterRequestDto dto) {
        UserAuthDto existing = userMapper.selectUserAuthByEmail(dto.getEmail());
        if (existing != null) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        userMapper.insertLocalUser(dto.getNickname(), dto.getEmail(), passwordHash);
    }

    public AuthTokenResponseDto login(UserLoginRequestDto dto) {
        UserAuthDto user = userMapper.selectUserAuthByEmail(dto.getEmail());
        if (user == null || user.getPasswordHash() == null) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return issueTokens(user);
    }

    @Transactional
    public AuthTokenResponseDto loginWithGoogle(String email, String providerId, String nickname) {
        if (email == null || providerId == null) {
            throw new RuntimeException("Google 인증 정보가 올바르지 않습니다.");
        }

        UserAuthDto user = userMapper.selectUserAuthByEmail(email);
        if (user == null) {
            userMapper.insertGoogleUser(nickname, email, providerId);
            user = userMapper.selectUserAuthByEmail(email);
        } else if ("local".equalsIgnoreCase(user.getProvider())) {
            throw new RuntimeException("이미 로컬 계정으로 가입된 이메일입니다.");
        }
        if (user == null) {
            throw new RuntimeException("Google 로그인 유저 생성에 실패했습니다.");
        }

        return issueTokens(user);
    }

    public AuthTokenResponseDto refresh(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 refresh token 입니다.");
        }
        String userId = jwtTokenProvider.getUserId(refreshToken);
        UserResponseDto user = userMapper.selectUserById(userId);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }
        UserAuthDto authDto = userMapper.selectUserAuthByEmail(user.getEmail());
        if (authDto == null) {
            throw new RuntimeException("유저 인증 정보를 찾을 수 없습니다.");
        }
        return issueTokens(authDto);
    }

    public void logout(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 refresh token 입니다.");
        }
        // 서버 저장소 기반 revoke를 도입하면 여기서 처리한다.
    }

    private AuthTokenResponseDto issueTokens(UserAuthDto user) {
        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        UserResponseDto responseUser = new UserResponseDto(
            user.getId(),
            user.getNickname(),
            user.getEmail(),
            user.getProvider()
        );

        return new AuthTokenResponseDto(
            accessToken,
            refreshToken,
            "Bearer",
            jwtTokenProvider.getAccessTokenExpirationMs(),
            responseUser
        );
    }
}
