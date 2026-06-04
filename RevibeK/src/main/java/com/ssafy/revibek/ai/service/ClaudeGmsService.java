package com.ssafy.revibek.ai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.ssafy.revibek.ai.dto.external.ClaudeMessageRequestDto;
import com.ssafy.revibek.ai.dto.external.ClaudeMessageResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaudeGmsService {

    private final RestClient.Builder restClientBuilder;

    @Value("${gms.api.base-url}")
    private String baseUrl;

    @Value("${gms.api.key:}")
    private String apiKey;

    @Value("${gms.api.anthropic-version:2023-06-01}")
    private String anthropicVersion;

    @Value("${gms.api.model:claude-3-7-sonnet-latest}")
    private String defaultModel;

    @Value("${gms.api.max-tokens:300}")
    private int defaultMaxTokens;

    public String generateText(String prompt, String system, Integer maxTokens) {
        if (!StringUtils.hasText(apiKey)) {
            throw new RuntimeException("GMS API 키가 비어 있습니다.");
        }

        ClaudeMessageRequestDto request = new ClaudeMessageRequestDto(
            defaultModel,
            maxTokens != null ? maxTokens : defaultMaxTokens,
            List.of(new ClaudeMessageRequestDto.Message("user", prompt)),
            StringUtils.hasText(system) ? system : null
        );

        ClaudeMessageResponseDto response;
        try {
            response = restClientBuilder.build()
                .post()
                .uri(baseUrl)
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .header("anthropic-version", anthropicVersion)
                .body(request)
                .retrieve()
                .body(ClaudeMessageResponseDto.class);
        } catch (RestClientResponseException e) {
            throw new RuntimeException("GMS Claude 호출 실패: " + e.getResponseBodyAsString(), e);
        }

        if (response == null || response.getContent() == null || response.getContent().isEmpty()) {
            throw new RuntimeException("Claude 응답이 비어 있습니다.");
        }

        return response.getContent().stream()
            .filter(content -> "text".equals(content.getType()))
            .map(ClaudeMessageResponseDto.ContentBlock::getText)
            .filter(StringUtils::hasText)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Claude 텍스트 응답을 찾을 수 없습니다."));
    }
}
