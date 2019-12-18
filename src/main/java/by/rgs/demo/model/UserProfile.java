package by.rgs.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfile {
	
	private String login;
	@JsonProperty("default_email")
	private String email;
	@JsonProperty("default_avatar_id")
	private String avatarId;
	@JsonProperty("is_avatar_empty")
	private boolean isAvatarEmpty;
	
	public UserProfile() {
	}

	public UserProfile(String login, String email, String avatarId, boolean isAvatarEmpty) {
		this.login = login;
		this.email = email;
		this.avatarId = avatarId;
		this.isAvatarEmpty = isAvatarEmpty;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatarId() {
		return avatarId;
	}
	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}
	public boolean isAvatarEmpty() {
		return isAvatarEmpty;
	}
	public void setAvatarEmpty(boolean isAvatarEmpty) {
		this.isAvatarEmpty = isAvatarEmpty;
	}
	
	@Override
	public String toString() {
		return "UserProfile [login=" + login + ", email=" + email + ", avatarId=" + avatarId + ", isAvatarEmpty="
				+ isAvatarEmpty + "]";
	}
	

}
