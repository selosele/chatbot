package com.github.selosele.chatbot.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 봇의 응답을 로깅하기 위한 어노테이션
 * 이 어노테이션이 붙은 메소드는 봇의 응답을 로그로 기록한다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogBotResponse {

}
