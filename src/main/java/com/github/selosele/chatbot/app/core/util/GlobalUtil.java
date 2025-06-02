package com.github.selosele.chatbot.app.core.util;

import jakarta.servlet.http.HttpServletRequest;

public class GlobalUtil {

  /**
   * 클라이언트의 IP 주소를 가져오기 위한 HTTP 헤더 목록
   */
  private static final String[] headerTypes = {
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

    for (String headerType : headerTypes) {
      ip = request.getHeader(headerType);
      if (ip != null) break;
    }

    if (ip == null) ip = request.getRemoteAddr();

    return ip;
  }
}
