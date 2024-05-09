package es.codeurjc.eolopark.model;

import java.util.ArrayList;
import java.util.List;

public class EoloPark {

  
    private Long id;

   
    private List<Aerogenerator> aerogeneratorList = new ArrayList<>();

    private Substation substation;

    private String name;

    private String city;

    private double latitude;

    private double longitude;

    private double area;

    private String terrainType;




    public EoloPark(String city, double area){
        this.city = city;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTerrainType() {
        return terrainType;
    }


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

    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
    }


    //New
    public List<Aerogenerator> getAerogeneratorList() {
        return aerogeneratorList;
    }

    public void setAerogeneratorList(List<Aerogenerator> aerogeneratorList) {

        this.aerogeneratorList = aerogeneratorList;
    }

    public void setAerogenerator(Aerogenerator aerogenerator) {

        this.aerogeneratorList.add(aerogenerator);
    }

    public Substation getSubstation() {
        return substation;
    }

    public void setSubstation(Substation substation) {
        this.substation = substation;
    }

    @Override
    public String toString() {
        return "EoloPark{" +
                "id=" + id +
                ", aerogeneratorList=" + aerogeneratorList +
                ", substation=" + substation +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", area=" + area +
                ", terrainType='" + terrainType + '\'' +
                '}';
    }
}
