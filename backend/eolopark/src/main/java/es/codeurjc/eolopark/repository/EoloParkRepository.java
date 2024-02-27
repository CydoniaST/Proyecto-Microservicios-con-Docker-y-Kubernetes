package es.codeurjc.eolopark.repository;

import es.codeurjc.eolopark.model.EoloPark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EoloParkRepository extends JpaRepository<EoloPark, Long> {

    List<EoloPark> findByCity(String city);
    Page<EoloPark> findByCity(String city, Pageable pageable);


    Optional<EoloPark> findByName(String name);

}
