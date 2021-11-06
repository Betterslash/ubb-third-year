package com.microservices.pokemons.repository;

import com.microservices.pokemons.model.locations.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, String> {

}
