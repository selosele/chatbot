package com.github.selosele.chatbot.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.github.selosele.chatbot.core.model.dto.KakaoBotRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class BotLoggingAspect {
  
  @AfterReturning(
    pointcut = "@annotation(com.github.selosele.chatbot.core.annotation.LogBotResponse)",
    returning = "response"
  )
  public void logBotResponse(JoinPoint joinPoint, Object response) {
    if (!(response instanceof ResponseEntity)) return;

    Object[] args = joinPoint.getArgs();
    String clientIp = null;
    String input = null;

    for (Object arg : args) {
      if (arg instanceof String) {
        clientIp = (String) arg;
      }
      // 카카오톡 봇 요청 DTO
      else if (arg instanceof KakaoBotRequestDTO) {
        KakaoBotRequestDTO dto = (KakaoBotRequestDTO) arg;
        if (dto != null && dto.getUserRequest() != null) {
          input = dto.getUserRequest().getUtterance();
        }
      }
    }

    log.info("Client IP: {}, Input: {}, Bot Response: {}", clientIp, input, response.toString());
  }
}
