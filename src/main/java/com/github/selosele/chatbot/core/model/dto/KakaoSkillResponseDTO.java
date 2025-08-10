package com.github.selosele.chatbot.core.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 카카오톡 스킬 응답 DTO
 */
@Getter
@Builder
@ToString
public class KakaoSkillResponseDTO {

	private final String version = "2.0";
	private Template template;

	@Getter
	@Builder
	@ToString
	public static class Template {
		private List<Output> outputs;
	}

	@Getter
	@Builder
	@ToString
	public static class Output {
		private SimpleText simpleText;
	}

	@Getter
	@Builder
	@ToString
	public static class SimpleText {
		private String text;
	}

	public static KakaoSkillResponseDTO of(String text) {
		return KakaoSkillResponseDTO.builder()
				.template(Template.builder()
						.outputs(List.of(Output.builder()
								.simpleText(SimpleText.builder()
										.text(text)
										.build())
								.build()))
						.build())
				.build();
	}

}