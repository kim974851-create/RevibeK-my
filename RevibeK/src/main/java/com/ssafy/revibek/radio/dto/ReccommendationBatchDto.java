package com.ssafy.revibek.radio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 여러 추천곡을 한번에 INSERT 할 때 사용
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReccommendationBatchDto {
	private String sessionId;
	private String songId;
	private int orderNum;
	private String reason;

}
