package by.rgs.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import by.rgs.demo.model.Message;
import by.rgs.demo.model.Metrics;
import by.rgs.demo.model.MetricsConfiguration;
import by.rgs.demo.service.UpdateReportService;

@Service
public class UpdateReportServiceImpl implements UpdateReportService {
	
	@Override
	public Message updateReport(MetricsConfiguration metricsConf) {
		Metrics dataMetrics = getMetricsFromYM(metricsConf);
		if(dataMetrics != null) {
		try {
			writeMetricsToXLSX(dataMetrics);
		} catch (IOException e) {
			return new Message(500, "Произошла ошибка при записи в файл!");
		}
		return new Message(200, "Вы успешно записали данные в файл");
		}else {
			return new Message(503, "Не удалось получить данные метрики");
		}
	}
	
	private Metrics getMetricsFromYM(MetricsConfiguration metricsConf) {
		String metricsName = String.join(",", metricsConf.getMetrics());
		String dateStart = metricsConf.getDateStart();
		String dateEnd = metricsConf.getDateEnd();		
		String ids = metricsConf.getIdCount();		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("https://api-metrika.yandex.ru/stat/v1/data"
				+ "?metrics=" + metricsName
				+ "&date1=" + dateStart
				+ "&date2=" + dateEnd
				+ "&limit=10000&offset=1"
				+ "&ids=" + ids
				+ "&oauth_token=AgAAAAAVzRGcAAYDpUewH7NWikZDgZUHlRNX1Aw",
				Metrics.class);
		}
	
	private void writeMetricsToXLSX(Metrics dataMetrics) throws IOException {
		File file = new File("D:/Book1.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int i = 1;
		for (String metric : dataMetrics.getMetrics()) {
			XSSFCell cell = sheet.getRow(8).getCell(i++);
			cell.setCellValue(metric);
		}
		inputStream.close();
		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		workbook.close();
		out.close();
	}

}
