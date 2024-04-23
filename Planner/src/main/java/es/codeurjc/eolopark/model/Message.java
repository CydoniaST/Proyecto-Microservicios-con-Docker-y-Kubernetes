package es.codeurjc.eolopark.model;

public class Message {
    
    private String name;
    private Double area;
    private boolean recived;

    public Message (){

    }

    public Message(String name, Double area, boolean recived){
        this.name = name;
        this.area = area;
        this.recived = recived;
    }

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Boolean getRecived() {
        return recived;
    }

    public void setRecived(Boolean recived) {
        this.recived = recived;
    }
    
}
