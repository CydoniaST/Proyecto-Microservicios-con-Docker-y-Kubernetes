package es.codeurjc.eolopark.repository;

import es.codeurjc.eolopark.model.EoloPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
//import java.util.Optional;

@Repository
public interface EoloParkRepository extends JpaRepository<EoloPark, Long> {


    //@Query("SELECT DISTINCT e FROM EoloPark  e JOIN e.name c WHERE c.user=?1")
    @Query("SELECT DISTINCT e FROM EoloPark e")
    List<EoloPark> findByCity(String city);

    List<EoloPark> findByName(String name);

}
