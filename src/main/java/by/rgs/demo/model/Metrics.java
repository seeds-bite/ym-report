package by.rgs.demo.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metrics {
  
  @JsonProperty("totals")
  private String[]  metrics; 
  private Query query;
  
  public Query getQuery() {
    return query;
  }

  public void setQuery(Query query) {
    this.query = query;
  }

  public String[] getMetrics() {
    return metrics;
  }

  public void setMetrics(String[] metrics) {
    this.metrics = metrics;
  }

  public Metrics() {

  }

  @Override
  public String toString() {
    return "Metrics [metrics=" + Arrays.toString(metrics) + ", query=" + query + "]";
  }

}