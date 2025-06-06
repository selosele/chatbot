package com.github.selosele.chatbot.bot.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 봇의 응답 DTO
 */
@Getter
@Builder
@ToString
public class BotResponseDTO<T> {

	private List<T> data;
	private String input;
	private String message;
	
}
