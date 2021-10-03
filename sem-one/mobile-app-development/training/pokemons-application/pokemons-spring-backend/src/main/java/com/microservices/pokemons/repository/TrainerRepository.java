package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
    TrainerEntity findByUsername(String username);
}
