package com.github.selosele.chatbot.app.bot.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

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