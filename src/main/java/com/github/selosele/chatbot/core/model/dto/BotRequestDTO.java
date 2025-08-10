package com.github.selosele.chatbot.core.model.dto;

import com.github.selosele.chatbot.core.util.BotUtil;
import com.github.selosele.chatbot.core.util.CommonUtil;

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

	public static BotRequestDTO of(Object dto) {
		if (dto instanceof KakaoBotRequestDTO kakaoDto) {
			return BotRequestDTO.builder()
					.input(kakaoDto.getUserRequest().getUtterance())
					.kakaoBotRequest(kakaoDto)
					.build();
		}
		return null;
	}

	/**
	 * 입력 문자열에서 카테고리를 추출하는 메소드
	 * 
	 * @param input 사용자 입력
	 * @return 카테고리 문자열
	 */
	public String extractCategory(String input) {
		if (CommonUtil.isBlank(input)) {
			return null;
		}
		String[] parts = BotUtil.getParts(input);
		return parts.length > 0 ? parts[0].trim() : null;
	}

	/**
	 * 입력 값이 유효한지 검사하는 메소드
	 * 
	 * @param input 사용자 입력
	 * @return 유효성 검사 결과
	 */
	public boolean isValidInput(String input) {
		return CommonUtil.isNotBlank(input);
	}

}
