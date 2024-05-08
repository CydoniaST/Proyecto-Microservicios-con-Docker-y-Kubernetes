package es.codeurjc.eolopark.model;

import es.codeurjc.eolopark.repository.EoloParkRepository;
import es.codeurjc.eolopark.service.EoloParkService;
import jakarta.persistence.*;
import es.codeurjc.eolopark.model.EoloPark;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean completed;
    private double progress;

    @OneToOne
    private EoloPark eoloPark;



    public Report(){
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }



    public Long getId() {
        return id;
    }


    public EoloPark getEoloPark() {
        return eoloPark;
    }

    public void setEoloPark(EoloPark eoloPark) {
        this.eoloPark = eoloPark;
    }


}