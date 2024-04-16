package es.codeurjc.eolopark.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cityEoloPark", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EoloPark> cities = new ArrayList<>();

    private String name;
    private String province;
    private double windSpeed;
    private double longitude;
    private double  latitude;
    private long population;
    public int height;

    public Cities(){
        
    }

    public Cities(String name, String province, double windSpeed, double longitude, double latitude, long population, int height){
        this.name = name;
        this.province = province;
        this.windSpeed = windSpeed;
        this.longitude=longitude;
        this.latitude=latitude;
        this.population = population;
        this.height=height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getPopulation() {
        return population;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
