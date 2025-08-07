package com.github.selosele.chatbot.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.github.selosele.chatbot.bot.model.dto.BotRequestDTO;

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
      } else if (arg instanceof BotRequestDTO) {
        BotRequestDTO dto = (BotRequestDTO) arg;
        if (dto != null && dto.getUserRequest() != null) {
          input = dto.getUserRequest().getUtterance();
        }
      }
    }

    log.info("Client IP: {}, Input: {}, Bot Response: {}", clientIp, input, response.toString());
  }
}
