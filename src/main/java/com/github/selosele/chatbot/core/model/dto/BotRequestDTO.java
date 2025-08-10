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

	private String input;
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
