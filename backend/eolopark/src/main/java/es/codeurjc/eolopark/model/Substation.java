package es.codeurjc.eolopark.model;

public class Substation {

    private String Model;

    private double Power;

    private double Voltage;



 public Substation(String Model, double Power, double Voltage) {
        this.Model = Model;
        this.Power = Power;
        this.Voltage = Voltage;
    }

     public String getModel() {
        return Model;
    }    

     public double getPower() {
        return Power;
    }

    public double getVoltage() {
        return Voltage;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public void setPower(double Power) {
        this.Power = Power;
    }

     public void setVoltage(double Voltage) {
        this.Voltage = Voltage;
    }





}

