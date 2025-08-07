package com.github.selosele.chatbot.core.util;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 공통으로 사용되는 유틸리티 클래스
 */
public class CommonUtil {

  /**
   * 클라이언트의 IP 주소를 가져오기 위한 HTTP 헤더 목록
   */
  private static final String[] HEADER_TYPES = {
    "X-Forwarded-For",
    "Proxy-Client-IP",
    "WL-Proxy-Client-IP",
    "HTTP_CLIENT_IP",
    "HTTP_X_FORWARDED_FOR"
  };

  /**
   * 클라이언트의 IP 주소를 가져오는 메소드
   * @param request HttpServletRequest 객체
   * @return 클라이언트의 IP 주소
   */
  public static String getClientIP(HttpServletRequest request) {
    String ip = null;

    for (String header : HEADER_TYPES) {
      ip = request.getHeader(header);
      if (ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip)) {
        // 여러 IP가 있을 경우 첫 번째 것만 사용
        if (ip.contains(",")) {
          ip = ip.split(",")[0].trim();
        }
        break;
      }
    }

    if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    return ip;
  }

  /**
   * 객체가 비어있는지 확인하는 메소드
   * @param obj 확인할 객체
   * @return 비어있으면 true, 그렇지 않으면 false
   */
  public static boolean isEmpty(Object obj) {
    if (obj == null) return true;
    if (obj instanceof String) return ((String) obj).trim().isEmpty();
    if (obj instanceof Iterable) return !((Iterable<?>) obj).iterator().hasNext();
    if (obj instanceof Object[]) return ((Object[]) obj).length == 0;
    if (obj instanceof List) return ((List<?>) obj).isEmpty();
    return false;
  }

  /**
   * 객체가 비어있지 않은지 확인하는 메소드
   * @param obj 확인할 객체
   * @return 비어있지 않으면 true, 그렇지 않으면 false
   */
  public static boolean isNotEmpty(Object obj) {
    return !isEmpty(obj);
  }

  /**
   * 문자열이 비어있거나 null인지 확인하는 메소드
   * @param str
   * @return 비어있거나 null이면 true, 그렇지 않으면 false
   */
  public static boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  /**
   * 문자열이 비어있지 않은지 확인하는 메소드
   * @param str
   * @return 비어있지 않으면 true, 그렇지 않으면 false
   */
  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

}
