package es.codeurjc.eolopark.model;

public class Aerogenerator {

    private String Id;

    private Int Latitude;

    private Int Longitude;

    private Double BladeLength;

    private Double Height;

    private  Double Power;


  public Aerogenerator(String Id, Int Latitude, Int Longitude, Double BladeLength, Double Height, Double Power){
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

    public  Int getLatitude(){
        return Latitude;
    }

    public  Int Longitude(){
        return Longitude;
    }

    public Double getBladeLength(){
        return BladeLength;
    }

    public Double getHeigh(){
        return Height;
    }
    
    public Double getPower(){
        return Power;
    }
     public void setId(String Id) {
        this.Id = Id;
    }

      public void setId(Int Latitude) {
        this.Latituded = Latitude;
    }
      public void setId(Int Longitude) {
        this.Longitude = Longitude;
    }
      public void setId(Int Longitude) {
        this.Longitude = Longitude;
    }
  public void setId(Double BladeLength) {
        this.BladeLength = BladeLength;
    }  
     public void setId(Double Height) {
        this.Height = Height;
    }    
     public void setId(Double Power) {
        this.Power = Power;
    }    




}
