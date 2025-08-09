package com.github.selosele.chatbot.core.model.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.ToString;

/**
 * 카카오톡 봇의 요청 DTO
 */
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoBotRequestDTO {

	private Intent intent;
	private UserRequest userRequest;
	private Bot bot;
	private Action action;

	@Getter
	public static class Intent {
		private String id;
		private String name;
		private Map<String, Object> extra;
	}

	@Getter
	public static class UserRequest {
		private String timezone;
		private Map<String, Object> params;
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
		private Map<String, Object> clientExtra;
		private Map<String, Object> params;
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
