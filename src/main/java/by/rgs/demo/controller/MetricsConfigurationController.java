package by.rgs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import by.rgs.demo.model.Message;
import by.rgs.demo.model.MetricsConfiguration;
import by.rgs.demo.service.FileService;
import by.rgs.demo.service.UpdateReportService;

@RestController
public class MetricsConfigurationController {

	@Autowired
	UpdateReportService updateReportService;
	@Autowired
	FileService fileService;

	@PostMapping(path = "/update-xslx", /* consumes = MediaType.APPLICATION_JSON_VALUE, */ produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateXSLXReport(@RequestBody MetricsConfiguration metricsConf) {
		Message message = updateReportService.updateReport(metricsConf);
		System.out.println("metricsConf " + metricsConf.getIdCount());
		return new ResponseEntity<Object>(message, null, message.getStatus());
	}
	
	@PostMapping(path = "/uploadFiles")
	public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		Message message = fileService.uploadFiles(files);
		return new ResponseEntity<Object>(message, null, message.getStatus());
	}
	
	@PostMapping(path = "/code")
	public ResponseEntity<?> uploadFiles(@RequestParam("code") String code) {
		return new ResponseEntity<Object>(code, null, HttpStatus.OK);
	}
	

}
