package by.rgs.demo.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import by.rgs.demo.model.Message;
import by.rgs.demo.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	public static final String uploadingDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\reports\\";

	@Override
	public Message uploadFiles(MultipartFile[] files) {
		if (files.length != 0) {
			for (MultipartFile multipartFile : files) {
				File file = new File(uploadingDirectory + multipartFile.getOriginalFilename());
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

}
