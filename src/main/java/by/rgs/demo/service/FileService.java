package by.rgs.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import by.rgs.demo.model.Message;

public interface FileService {
	
	public Message uploadFiles(MultipartFile[] files);
	public ResponseEntity<?> downloadFiles();

}
