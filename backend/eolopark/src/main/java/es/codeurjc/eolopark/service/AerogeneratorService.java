package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.Aerogenerator;
import es.codeurjc.eolopark.repository.AerogeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AerogeneratorService {

    @Autowired
    private AerogeneratorRepository aerogeneratorRepository;
    public Page<Aerogenerator> getAllAerogenerators(Pageable pageable) {
        return aerogeneratorRepository.findAll(pageable);
    }

    public AerogeneratorService(AerogeneratorRepository aerogeneratorRepository) {
        this.aerogeneratorRepository = aerogeneratorRepository;
    }

    public Optional<Aerogenerator> findAerogeneratorById(long id) {

        return aerogeneratorRepository.findById(id);
    }

     public Aerogenerator findAerogeneratorByEoloParkId(Long eoloParkId) {

        return aerogeneratorRepository.findById(eoloParkId).orElseThrow(() -> new RuntimeException("Aerogenerator no encontrado con ID: " + eoloParkId));
    }

    public Aerogenerator modifyAerogenerator(long id, Aerogenerator updatedAerogenerator) {
        Optional<Aerogenerator> existingAerogenerator = aerogeneratorRepository.findById(id);
        if (existingAerogenerator.isPresent()) {
            Aerogenerator aerogenerator = existingAerogenerator.get();

            
            //String Id, double Latitude, double Longitude, double BladeLength, double Height, double Power
            aerogenerator.setLatitude(updatedAerogenerator.getLatitude());
            aerogenerator.setLongitude(updatedAerogenerator.getLongitude());
            aerogenerator.setBladeLength(updatedAerogenerator.getBladeLength());
            aerogenerator.setHeight(updatedAerogenerator.getHeight());
            aerogenerator.setPower(updatedAerogenerator.getPower());
            return aerogeneratorRepository.save(aerogenerator);
            
        } else {

            throw new RuntimeException("Aerogenerator not found with id " + id);
        }
    }

    public void deleteAerogenerator(long id) {

        aerogeneratorRepository.deleteById(id);
    }

    public List<Aerogenerator> findAllAerogenerators() {

        return aerogeneratorRepository.findAll();
    }

      public void save(Aerogenerator aerogenerator){
        aerogeneratorRepository.save(aerogenerator);
    }

}