package by.rgs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import by.rgs.demo.model.Message;
import by.rgs.demo.model.MetricsConfiguration;
import by.rgs.demo.service.UpdateReportService;

@Controller
public class MetricsConfigurationController {
	
	@Autowired
	UpdateReportService updateReportService;
	
	@PostMapping(path = "/update-xslx", /*consumes = MediaType.APPLICATION_JSON_VALUE,*/ produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateXSLXReport(@RequestBody MetricsConfiguration metricsConf) {
		Message message = updateReportService.updateReport(metricsConf);
		System.out.println ("metricsConf " + metricsConf.getIdCount());
		return new ResponseEntity<Object>(message, null, HttpStatus.valueOf(message.getStatus()));
	}
	

}
