package com.github.selosele.chatbot.app.bot.model.dto;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BotResponseDTO {

	private List<Map<String, String>> data;
	private String message;
}
