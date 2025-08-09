package com.github.selosele.chatbot.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 봇의 요청 DTO
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BotRequestDTO {

	/**
	 * 카카오톡 봇의 요청 DTO
	 */
	private KakaoBotRequestDTO kakaoBotRequest;

	public static BotRequestDTO of(KakaoBotRequestDTO kakaoBotRequest) {
		return new BotRequestDTO(kakaoBotRequest);
	}
	
}
