package by.rgs.demo.model;

import java.util.List;

public class CountersParam {
	
	private int rows;
	private List<Counter> counters;

	public List<Counter> getCounters() {
		return counters;
	}

	public void setCounters(List<Counter> counters) {
		this.counters = counters;
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public CountersParam() {
	}
	
	public CountersParam(List<Counter> counters, int rows) {
		this.counters = counters;
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "CountersParam [rows=" + rows + ", counters=" + counters + "]";
	}
	

}
