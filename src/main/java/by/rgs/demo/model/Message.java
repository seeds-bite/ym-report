package by.rgs.demo.model;

import org.springframework.http.HttpStatus;

public class Message {

	private HttpStatus status;
	private String message;
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Message(HttpStatus internalServerError, String message) {
		this.status = internalServerError;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Message [status=" + status.toString() + ", message=" + message + "]";
	}
	
	
	
}
