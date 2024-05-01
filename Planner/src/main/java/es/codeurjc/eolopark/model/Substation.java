package es.codeurjc.eolopark.model;

public class Substation {

    private Long id;

    private String model;
    private Double power;
    private Double voltage;


    public Substation() {
    }

    public Substation(String model, Double power, Double voltage) {
        this.model = model;
        this.power = power;
        this.voltage = voltage;
    }


    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
