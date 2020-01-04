package by.rgs.demo.model;

public class Counter {

	private String id;
	private String status;
	private String name;
	private String site;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	
	public Counter() {
	}
	public Counter(String id, String status, String name, String site) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.site = site;
	}
	@Override
	public String toString() {
		return "Counter [id=" + id + ", status=" + status + ", name=" + name + ", site=" + site + "]";
	}
	
	
	
	
	
}
