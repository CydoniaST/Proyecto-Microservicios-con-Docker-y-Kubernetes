package es.codeurjc.eolopark.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.codeurjc.eolopark.model.Substation;

@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long> {


    @Query("SELECT DISTINCT s FROM Substation s WHERE s.eoloPark.id = :id")
    Optional<Substation> findByEoloParkId(long id);


}