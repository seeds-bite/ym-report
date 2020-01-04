package by.rgs.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import by.rgs.demo.model.Message;
import by.rgs.demo.model.Metrics;
import by.rgs.demo.model.MetricsConfiguration;
import by.rgs.demo.service.UpdateReportService;

@Service
public class UpdateReportServiceImpl implements UpdateReportService {
	
	@Autowired
	private HttpServletRequest request;
	
	@Value("${url.get.counters.data}")
	private String urlForCountersData;	
	public static final String uploadingDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\reports\\";
	
	@Override
	public Message updateReport(MetricsConfiguration metricsConf) {
		Metrics dataMetrics = getMetricsFromYM(metricsConf);
		if(dataMetrics != null) {
		try {
			writeMetricsToXLSX(dataMetrics);
		} catch (IOException e) {
			return new Message(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла ошибка при записи в файл!");
		}
		return new Message(HttpStatus.OK, "Вы успешно записали данные в файл");
		}else {
			return new Message(HttpStatus.SERVICE_UNAVAILABLE, "Не удалось получить данные метрики");
		}
	}
	
	private Metrics getMetricsFromYM(MetricsConfiguration metricsConf) {
		HttpSession session = request.getSession(false);
		String accessToken = (String) session.getAttribute("ASSESS_TOKEN");
		String metricsName = String.join(",", metricsConf.getMetrics());
		String dateStart = metricsConf.getDateStart();
		String dateEnd = metricsConf.getDateEnd();		
		String ids = metricsConf.getIdCount();		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(urlForCountersData			//TODO: Create request Builder
				+ "?metrics=" + metricsName
				+ "&date1=" + dateStart
				+ "&date2=" + dateEnd
				+ "&limit=10000&offset=1"
				+ "&ids=" + ids
				+ "&oauth_token=" + accessToken,
				Metrics.class);
		}
	
	private void writeMetricsToXLSX(Metrics dataMetrics) throws IOException {
		File directory = new File(uploadingDirectory);			//TODO: Move path to properties file 
		File[] files = directory.listFiles();
		for (File fileForStream : files) {					//TODO: Add threads and move to FileService
			FileInputStream inputStream = new FileInputStream(fileForStream);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int i = 1;
			for (String metric : dataMetrics.getMetrics()) {
				XSSFCell cell = sheet.getRow(8).getCell(i++);
				cell.setCellValue(metric);
			}
			inputStream.close();
			FileOutputStream out = new FileOutputStream(fileForStream);
			workbook.write(out);
			workbook.close();
			out.close();
		}	
	}

}
