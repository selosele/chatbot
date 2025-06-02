package com.github.selosele.chatbot.app.core.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.github.selosele.chatbot.app.core.annotation.ClientIP;
import com.github.selosele.chatbot.app.core.util.GlobalUtil;

import jakarta.servlet.http.HttpServletRequest;

public class ClientIPArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(ClientIP.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    return GlobalUtil.getClientIP(webRequest.getNativeRequest(HttpServletRequest.class));
  }
  
}
