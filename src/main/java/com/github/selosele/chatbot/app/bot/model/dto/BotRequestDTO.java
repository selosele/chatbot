package com.github.selosele.chatbot.app.bot.model.dto;

import java.util.Map;

import lombok.Getter;

/**
 * 봇의 요청 DTO
 */
@Getter
public class BotRequestDTO {

	private Intent intent;
	private UserRequest userRequest;
	private Bot bot;
	private Action action;

	@Getter
	public static class Intent {
		private String id;
		private String name;
	}

	@Getter
	public static class UserRequest {
		private String timezone;
		private Map<String, String> params;
		private Block block;
		private String utterance;
		private String lang;
		private User user;
	}

	@Getter
	public static class Block {
		private String id;
		private String name;
	}

	@Getter
	public static class User {
		private String id;
		private String type;
		private Map<String, Object> properties;
	}

	@Getter
	public static class Bot {
		private String id;
		private String name;
	}

	@Getter
	public static class Action {
		private String name;
		private String clientExtra;
		private Map<String, String> params;
		private String id;
		private Map<String, Input> detailParams;
	}

	@Getter
	public static class Input {
		private String origin;
		private String value;
		private String groupName;
	}
	
}
