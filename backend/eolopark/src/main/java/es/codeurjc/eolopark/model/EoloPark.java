package es.codeurjc.eolopark.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class EoloPark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "eoloPark", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aerogenerator> aerogeneratorList = new ArrayList<>();

    private String name;

    private String city;

    private double latitude;

    private double longitude;

    private double area;

    private TerrainType terrainType;
    

    @OneToOne(cascade = CascadeType.ALL)
    private Substation substation;


    private Long createdByUserId;
    public Long getCreatedByUserId() {
        return createdByUserId;
    }
    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public EoloPark(){

    }

    public EoloPark(String name, String city, double latitude, double longitude, double area, TerrainType terrainType){
       this.name= name;
       this.city= city;
       this.latitude=latitude;
       this.longitude=longitude;
       this.area= area;
       this.terrainType= terrainType;
    }

     public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getArea() {
        return area;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }
/*
    public List<Aerogenerator> getAerogenerators() {
        return aerogeneratorList;
    }*/

     public void setName(String name) {
        this.name = name;
    }
    public void setCity(String city) {
        this.city = city;
    }
     public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setArea(double area) {
        this.area = area;
    }

     public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

}
