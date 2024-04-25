package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.City;
import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.Message;
import es.codeurjc.eolopark.model.Aerogenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


@Component
public class EoloParkServiceSend {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PlannerService plannerService;

    private String name; 
    private Double area;
    private int numData;

    //LISTENER 
    @RabbitListener(queues=" eoloplantCreationRequests", ackMode = "AUTO")
    public void received(Message data){

        name = data.getName();
        area = data.getArea();
        
        EoloPark newPark = newAutomaticEoloPark(name, area);

    }
    
    //Automatic Creation
    public EoloPark newAutomaticEoloPark(String name, double area){
        //GENERIC NAME FOR AUTOMATIC PARK
        String nameAutoPark = "EoloParque Automatico con ID: "+Math.random()*10;

        //Tengo la información de la ciudad:
        City city = plannerService.getCityInfo(name);
        /*method to use latitude and longitude of a city from dataBase



         if(newCity.isPresent()){
             Cities cityAux = newCity.get();

             String windSpeed = getWindSpeed(name);

             Aerogenerator.Size size = aerogeneratorSize(windSpeed);

             //Number of Aerogen that fit in the EoloPark
             int aerogeneratorNum= (int)Math.ceil(area/1.0);

             //create EoloPark based on city and are
             EoloPark automaticEoloPark = new EoloPark(name, area);

             automaticEoloPark.setName(nameAutoPark);
             automaticEoloPark.setLatitude(cityAux.getLatitude());
             automaticEoloPark.setLongitude(cityAux.getLongitude());

             //Substation based on new EoloPark
             Substation newSubstation = new Substation("Model 1",220.0, calculateSubstationPower(aerogeneratorNum),automaticEoloPark);
             automaticEoloPark.setSubstation(newSubstation);

             //List of new Aerogens
             List<Aerogenerator> aerogenerators = new ArrayList<>();
             double latitude = cityAux.getLatitude() - 0.5 / 111.0;
             double longitude = cityAux.getLongitude() + 0.5 / 111.0;
             for(int i = 0; i < aerogeneratorNum; i++){
                 Aerogenerator aerogenerator = new Aerogenerator(i + "",latitude, longitude, size.getBladeLength(), size.getHeight(), size.getPower());
                 aerogenerator.setEoloPark(automaticEoloPark);
                 aerogenerators.add(aerogenerator);
                 longitude += 1.0/111.0;
             }

             automaticEoloPark.setAerogeneratorList(aerogenerators);
             automaticEoloPark.setTerrainType(TerrainType.PLAIN);

             return automaticEoloPark;
         }else{
             throw new IllegalArgumentException("No se encuentra la ciudad");
         }*/

        return new EoloPark();
    }

    
    private Aerogenerator.Size aerogeneratorSize(String windSpeed){
        switch(windSpeed) {
            case "LOW":
                return Aerogenerator.Size.SMALL;
            case "MEDIUM":
                return Aerogenerator.Size.MEDIUM;
            case "HIGH":
                return Aerogenerator.Size.BIG;
            default:
                throw new IllegalArgumentException("Unknown wind speed: " + windSpeed);
        }
    }

    private double calculateSubstationPower(int aerogeneratorNum){

        double maxPower = 0;
        for(int i = 0; i< aerogeneratorNum; i++){
            maxPower += Aerogenerator.Size.MEDIUM.getPower();
        }

        return maxPower*1.2; //margin = +20%
    }

	

	@Scheduled(fixedRate = 1000)
	public void sendData() {

		Message data = new Message(name ,area,true);
        
		numData++;

		System.out.println("publishToQueue: " + data);

		rabbitTemplate.convertAndSend("eoloplantCreationRequests", data);
	}
}



