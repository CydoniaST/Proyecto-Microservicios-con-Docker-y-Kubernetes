package es.codeurjc.eolopark.repository;

import es.codeurjc.eolopark.model.Cities;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<Cities, Long>{

    Optional<Cities> findByName(String name);

}
