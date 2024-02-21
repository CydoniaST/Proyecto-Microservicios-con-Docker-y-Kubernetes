package es.codeurjc.eolopark.repository;

import es.codeurjc.eolopark.model.Aerogenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AerogeneratorRepository extends JpaRepository<Aerogenerator, Long> {

    @Query("SELECT a FROM Aerogenerator a WHERE a.eoloPark.id = :id")
    Optional<Aerogenerator> findById(long id);
}
