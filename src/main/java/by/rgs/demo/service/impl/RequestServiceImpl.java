package by.rgs.demo.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import by.rgs.demo.model.Counter;
import by.rgs.demo.model.CountersParam;
import by.rgs.demo.model.UserProfile;
import by.rgs.demo.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {
	
	@Autowired
	private HttpServletRequest request;
	
	@Value("${url.get.token}")
	private String urlForToken;
	@Value("${url.get.profile}")
	private String urlForProfile;
	@Value("${url.get.counters}")
	private String urlForGetCounters;
	@Value("${credentials}")
	private String credentials;

	@Override
	public UserProfile executeRequestForToken(String code) {
		HttpSession session = request.getSession();
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
		session.setAttribute("ASSESS_TOKEN", accessToken);
		System.out.println("session accessTtoken " + session.getAttribute("ASSESS_TOKEN"));
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

	@Override
	public List<Counter> getUserCounters() {
		HttpSession session = request.getSession(false);
		System.out.println("session accessTtoken getUserCounters" + session.getAttribute("ASSESS_TOKEN"));
		String oauthToken = "OAuth " + session.getAttribute("ASSESS_TOKEN");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", oauthToken);
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		ResponseEntity<CountersParam> counters = restTemplate.exchange(urlForGetCounters, HttpMethod.GET, entity, CountersParam.class);
		System.out.println("counters: " + counters.getBody().getCounters().toString());
		return counters.getBody().getCounters();
	}
}
