package by.rgs.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import by.rgs.demo.model.Counter;
import by.rgs.demo.model.Message;
import by.rgs.demo.model.MetricsConfiguration;
import by.rgs.demo.model.UserProfile;
import by.rgs.demo.service.FileService;
import by.rgs.demo.service.RequestService;
import by.rgs.demo.service.UpdateReportService;

@RestController
public class MetricsConfigurationController {
	private static final Logger log = LoggerFactory.getLogger(MetricsConfigurationController.class);

	@Autowired
	UpdateReportService updateReportService;
	@Autowired
	FileService fileService;
	@Autowired
	RequestService requestService;

	@PostMapping(path = "/update-xslx", /* consumes = MediaType.APPLICATION_JSON_VALUE, */ produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateXSLXReport(@RequestBody MetricsConfiguration metricsConf) {
		log.debug("Пришло metricsConf.getIdCount(): " + metricsConf.getIdCount());
		Message message = updateReportService.updateReport(metricsConf);
		System.out.println("metricsConf " + metricsConf.getIdCount());
		return new ResponseEntity<Object>(message, null, message.getStatus());
	}
	
	@PostMapping(path = "/uploadFiles")
	public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		log.debug("Пришло files[0].getName(): " + files[0].getName());
		Message message = fileService.uploadFiles(files);
		return new ResponseEntity<Object>(message, null, message.getStatus());
	}
	
	@PostMapping(path = "/code")
	public ResponseEntity<?> uploadFiles(@RequestParam("code") String code) {
		log.debug("Пришло code: " + code);
		UserProfile profile = requestService.executeRequestForToken(code);
		return new ResponseEntity<Object>(profile, null, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getCounters")
	public ResponseEntity<?> getCounters() {
		log.debug("getCounters запрос");
		System.out.println("get Counters controller");
		List<Counter> counters = requestService.getUserCounters();
		return new ResponseEntity<Object>(counters, null, HttpStatus.OK);
	}
	
	@GetMapping(path = "/download")
	public ResponseEntity<?> downloadReports() {
		log.debug("downloadReports запрос");
		ResponseEntity<?> files = fileService.downloadFiles();
		log.debug("getHeaders files=" + files.getHeaders().toString());
		log.debug("Body=" + files.getBody().toString());
		return files;

	}
	
//	@GetMapping(path = "/userProfile")
//	public ResponseEntity<?> getUserProfile() {
//		UserProfile userProfile = requestService.executeRequestForUserProfile(null);
//		return new ResponseEntity<Object>(userProfile, null, HttpStatus.OK);
//	}
	

}
