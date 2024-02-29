package es.codeurjc.eolopark.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.eolopark.model.Aerogenerator;

public interface AerogeneratorRepository extends JpaRepository<Aerogenerator, Long> {

    //@Query("SELECT a FROM Aerogenerator a WHERE a.eoloPark.id = :id")
    Optional<Aerogenerator> findById(long id);

    
}
