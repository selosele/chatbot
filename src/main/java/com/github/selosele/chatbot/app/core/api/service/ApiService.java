package com.github.selosele.chatbot.app.core.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.selosele.chatbot.app.core.constant.Message;

import lombok.RequiredArgsConstructor;

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
	public String request(String endpoint, Map<String, Object> data, String method, String returnType) {
		if (method.equalsIgnoreCase(HttpMethod.GET.name())) {
			return requestForGet(endpoint, data, returnType);
		}
		throw new UnsupportedOperationException(Message.UNSUPPORTED_HTTP_METHOD.getMessage() + method);
	}

	/**
	 * GET 요청을 처리하는 메소드
	 * @param endpoint
	 * @param data
	 * @param returnType
	 * @return 응답 문자열
	 */
	private String requestForGet(String endpoint, Map<String, Object> data, String returnType) {
		HttpURLConnection conn = null;
		BufferedReader rd = null;

		try {
			StringBuilder urlBuilder = new StringBuilder(endpoint);
			if (!data.isEmpty()) {
				urlBuilder.append("?");
				boolean first = true;
				for (Map.Entry<String, Object> entry : data.entrySet()) {
					if (!first) urlBuilder.append("&");
					urlBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
						.append("=")
						.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
					first = false;
				}
			}

			URL url = new URL(urlBuilder.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(HttpMethod.GET.name());
			conn.setRequestProperty("Content-type", "application/json");

			int responseCode = conn.getResponseCode();
			System.out.println("Response code: " + responseCode);

			rd = new BufferedReader(new InputStreamReader(
				responseCode >= 200 && responseCode <= 299 ? conn.getInputStream() : conn.getErrorStream()));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			if (returnType.equalsIgnoreCase("xml")) {
				Map<String, Object> map = xmlMapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>() {});
				return objectMapper.writeValueAsString(map);
			} else if (returnType.equalsIgnoreCase("json")) {

			}

			return sb.toString();
		}
		catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(HttpMethod.GET.name() + Message.REQUEST_ERROR.getMessage(), ex);
		}
		finally {
			try { if (rd != null) rd.close(); } catch (IOException ignored) {}
			if (conn != null) conn.disconnect();
		}
	}

}
