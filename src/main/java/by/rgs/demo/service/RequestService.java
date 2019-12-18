package by.rgs.demo.service;

import by.rgs.demo.model.UserProfile;

public interface RequestService {
	
	public UserProfile executeRequestForToken(String code);



}
