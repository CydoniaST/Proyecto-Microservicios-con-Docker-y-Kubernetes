package es.codeurjc.eolopark.model;

import jakarta.persistence.*;

@Entity
public class Aerogenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String identifyer = String.valueOf(id);

    //Nuevo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eoloPark_id")
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

    
    //New
    public EoloPark getEoloPark() {
        return eoloPark;
    }

    public void setEoloPark(EoloPark eoloPark) {
        this.eoloPark = eoloPark;
    }    

    public static class Size {
        public static final Size SMALL = new Size(10, 25, 1500);
        public static final Size MEDIUM = new Size(20, 50, 2500);
        public static final Size BIG = new Size(40, 100, 3500);

        private final double bladeLength;
        private final double height;
        private final double power;

        private Size(double bladeLength, double height, double power) {
            this.bladeLength = bladeLength;
            this.height = height;
            this.power = power;
        }

        public double getBladeLength() {
            return bladeLength;
        }

        public double getHeight() {
            return height;
        }

        public double getPower() {
            return power;
        }
    }

}