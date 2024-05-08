package es.codeurjc.eolopark.model;

import java.util.ArrayList;
import java.util.List;

public class AutomaticPark {

    private String city;
    private double area;
    private Long owner;

    public List<Aerogenerator> getAerogeneratorList() {
        return aerogeneratorList;
    }

    public void setAerogeneratorList(List<Aerogenerator> aerogeneratorList) {
        this.aerogeneratorList = aerogeneratorList;
    }

    public Substation getSubstation() {
        return substation;
    }

    public void setSubstation(Substation substation) {
        this.substation = substation;
    }

    private List<Aerogenerator> aerogeneratorList = new ArrayList<>();
    private Substation substation;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
