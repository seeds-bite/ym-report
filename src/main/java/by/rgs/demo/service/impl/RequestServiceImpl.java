package by.rgs.demo.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import by.rgs.demo.model.UserProfile;
import by.rgs.demo.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {
	private final String urlForToken = "https://oauth.yandex.ru/token";
	private final String urlForProfile = "https://login.yandex.ru/info?format=json";
	private final String credentials = "id:pass";

	@Override
	public UserProfile executeRequestForToken(String code) {
		String authBasic = getEncodedBasicAuth();
		RestTemplate restTemplate = new RestTemplate();
		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authBasic);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(body, headers);
		ResponseEntity<JsonNode> responce = restTemplate.postForEntity(urlForToken, httpEntity, JsonNode.class);		//TODO: Create request factory
		System.out.println("responce " + responce);
		String accessToken = responce.getBody().get("access_token").textValue();
		System.out.println("accessTtoken " + accessToken);
		return executeRequestForUserProfile(accessToken);
	}
	
	private String getEncodedBasicAuth() {
		String asB64 = null;
		try {
			asB64 = Base64.getEncoder().encodeToString(credentials.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		String authBasic = "Basic " + asB64;
		return authBasic;
	}

	private UserProfile executeRequestForUserProfile(String accessToken) {
		System.out.println("token " + accessToken);
		String oauthToken = "OAuth " + accessToken;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", oauthToken);
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		ResponseEntity<UserProfile> profile = restTemplate.exchange(urlForProfile, HttpMethod.GET, entity, UserProfile.class);
		System.out.println("profile " + profile.toString());
		return profile.getBody();
	}
}
