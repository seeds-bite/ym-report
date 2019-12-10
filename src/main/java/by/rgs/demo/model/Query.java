package by.rgs.demo.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {
  
  private String[] ids;
  private String[] metrics;
  public String[] getIds() {
    return ids;
  }
  public void setIds(String[] ids) {
    this.ids = ids;
  }
  public String[] getMetrics() {
    return metrics;
  }
  public void setMetrics(String[] metrics) {
    this.metrics = metrics;
  }
  public Query() {
  }
  
  @Override
  public String toString() {
    return "Query [ids=" + Arrays.toString(ids) + ", metrics=" + Arrays.toString(metrics) + "]";
  }  

}