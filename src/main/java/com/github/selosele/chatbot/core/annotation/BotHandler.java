package com.github.selosele.chatbot.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 봇 요청/응답을 처리하기 위한 어노테이션
 * 이 어노테이션은 봇 요청/응답을 처리하는 메소드에 적용된다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BotHandler {

}
