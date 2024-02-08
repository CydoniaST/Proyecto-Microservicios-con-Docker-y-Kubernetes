package es.codeurjc.eolopark.model;

public class Aerogenerator {

    private String Id;

    private double Latitude;

    private double Longitude;

    private double BladeLength;

    private double Height;

    private  double Power;

  public Aerogenerator(String Id, double Latitude, double Longitude, double BladeLength, double Height, double Power){
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

      public void setLatitude(int Latitude) {
        this.Latituded = Latitude;
    }
      public void setLongitude(int Longitude) {
        this.Longitude = Longitude;
    }
    
    public void setBladeLength(double BladeLength) {
        this.BladeLength = BladeLength;
    }  
     public void setHeight(double Height) {
        this.Height = Height;
    }    
     public void setPower(double Power) {
        this.Power = Power;
    }    


}
