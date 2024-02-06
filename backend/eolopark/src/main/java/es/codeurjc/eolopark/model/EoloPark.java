package es.codeurjc.eolopark.model;

public class EoloPark {

    private String Name;

    private String City;

    private int Latitude;

    private int Longitude;

    private double Area;

    private TerrainType terrainType;


    public EoloPark(String Name, String City, int Latitude, int Longitude, double Area, TerrainType terrainType){
       this.Name= Name;
       this.City= City;
       this.Latitude=Latitude;
       this.Longitude=Longitude;
       this.Area= Area;
       this.terrainType= terrainType;
    }

     public String getName() {
        return Name;
    }

    public String getCity() {
        return City;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double getArea() {
        return Area;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

     public void setName(String Name) {
        this.Name = Name;
    }
    public void setCity(String City) {
        this.City = City;
    }
     public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }
    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public void setArea(double Area) {
        this.Area = Area;
    }

     public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }








    




}
