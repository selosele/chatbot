package com.github.selosele.chatbot.core.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 봇의 응답 DTO
 */
@Getter
@Builder
@ToString
public class BotResponseDTO {

	/**
	 * 카카오톡 봇의 응답 DTO
	 */
	private KakaoSkillResponseDTO kakaoSkillResponse;

	public static BotResponseDTO of(String message) {
		return BotResponseDTO.builder()
			.kakaoSkillResponse(KakaoSkillResponseDTO.of(message))
			.build();
	}
	
}
