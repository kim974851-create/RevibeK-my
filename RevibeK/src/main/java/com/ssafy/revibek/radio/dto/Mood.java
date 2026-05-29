package com.ssafy.revibek.radio.dto;


/**
 * 라디오 감정 상태 Enum
 * DB에는 ENUM 값으로 저장됨 (LONELY, EXCITED, NOSTALGIC, TIRED, HAPPY, SAD)
 */
public enum Mood {
	LONELY("외로운"),
	EXCITED("설레는"),
	NOSTALGIC("그리운"),
	TIRED("지친"),
	HAPPY("행복한"),
	SAD("슬픈");
	
	private final String displayName;
	
	Mood(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
