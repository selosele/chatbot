package com.github.selosele.chatbot.app.core.constant;

import org.springframework.http.HttpMethod;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
	BOT_RESPONSE_ERROR("봇이 응답할 수 없습니다. 다시 시도해 주세요."),
	UNSUPPORTED_HTTP_METHOD("지원하지 않는 HTTP 메소드: "),
	REQUEST_FOR_GET_ERROR(HttpMethod.GET.name() + " 요청 중 오류 발생: ");

	private final String message;
}
