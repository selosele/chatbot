package com.github.selosele.chatbot.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 봇의 요청 DTO
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BotRequestDTO {

	/**
	 * 사용자 입력
	 * 예) "공휴일/2025/01"
	 */
	private String input;

	/**
	 * 카테고리
	 * 예) 사용자 입력 "공휴일/2025/01"에서 "공휴일"이 추출됨
	 */
	private String category;

	/**
	 * 카카오톡 봇의 요청 DTO
	 */
	private KakaoBotRequestDTO kakaoBotRequest;

	public static BotRequestDTO of(KakaoBotRequestDTO kakaoBotRequest) {
		return BotRequestDTO.builder()
				.input(kakaoBotRequest.getUserRequest().getUtterance())
				.kakaoBotRequest(kakaoBotRequest)
				.build();
	}

}
