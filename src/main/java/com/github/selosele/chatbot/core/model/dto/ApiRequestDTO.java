package com.github.selosele.chatbot.core.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * API 요청 DTO
 */
@Getter
@Setter
@ToString
public class ApiRequestDTO {

	/** 서비스키 */
	private String serviceKey;

	/** 한 페이지 결과 수 */
	private String numOfRows;

}
