package com.github.selosele.chatbot.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 봇의 응답 메시지를 정의하는 Enum 클래스
 */
@Getter
@RequiredArgsConstructor
public enum Message {
	BOT_RESPONSE_ERROR("봇이 응답할 수 없습니다. 다시 시도해 주세요."),
	REQUEST_ERROR(" 요청 중 오류 발생: "),
	UNSUPPORTED_HTTP_METHOD("지원하지 않는 HTTP 메소드: "),
	UNSUPPORTED_COMMAND("지원하지 않는 명령어입니다. '공휴일', '버스', '지하철' 중 하나를 입력해주세요."),
	IS_INPUT_BLANK("입력 값이 비어있습니다. '공휴일', '버스', '지하철' 중 하나를 입력해주세요."),
	FOUND_DATA_COUNT("건의 결과를 찾았습니다."),
	FOUND_NO_HOLIDAY_DATA("공휴일 정보가 없습니다.");

	private final String message;
}
