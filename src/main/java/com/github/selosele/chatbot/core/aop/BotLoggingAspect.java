package com.github.selosele.chatbot.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.github.selosele.chatbot.core.util.BotUtil;
import com.github.selosele.chatbot.core.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 봇 응답을 로깅하는 AOP 클래스
 */
@Slf4j
@Aspect
@Component
public class BotLoggingAspect {
  
  /**
   * 봇 응답을 로깅하는 메소드
   * @param joinPoint AOP JoinPoint
   * @param response 봇의 응답
   */
  @AfterReturning(
    pointcut = "@annotation(com.github.selosele.chatbot.core.annotation.LogBotResponse)",
    returning = "response"
  )
  public void logBotResponse(JoinPoint joinPoint, Object response) {
    if (!(response instanceof ResponseEntity)) return;

    Object[] args = joinPoint.getArgs();
    HttpServletRequest request = CommonUtil.getRequest();
    String clientIp = CommonUtil.getClientIP(request);
    String input = null;

    for (Object arg : args) {
      input = BotUtil.extractInput(arg);
    }

    log.info("Client IP: {}, Input: {}, Bot Response: {}", clientIp, input, response.toString());
  }

}
