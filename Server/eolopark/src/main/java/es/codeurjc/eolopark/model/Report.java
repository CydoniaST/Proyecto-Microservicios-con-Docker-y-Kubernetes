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
    private String completed;
    private int progress;

    public Report(){
    }

    public Long getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }

    public String getCompleted() {
        return completed;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}