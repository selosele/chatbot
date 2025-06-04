package com.github.selosele.chatbot.app.bot.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * 카카오톡 스킬 응답 DTO
 */
@Getter
@Builder
public class SkillResponseDTO {

  private final String version = "2.0";
	private Template template;

	@Getter
	@Builder
	public static class Template {
		private List<Output> outputs;
	}

	@Getter
	@Builder
	public static class Output {
		private SimpleText simpleText;
	}

	@Getter
	@Builder
	public static class SimpleText {
		private String text;
	}
}