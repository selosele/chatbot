package com.github.selosele.chatbot.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 봇 요청 DTO에 적용되는 어노테이션
 * 이 어노테이션은 봇 요청 DTO의 필드에 적용되어, 해당 필드가 봇 요청의 일부임을 나타낸다.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BotRequest {

}
