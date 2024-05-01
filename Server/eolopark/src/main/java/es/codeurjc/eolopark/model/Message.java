package es.codeurjc.eolopark.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
    private Long id;
    private Double progress;
    private boolean completed;
    private Double area;

    private User user;

    private String city;

    private EoloPark eoloPark;


    public Message(Long id, String city, Double area){
        this.id = id;
        this.city = city;
        this.area = area;
    }
//    public Message(Long id, String city, Double area, User user){
//        this.id = id;
//        this.city = city;
//        this.area = area;
//        this.user = user;
//    }
//
    public Message(Long id, Double progress, boolean completed, EoloPark eoloPark){
        this.id = id;
        this.progress = progress;
        this.completed = completed;
        this.eoloPark = eoloPark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public EoloPark getEoloPark() {
        return eoloPark;
    }

    public void setEoloPark(EoloPark eoloPark) {
        this.eoloPark = eoloPark;
    }
    
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
