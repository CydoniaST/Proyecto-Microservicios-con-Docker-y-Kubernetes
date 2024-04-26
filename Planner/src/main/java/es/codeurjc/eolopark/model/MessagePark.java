package es.codeurjc.eolopark.model;

public class MessagePark {

    private String id;
    private Double progress;
    private boolean completed;
    private Double area;

    private String city;

    private EoloPark eoloPark;


    public MessagePark(String id, Double progress, boolean completed, EoloPark eoloPark){
        this.id = id;
        this.progress = progress;
        this.completed = completed;
        this.eoloPark = eoloPark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public EoloPark getEoloPark() {
        return eoloPark;
    }

    public void setEoloPark(EoloPark eoloPark) {
        this.eoloPark = eoloPark;
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
}
