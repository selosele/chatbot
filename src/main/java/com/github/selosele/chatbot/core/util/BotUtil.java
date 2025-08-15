package com.github.selosele.chatbot.core.util;

import com.github.selosele.chatbot.core.constant.Separator;
import com.github.selosele.chatbot.core.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.core.model.dto.KakaoBotRequestDTO;

/**
 * 봇 관련 유틸리티
 */
public class BotUtil {

	/**
	 * 사용자 입력을 구분자로 분리하여 배열로 반환하는 메소드
	 * 
	 * @param input 사용자 입력
	 * @return 분리된 문자열 배열
	 */
	public static String[] getParts(String input) {
		if (CommonUtil.isBlank(input)) {
			return new String[0];
		}
		return input.split(Separator.SLASH.getName());
	}

	/**
	 * 입력 객체에서 사용자 입력을 추출하는 메소드
	 * 
	 * @param arg 입력 객체
	 * @return 사용자 입력 문자열
	 */
	public static String extractInput(Object arg) {
		if (arg instanceof KakaoBotRequestDTO) { // 카카오톡 봇 요청 DTO
			KakaoBotRequestDTO dto = (KakaoBotRequestDTO) arg;
			if (dto != null && dto.getUserRequest() != null) {
				return dto.getUserRequest().getUtterance();
			}
		}
		return null;
	}

	/**
	 * 봇 요청 DTO에서 사용자 입력을 추출하는 메소드
	 * 
	 * @param dto 봇 요청 DTO
	 * @return 사용자 입력 문자열
	 */
	public static String extractInput(BotRequestDTO dto) {
		if (dto != null && dto.getKakaoBotRequest() != null) { // 카카오톡 봇 요청 DTO
			return dto.getKakaoBotRequest().getUserRequest().getUtterance();
		}
		return null;
	}

}
