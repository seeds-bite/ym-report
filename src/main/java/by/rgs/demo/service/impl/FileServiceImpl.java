package by.rgs.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import by.rgs.demo.controller.MetricsConfigurationController;
import by.rgs.demo.model.Message;
import by.rgs.demo.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Autowired
	private HttpServletRequest request;
	//private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
	private static final String uploadingDirectory = System.getProperty("user.dir")
			+ "/src/main/resources/reports/";
	private static final String LIST_FILE_NAMES = "LIST_FILE_NAMES";

	@SuppressWarnings("unchecked")
	@Override
	public Message uploadFiles(MultipartFile[] files) {
		log.debug("File directory: " + System.getProperty("user.dir"));
		log.debug("uploadingDirectory: " + uploadingDirectory);
		if (files.length != 0) {
			File directory = new File(uploadingDirectory);
			boolean isExistDirectory = checkExistDirectory(directory);
			if (!isExistDirectory) {
				try {
					directory.mkdir();
				} catch (Exception e) {
					return new Message(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось создать директорию на сервере");
				}
			}
			HttpSession session = request.getSession(false);
			List<String> listFileNames = new ArrayList<String>();
			session.setAttribute(LIST_FILE_NAMES, listFileNames);
			File file;
			for (MultipartFile multipartFile : files) {
				file = new File(uploadingDirectory + multipartFile.getOriginalFilename());
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					return new Message(HttpStatus.INTERNAL_SERVER_ERROR,
							"Метод был вызван в недопустимое или несоответствующее время.");
				} catch (IOException e) {
					return new Message(HttpStatus.INTERNAL_SERVER_ERROR,
							"Произошла ошибка при сохранении файла в директорию!");
				}
				listFileNames = (List<String>) session.getAttribute(LIST_FILE_NAMES);
				listFileNames.add(multipartFile.getOriginalFilename());
				session.setAttribute(LIST_FILE_NAMES, listFileNames);
			}
			return new Message(HttpStatus.OK, "Файлы успешно загружены!");
		} else {
			return new Message(HttpStatus.BAD_REQUEST, "Вы не загрузили файлы отчётов!");
		}
	}

	private boolean checkExistDirectory(File directory) {
		return directory.exists();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> downloadFiles() {
		HttpSession session = request.getSession(false);
		List<String> fileNames = (List<String>) session.getAttribute(LIST_FILE_NAMES);
		File file = new File(uploadingDirectory + fileNames.get(0));
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);					// TODO: send response with message to front
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(file.length())
				.body(resource);
	}
}
