package es.codeurjc.eolopark.repository;

import java.util.List;
import java.util.Optional;
//import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.codeurjc.eolopark.model.EoloPark;

@Repository
public interface EoloParkRepository extends JpaRepository<EoloPark, Long> {


    //@Query("SELECT DISTINCT e FROM EoloPark  e JOIN e.name c WHERE c.user=?1")
   // @Query("SELECT DISTINCT e FROM EoloPark e")

    List<EoloPark> findByCity(String city);
    Page<EoloPark> findByCity(String city, Pageable pageable);

    Optional<EoloPark> findByName(String name);


    EoloPark findBySubstation_Id(Long id);


    Page<EoloPark> findByOwnerId(Long id, Pageable pageable);

    Page<EoloPark> findByOwnerIdAndCity(Long id, String city, Pageable pageable);
}
