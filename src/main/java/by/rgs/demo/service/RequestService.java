package by.rgs.demo.service;

import java.util.List;

import by.rgs.demo.model.Counter;
import by.rgs.demo.model.UserProfile;

public interface RequestService {
	
	public UserProfile executeRequestForToken(String code);

	public List<Counter> getUserCounters();



}
