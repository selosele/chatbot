package com.github.selosele.chatbot.core.util;

import com.github.selosele.chatbot.core.model.dto.KakaoBotRequestDTO;

/**
 * 봇 관련 유틸리티 클래스
 */
public class BotUtil {

  /**
   * 입력 객체에서 사용자 입력을 추출하는 메소드
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
   * 입력 문자열에서 카테고리를 추출하는 메소드
   * @param input 사용자 입력
   * @return 카테고리 문자열
   */
  public static String extractCategory(String input) {
    if (CommonUtil.isBlank(input)) {
      return null;
    }
    String[] parts = input.split("/");
    return parts.length > 0 ? parts[0].trim() : null;
  }

  /**
	 * 입력 값이 유효한지 검사하는 메소드
	 * @param input 사용자 입력
	 * @return 유효성 검사 결과
	 */
	public static boolean isValidInput(String input) {
		return CommonUtil.isNotBlank(input);
	}

}
