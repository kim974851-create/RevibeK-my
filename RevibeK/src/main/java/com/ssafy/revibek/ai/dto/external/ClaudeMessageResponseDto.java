package com.ssafy.revibek.ai.dto.external;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClaudeMessageResponseDto {

    private List<ContentBlock> content;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentBlock {
        private String type;
        private String text;
    }
}
