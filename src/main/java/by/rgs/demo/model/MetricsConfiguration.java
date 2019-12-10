package by.rgs.demo.model;

import java.util.Arrays;

public class MetricsConfiguration {
	
	private String[] metrics;
	private String idCount;
	private String dateStart;
	private String dateEnd;
	
	public String[] getMetrics() {
		return metrics;
	}
	public void setMetrics(String[] metrics) {
		this.metrics = metrics;
	}
	public String getIdCount() {
		return idCount;
	}
	public void setIdCount(String idCount) {
		this.idCount = idCount;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public MetricsConfiguration(String[] metrics, String idCount, String dateStart, String dateEnd) {
		this.metrics = metrics;
		this.idCount = idCount;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	
	@Override
	public String toString() {
		return "MetricsConfiguration [metrics=" + Arrays.toString(metrics) + ", idCount=" + idCount + ", dateStart="
				+ dateStart + ", dateEnd=" + dateEnd + "]";
	}
	

}
