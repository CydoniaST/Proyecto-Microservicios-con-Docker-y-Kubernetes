package es.codeurjc.eolopark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Aerogenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_aerogenerator;
    
    private String identifyer = String.valueOf(id_aerogenerator);

    //Nuevo
    @ManyToOne
    @JoinColumn(name = "eolo_park_id")
    private EoloPark eoloPark;

    private double latitude;

    private double longitude;

    private double bladeLength;

    private double height;

    private  double power;

    public Aerogenerator(){

    }

    public Aerogenerator(String identifyer, double latitude, double longitude, double bladeLength, double height, double power){
        
        this.identifyer = identifyer;
        this.latitude= latitude;
        this.longitude= longitude;
        this.bladeLength= bladeLength;
        this.height=height;
        this.power= power;

    }

    // public void setEoloPark(EoloPark eoloPark) {
    //     this.eoloPark = eoloPark;
    // }

    public String getId() {
        return identifyer;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getBladeLength(){
        return bladeLength;
    }

    public double getHeight(){
        return height;
    }
    
    public double getPower(){
        return power;
    }
     public void setId(String identifyer) {
        this.identifyer = identifyer;
    }

      public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
      public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public void setBladeLength(double bladeLength) {
        this.bladeLength = bladeLength;
    }  
     public void setHeight(double height) {
        this.height = height;
    }    
     public void setPower(double power) {
        this.power = power;
    }    

    
    //Nuevo
    public EoloPark getEoloPark() {
        return eoloPark;
    }

    public void setEoloPark(EoloPark eoloPark) {
        this.eoloPark = eoloPark;
    }    


}