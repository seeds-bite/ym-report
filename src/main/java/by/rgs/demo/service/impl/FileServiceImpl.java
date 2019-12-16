package by.rgs.demo.service.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import by.rgs.demo.model.Message;
import by.rgs.demo.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
	public static final String uploadingDirectory = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\reports\\";

	@Override
	public Message uploadFiles(MultipartFile[] files) {
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
			}
			return new Message(HttpStatus.OK, "Файлы успешно загружены!");
		} else {
			return new Message(HttpStatus.BAD_REQUEST, "Вы не загрузили файлы отчётов!");
		}
	}

	private boolean checkExistDirectory(File directory) {
		return directory.exists();
	}
}
