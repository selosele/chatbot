package com.github.selosele.chatbot.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 요일을 나타내는 열거형 Enum 클래스
 */
@Getter
@RequiredArgsConstructor
public enum CustomDayOfWeek {
  MONDAY("월"),
  TUESDAY("화"),
  WEDNESDAY("수"),
  THURSDAY("목"),
  FRIDAY("금"),
  SATURDAY("토"),
  SUNDAY("일");

  private final String dayName;
}
