package es.codeurjc.eolopark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reportData;
    private int progress;

    public Report(){
    }

    public Long getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }

    public String getReportData() {
        return reportData;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }
}