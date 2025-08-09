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
  
  private String serviceKey;
  private String numOfRows;
  
}
