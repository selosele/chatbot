package com.github.selosele.chatbot.core.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

import com.github.selosele.chatbot.core.constant.CustomDayOfWeek;

/**
 * 날짜 관련 유틸리티 클래스
 */
public class DateUtil {

  /**
   * 날짜를 지정된 형식으로 문자열로 변환하는 메소드
   * @param format 날짜 형식
   * @return 날짜를 지정된 형식으로 변환한 문자열
   */
  public static String getDateString(String format, Date date) {
    return new SimpleDateFormat(format).format(date);
  }

  /**
   * 문자열이 유효한 날짜 형식인지 확인하는 메소드
   * @param dateStr 확인할 날짜 문자열
   * @param format 날짜 형식
   * @return 유효한 날짜 형식이면 true, 그렇지 않으면 false
   */
  public static boolean isValidDate(String dateStr, String format) {
    if (format.equals("yyyy") && dateStr.length() != 4) return false;
    if (format.equals("MM") && dateStr.length() != 2) return false;
    if (format.equals("dd") && dateStr.length() != 2) return false;
    
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      sdf.setLenient(false);
      return sdf.format(sdf.parse(dateStr)).equals(dateStr);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 요일을 한글로 변환하는 메소드
   * @param value 요일 값
   * @return 한글 요일 이름
   */
  public static String getDayOfWeekToKor(DayOfWeek value) {
    return switch (value) {
      case MONDAY 	 -> CustomDayOfWeek.MONDAY.getDayName();
      case TUESDAY 	 -> CustomDayOfWeek.TUESDAY.getDayName();
      case WEDNESDAY -> CustomDayOfWeek.WEDNESDAY.getDayName();
      case THURSDAY  -> CustomDayOfWeek.THURSDAY.getDayName();
      case FRIDAY 	 -> CustomDayOfWeek.FRIDAY.getDayName();
      case SATURDAY  -> CustomDayOfWeek.SATURDAY.getDayName();
      case SUNDAY    -> CustomDayOfWeek.SUNDAY.getDayName();
    };
  }

}
