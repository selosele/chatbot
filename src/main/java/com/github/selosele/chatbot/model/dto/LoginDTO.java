package com.github.selosele.chatbot.model.dto;

import com.github.selosele.chatbot.core.model.dto.HttpRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 DTO
 */
public class LoginDTO {

    /**
     * 로그인 요청 DTO
     */
    @Getter
    @Setter
    @Builder
    @ToString
    public static class Request extends HttpRequestDTO {

        /** 사용자 계정 */
        private String userAccount;

        /** 비밀번호 */
        private String userPassword;

        public static Request of(String userAccount, String userPassword) {
            return Request.builder()
                    .userAccount(userAccount)
                    .userPassword(userPassword)
                    .build();
        }
    }

    /**
     * 로그인 응답 DTO
     */
    @Getter
    @Builder
    @ToString
    public static class Response {

        /** 액세스 토큰 */
        private String accessToken;

        public static Response of(String accessToken) {
            return Response.builder()
                    .accessToken(accessToken)
                    .build();
        }
    }

}
