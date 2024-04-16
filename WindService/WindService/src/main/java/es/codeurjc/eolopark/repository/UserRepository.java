package es.codeurjc.eolopark.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.eolopark.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

}
