package es.codeurjc.eolopark.service;

// import java.io.BufferedReader;
// import java.io.FileInputStream;
// import java.io.FileReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.apache.commons.csv.CSVFormat;
// import org.apache.commons.csv.CSVParser;
// import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.eolopark.model.Cities;

import es.codeurjc.eolopark.repository.CitiesRepository;

@Service
public class CitiesService {
    
    @Autowired
    private CitiesRepository citiesRepository;


    //CSVParser parser = null;

    // public void saveCities(String directory) throws IOException {
    //     try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(directory)))) {
    //         parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
    //         List<Cities> cities = new ArrayList<>();
    //         for (CSVRecord record : parser) {
    //             Cities city = new Cities();
    //             city.setName(record.get("Name"));
    //             city.setWindSpeed(Double.parseDouble(record.get("Wind Speed (m/s)")));
    //             cities.add(city);
    //         }
    //         citiesRepository.saveAll(cities);
    //     }catch(Exception e){

    //     }finally{
    //         if(parser != null){
    //             parser.close();
    //         }          
    //     }     
    // }

    public void save(Cities city){
        citiesRepository.save(city);
    }
}
