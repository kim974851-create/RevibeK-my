package com.ssafy.revibek.user.service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private static final long CODE_TTL_SECONDS = 5 * 60;      // 5분
    private static final long VERIFIED_TTL_SECONDS = 30 * 60; // 30분

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String senderEmail;

    private final Map<String, VerificationCodeEntry> codeStore = new ConcurrentHashMap<>();
    private final Map<String, Instant> verifiedEmailStore = new ConcurrentHashMap<>();

    public void sendVerificationCode(String email) {
        cleanupExpiredEntries();
        String code = generateCode();
        Instant expiresAt = Instant.now().plusSeconds(CODE_TTL_SECONDS);
        codeStore.put(email, new VerificationCodeEntry(code, expiresAt));

        SimpleMailMessage message = new SimpleMailMessage();
        if (!senderEmail.isBlank()) {
            message.setFrom(senderEmail);
        }
        message.setTo(email);
        message.setSubject("[RevibeK] 이메일 인증코드");
        message.setText(
            "아래 인증코드를 입력해주세요.\n\n" +
            "인증코드: " + code + "\n" +
            "만료시간: 5분"
        );
        mailSender.send(message);
    }

    public void verifyCode(String email, String code) {
        cleanupExpiredEntries();
        VerificationCodeEntry entry = codeStore.get(email);
        if (entry == null) {
            throw new RuntimeException("인증코드가 없거나 만료되었습니다.");
        }
        if (!entry.code().equals(code)) {
            throw new RuntimeException("인증코드가 올바르지 않습니다.");
        }
        codeStore.remove(email);
        verifiedEmailStore.put(email, Instant.now().plusSeconds(VERIFIED_TTL_SECONDS));
    }

    public boolean isVerified(String email) {
        cleanupExpiredEntries();
        Instant verifiedUntil = verifiedEmailStore.get(email);
        return verifiedUntil != null && verifiedUntil.isAfter(Instant.now());
    }

    public void consumeVerification(String email) {
        verifiedEmailStore.remove(email);
    }

    private void cleanupExpiredEntries() {
        Instant now = Instant.now();
        codeStore.entrySet().removeIf(entry -> entry.getValue().expiresAt().isBefore(now));
        verifiedEmailStore.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
    }

    private String generateCode() {
        int value = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return Integer.toString(value);
    }

    private record VerificationCodeEntry(String code, Instant expiresAt) {
    }
}
