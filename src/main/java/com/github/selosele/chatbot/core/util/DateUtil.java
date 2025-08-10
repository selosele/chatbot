package com.github.selosele.chatbot.core.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

import com.github.selosele.chatbot.core.constant.CustomDayOfWeek;

/**
 * 날짜 관련 유틸리티
 */
public class DateUtil {

	/**
	 * 날짜를 지정된 형식으로 문자열로 변환하는 메소드
	 * 
	 * @param format 날짜 형식
	 * @return 날짜를 지정된 형식으로 변환한 문자열
	 */
	public static String getDateString(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 날짜를 지정된 형식으로 문자열로 변환하는 메소드
	 * 
	 * @param format 날짜 형식
	 * @return 날짜를 지정된 형식으로 변환한 문자열
	 */
	public static String getDateString(String sourceFormat, String targetFormat, String dateStr) {
		if (dateStr == null || dateStr.isEmpty())
			return "";
		try {
			// 입력 문자열 포맷에 맞게 먼저 파싱
			SimpleDateFormat inputFormat = new SimpleDateFormat(sourceFormat);
			Date date = inputFormat.parse(dateStr);

			// 원하는 출력 포맷으로 변환
			SimpleDateFormat outputFormat = new SimpleDateFormat(targetFormat);
			return outputFormat.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 문자열이 유효한 날짜 형식인지 확인하는 메소드
	 * 
	 * @param dateStr 확인할 날짜 문자열
	 * @param format  날짜 형식
	 * @return 유효한 날짜 형식이면 true, 그렇지 않으면 false
	 */
	public static boolean isValidDate(String dateStr, String format) {
		if (format.equals("yyyy") && dateStr.length() != 4)
			return false;
		if (format.equals("MM") && dateStr.length() != 2)
			return false;
		if (format.equals("dd") && dateStr.length() != 2)
			return false;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			return sdf.format(sdf.parse(dateStr)).equals(dateStr);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 요일을 한글로 변환하는 메소드
	 * 
	 * @param value 요일 값
	 * @return 한글 요일 이름
	 */
	public static String dayOfWeekToKor(DayOfWeek value) {
		return switch (value) {
			case MONDAY -> CustomDayOfWeek.MONDAY.getDayName();
			case TUESDAY -> CustomDayOfWeek.TUESDAY.getDayName();
			case WEDNESDAY -> CustomDayOfWeek.WEDNESDAY.getDayName();
			case THURSDAY -> CustomDayOfWeek.THURSDAY.getDayName();
			case FRIDAY -> CustomDayOfWeek.FRIDAY.getDayName();
			case SATURDAY -> CustomDayOfWeek.SATURDAY.getDayName();
			case SUNDAY -> CustomDayOfWeek.SUNDAY.getDayName();
		};
	}

	/**
	 * 현재 연도를 문자열로 반환하는 메소드
	 * 
	 * @return 현재 연도
	 */
	public static String getCurrentYear() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}

}
