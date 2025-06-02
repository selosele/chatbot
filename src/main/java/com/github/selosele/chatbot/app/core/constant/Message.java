package com.github.selosele.chatbot.app.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
	BOT_RESPONSE_ERROR("봇이 응답할 수 없습니다. 다시 시도해 주세요."),
	UNSUPPORTED_HTTP_METHOD("지원하지 않는 HTTP 메소드: "),
	REQUEST_ERROR(" 요청 중 오류 발생: "),
	FOUND_DATA_COUNT("건의 결과를 찾았습니다.");

	private final String message;
}
