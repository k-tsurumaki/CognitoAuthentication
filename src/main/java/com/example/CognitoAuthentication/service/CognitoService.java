package com.example.CognitoAuthentication.service;

import com.example.CognitoAuthentication.dto.IdTokenDTO;
import com.example.CognitoAuthentication.dto.UrlDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Map;

@Service
public class CognitoService {

	@Value("${auth.cognitoUri}")
	private String cognitoUri;

	@Value("${spring.security.oauth2.resourceserver.jwt.clientId}")
	private String clientId;

	@Value("${spring.security.oauth2.resourceserver.jwt.clientSecret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.resourceserver.jwt.redirect-uri}")
	private String redirectUri;

	private final RestTemplate restTemplate;
	private final UserService userService;

	@Autowired
	public CognitoService(RestTemplate restTemplate, UserService userService) {
		this.restTemplate = restTemplate;
		this.userService = userService;
	}

	public IdTokenDTO getIdToken(@Valid String code) {
		String tokenEndpoint = cognitoUri + "/oauth2/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(clientId, clientSecret);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("code", code);
		params.add("redirect_uri", redirectUri);
		params.add("client_id", clientId);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		try {
			ResponseEntity<Map> response = restTemplate.exchange(
					tokenEndpoint,
					HttpMethod.POST,
					requestEntity,
					Map.class
			);

			Map<String, Object> responseBody = response.getBody();
			if (responseBody == null || !responseBody.containsKey("id_token")) {
				throw new IllegalStateException("ID token not found in response");
			}

			String idToken = (String) responseBody.get("id_token");
			String accessToken = (String) responseBody.get("access_token");

//			System.out.println(":::::"+idToken);
//			System.out.println(":::::"+accessToken);

			Map<String, Object> userInfo = getUserInfo(accessToken);
//			System.out.println(":::::"+userInfo);
			String email = (String) userInfo.get("email");
			String name = (String) userInfo.get("name");

			Long userId = userService.createUser(name, email); // userServiceを使用

			IdTokenDTO idTokenDTO = IdTokenDTO.builder()
					.idToken(idToken) // accessTokenでもどっちでもできた
//					.idToken(accessToken)
					.userId(userId)
					.build();
			return idTokenDTO;

		} catch (Exception e) {
			throw new RuntimeException("Error fetching the ID token", e);
		}
	}

	private Map<String, Object> getUserInfo(String accessToken) {
		String userInfoEndpoint = cognitoUri + "/oauth2/userInfo";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken);

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Map> response = restTemplate.exchange(
					userInfoEndpoint,
					HttpMethod.GET,
					requestEntity,
					Map.class
			);

			return response.getBody();

		} catch (Exception e) {
			throw new RuntimeException("Error fetching the user info", e);
		}
	}

	public UrlDTO getLoginUrl() {
		return UrlDTO.builder().url(createLoginUrl()).build();
	}

	private String createLoginUrl() {
		return cognitoUri +
				"/login?" +
				"response_type=code" +
				"&client_id=" + clientId +
				"&scope=openid"+
				"&redirect_uri=" + URLEncoder.encode(redirectUri);
	}
}
