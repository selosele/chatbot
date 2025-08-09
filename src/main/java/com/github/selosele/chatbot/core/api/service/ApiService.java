package com.github.selosele.chatbot.core.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.selosele.chatbot.core.constant.DataType;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.ApiRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

	private final XmlMapper xmlMapper;
	private final ObjectMapper objectMapper;

	/**
	 * HTTP 요청을 처리하는 메소드
	 * @param endpoint
	 * @param data
	 * @param method
	 * @param returnType
	 * @return 응답 문자열
	 */
	public String request(String endpoint, ApiRequestDTO data, String method, String returnType) {
		if (method.equalsIgnoreCase(HttpMethod.GET.name())) {
			return get(endpoint, data, returnType);
		}

		String message = Message.UNSUPPORTED_HTTP_METHOD.getMessage() + method;
		log.error(message);
		throw new UnsupportedOperationException(message);
	}

	/**
	 * GET 요청을 처리하는 메소드
	 * @param endpoint
	 * @param data
	 * @param returnType
	 * @return 응답 문자열
	 */
	private String get(String endpoint, ApiRequestDTO params, String returnType) {
		HttpURLConnection conn = null;
		BufferedReader rd = null;

		try {
			StringBuilder urlBuilder = new StringBuilder(endpoint);
			Map<String, Object> paramsToMap = objectMapper.convertValue(params, new TypeReference<Map<String, Object>>() {});
			if (!paramsToMap.isEmpty()) {
				urlBuilder.append("?");
				boolean first = true;
				for (Map.Entry<String, Object> entry : paramsToMap.entrySet()) {
					if (!first) urlBuilder.append("&");
					urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
						.append("=")
						.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
					first = false;
				}
			}

			URL url = new URL(urlBuilder.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(HttpMethod.GET.name());
			conn.setRequestProperty("Content-type", MediaType.APPLICATION_JSON_VALUE);

			int responseCode = conn.getResponseCode();
			rd = new BufferedReader(new InputStreamReader(
				responseCode >= 200 && responseCode <= 299 ? conn.getInputStream() : conn.getErrorStream()));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			if (returnType.equalsIgnoreCase(DataType.XML.getName())) {
				Map<String, Object> map = xmlMapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>() {});
				return objectMapper.writeValueAsString(map);
			} else if (returnType.equalsIgnoreCase(DataType.JSON.getName())) {

			}

			return sb.toString();
		}
		catch (IOException ex) {
			String message = HttpMethod.GET.name() + Message.REQUEST_ERROR.getMessage();
			log.error(message, ex);
			throw new RuntimeException(message, ex);
		}
		finally {
			try { if (rd != null) rd.close(); } catch (IOException ignored) {}
			if (conn != null) conn.disconnect();
		}
	}

}
