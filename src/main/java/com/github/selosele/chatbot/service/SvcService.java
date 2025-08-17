package com.github.selosele.chatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.selosele.chatbot.core.constant.DataType;
import com.github.selosele.chatbot.core.http.service.HttpService;
import com.github.selosele.chatbot.core.util.CommonUtil;
import com.github.selosele.chatbot.model.dto.LoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SvcService {

    @Value("${api.svc.login.endpoint}")
    private String loginEndpoint;

    private final HttpService http;
    private final ObjectMapper objectMapper;

    /**
     * 로그인 메소드
     * 
     * @param userAccount  사용자 계정
     * @param userPassword 비밀번호
     * @return 액세스 토큰
     */
    public String login(LoginDTO.Request dto) {
        String response = http.request(loginEndpoint, dto, HttpMethod.POST.name(), DataType.JSON.getName());
        if (response == null || response.isEmpty()) {
            log.error("로그인 요청 실패");
            return null;
        }

        // JSON 응답에서 액세스 토큰 추출
        try {
            LoginDTO.Response loginResponse = objectMapper.readValue(response, LoginDTO.Response.class);
            if (CommonUtil.isBlank(loginResponse.getAccessToken())) {
                log.error("로그인 실패: 액세스 토큰 없음");
                return null;
            }
            return loginResponse.getAccessToken();
        } catch (Exception ex) {
            log.error("로그인 응답 처리 중 오류 발생: {}", ex.getMessage());
            return null;
        }
    }

}
