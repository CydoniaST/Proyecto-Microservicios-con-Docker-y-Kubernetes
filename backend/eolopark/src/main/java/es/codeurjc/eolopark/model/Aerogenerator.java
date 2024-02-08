package es.codeurjc.eolopark.model;

public class Aerogenerator {

    private String Id;

    private int Latitude;

    private int Longitude;

    private double BladeLength;

    private double Height;

    private  double Power;
//cambio

  public Aerogenerator(String Id, int Latitude, int Longitude, double BladeLength, double Height, double Power){
    this.Id = Id;
    this.Latitude= Latitude;
    this.Longitude= Longitude;
    this.BladeLength= BladeLength;
    this.Height=Height;
    this.Power= Power;

  }

   public String getId() {
        return Id;
    }

    public  int getLatitude(){
        return Latitude;
    }

    public  int Longitude(){
        return Longitude;
    }

    public double getBladeLength(){
        return BladeLength;
    }

    public double getHeigh(){
        return Height;
    }
    
    public double getPower(){
        return Power;
    }
     public void setId(String Id) {
        this.Id = Id;
    }

      public void setId(int Latitude) {
        this.Latituded = Latitude;
    }
      public void setId(int Longitude) {
        this.Longitude = Longitude;
    }
      public void setId(int Longitude) {
        this.Longitude = Longitude;
    }
  public void setId(double BladeLength) {
        this.BladeLength = BladeLength;
    }  
     public void setId(double Height) {
        this.Height = Height;
    }    
     public void setId(double Power) {
        this.Power = Power;
    }    




}
